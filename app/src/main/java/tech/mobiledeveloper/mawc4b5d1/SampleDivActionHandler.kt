package tech.mobiledeveloper.mawc4b5d1

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class SampleDivActionHandler(val onClick: () -> Unit) : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        Log.e("TAG", "URL ${action.url}")
        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        Log.e("TAG", "SCHEME ${url.scheme}")
        return if (url.scheme == SCHEME_SAMPLE && handleSampleAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleSampleAction(action: Uri, context: Context): Boolean {
        Log.e("TAG", "HOST ${action.host}, QUERY ${action.query}")
        return when (action.host) {
            "toast" -> {
                onClick.invoke()
                true
            }

            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "sample-action"
    }
}