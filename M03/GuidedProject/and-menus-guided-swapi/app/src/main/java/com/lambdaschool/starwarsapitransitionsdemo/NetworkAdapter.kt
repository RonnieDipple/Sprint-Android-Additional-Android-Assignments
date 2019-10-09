package com.lambdaschool.starwarsapitransitionsdemo

import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.MalformedURLException
import java.net.URL

import javax.net.ssl.HttpsURLConnection

object NetworkAdapter {
    val GET = "GET"
    val POST = "POST"
    val HEAD = "HEAD"
    val OPTIONS = "OPTIONS"
    val PUT = "PUT"
    val DELETE = "DELETE"
    val TRACE = "TRACE"

    interface NetworkCallback {
        fun processResult(result: String)
    }

    @JvmOverloads
    internal fun httpRequest(urlString: String, requestMethod: String = GET, requestBody: JSONObject? = null, headerProperties: Map<String, String>? = null): String {
        var result = ""
        var inputStream: InputStream? = null
        var connection: HttpsURLConnection? = null

        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpsURLConnection

            connection.requestMethod = requestMethod

            if (headerProperties != null) {
                for ((key, value) in headerProperties) {
                    connection.setRequestProperty(key, value)
                }
            }

            connection.connectTimeout = 3000
            connection.readTimeout = 3000

            if ((requestMethod == POST || requestMethod == PUT) && requestBody != null) {
                connection.doInput = true
                val outputStream = connection.outputStream
                outputStream.write(requestBody.toString().toByteArray())
                outputStream.close()
            } else {
                connection.connect()
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.inputStream
                if (inputStream != null) {
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val builder = StringBuilder()

                    var line: String?
                    do {
                        line = reader.readLine()
                        builder.append(line)
                    } while (line != null)
                    result = builder.toString()
                }
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            connection?.disconnect()
        }
        return result
    }

    fun backgroundHttpRequest(urlString: String, callback: NetworkCallback) {
        Thread(Runnable { callback.processResult(httpRequest(urlString)) }).start()
    }
}
