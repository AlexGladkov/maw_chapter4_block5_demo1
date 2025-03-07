package tech.mobiledeveloper.mawc4b5d1

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction
import org.json.JSONObject

class SampleDivActionHandler : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleSampleAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleSampleAction(action: Uri, context: Context): Boolean {
        return when (action.host) {
            "toast" -> {
                Toast.makeText(context, action.query?.replace("message=", "").orEmpty(), Toast.LENGTH_SHORT).show()
                true
            }

            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "sample-action"
    }
}