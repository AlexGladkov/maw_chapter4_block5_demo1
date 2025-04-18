package tech.mobiledeveloper.mawc4b5d1.helpers

import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div2.DivData
import org.json.JSONObject

fun JSONObject.asDiv2DataWithTemplates(): DivData {
    val templates = getJSONObject("templates")
    val card = getJSONObject("card")
    val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
    environment.parseTemplates(templates)
    return DivData(environment, card)
}