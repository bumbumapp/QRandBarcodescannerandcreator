package com.qrbarcode.creator.feature.tabs.insights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.barcodeDatabase
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.usecase.Insights
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.TimeZone

/** Statistics computed locally from the scan and create history. */
class InsightsFragment : Fragment() {
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_insights, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.app_bar_layout).applySystemWindowInsets(applyTop = true)
    }

    override fun onResume() {
        super.onResume()
        val now = System.currentTimeMillis()
        barcodeDatabase.getInsightRows()
            .subscribeOn(Schedulers.io())
            .map { Insights.summarize(it, now, TimeZone.getDefault().getOffset(now).toLong()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ show(it) }, { com.qrbarcode.creator.usecase.Logger.log(it) })
            .addTo(disposable)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun show(s: Insights.Summary) {
        val v = view ?: return
        v.findViewById<View>(R.id.layout_content).isVisible = s.total > 0
        v.findViewById<View>(R.id.text_empty).isVisible = s.total == 0
        v.findViewById<TextView>(R.id.text_scanned).text = s.scanned.toString()
        v.findViewById<TextView>(R.id.text_created).text = s.created.toString()
        v.findViewById<TextView>(R.id.text_favorites).text = s.favorites.toString()
        v.findViewById<TextView>(R.id.text_active_days).text = s.activeDays.toString()
        v.findViewById<WeekBarsView>(R.id.week_bars).setValues(s.lastWeek)
        v.findViewById<TextView>(R.id.text_top_types).text = lines(s.topSchemas)
        v.findViewById<TextView>(R.id.text_top_formats).text = lines(s.topFormats)
    }

    private fun lines(list: List<Pair<String, Int>>): String =
        list.joinToString("\n") { (name, n) -> "${pretty(name)}  -  $n" }

    private fun pretty(raw: String): String =
        raw.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() }
}
