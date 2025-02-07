package tech.mobiledeveloper.mawc4b5d1

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

fun fetchJsonFromServer(url: String, callback: (JSONObject?) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            callback(null)
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                Log.e("TAG", "Response status ${it.networkResponse}")
                if (!it.isSuccessful) {
                    callback(null)
                    return
                }

                Log.e("TAG", "Response body ${it.body}")
                val jsonString = it.body?.string()
                val jsonObject = jsonString?.let { str -> JSONObject(str) }
                callback(jsonObject)
            }
        }
    })
}