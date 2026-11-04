package com.qrbarcode.creator.usecase

/** One history row, reduced to what the statistics need. */
data class InsightRow(
    val date: Long,
    val format: String,
    val schema: String,
    val isGenerated: Boolean,
    val isFavorite: Boolean
)

/** Counts for the Insights tab. Pure and unit tested. */
object Insights {

    const val DAY_MS = 24L * 60 * 60 * 1000

    data class Summary(
        val scanned: Int,
        val created: Int,
        val favorites: Int,
        val topSchemas: List<Pair<String, Int>>,
        val topFormats: List<Pair<String, Int>>,
        /** Rows per day for the last 7 days, oldest first, ending today. */
        val lastWeek: List<Int>,
        val activeDays: Int
    ) {
        val total: Int get() = scanned + created
    }

    fun dayIndex(timeMs: Long, tzOffsetMs: Long): Long = Math.floorDiv(timeMs + tzOffsetMs, DAY_MS)

    fun summarize(rows: List<InsightRow>, nowMs: Long, tzOffsetMs: Long, top: Int = 5): Summary {
        val today = dayIndex(nowMs, tzOffsetMs)
        val perDay = HashMap<Long, Int>()
        val schemas = HashMap<String, Int>()
        val formats = HashMap<String, Int>()
        var scanned = 0
        var created = 0
        var favorites = 0
        for (r in rows) {
            if (r.isGenerated) created++ else scanned++
            if (r.isFavorite) favorites++
            schemas[r.schema] = (schemas[r.schema] ?: 0) + 1
            formats[r.format] = (formats[r.format] ?: 0) + 1
            val d = dayIndex(r.date, tzOffsetMs)
            perDay[d] = (perDay[d] ?: 0) + 1
        }
        val week = (6 downTo 0).map { perDay[today - it] ?: 0 }
        return Summary(
            scanned, created, favorites,
            ranked(schemas, top), ranked(formats, top), week, perDay.size
        )
    }

    private fun ranked(map: Map<String, Int>, top: Int): List<Pair<String, Int>> =
        map.entries.sortedWith(compareByDescending<Map.Entry<String, Int>> { it.value }.thenBy { it.key })
            .take(top).map { it.key to it.value }
}
