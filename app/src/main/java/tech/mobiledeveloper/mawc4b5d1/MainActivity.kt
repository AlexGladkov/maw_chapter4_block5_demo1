package tech.mobiledeveloper.mawc4b5d1

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div2.DivData
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val imageLoader = PicassoDivImageLoader(applicationContext)
        val configuration = DivConfiguration.Builder(imageLoader)
            .actionHandler(SampleDivActionHandler())
            .build()

        val handler = Handler()

        fetchJsonFromServer(url = "http://10.0.2.2:9090/") {
            handler.post {
                try {
                    val divData = it?.asDiv2DataWithTemplates()
                    val div2View = Div2View(Div2Context(baseContext = this, configuration = configuration))
                    findViewById<FrameLayout>(R.id.containerView).addView(div2View)
                    div2View.setData(divData, DivDataTag("your_unique_tag_here"))
                    Log.d("DivKit", "JSON успешно создан: $it")
                } catch (e: JSONException) {
                    Log.e("DivKit", "Ошибка парсинга JSON: ${e.message}")
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

fun JSONObject.asDiv2DataWithTemplates(): DivData {
    val templates = getJSONObject("templates")
    val card = getJSONObject("card")
    val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
    environment.parseTemplates(templates)
    return DivData(environment, card)
}