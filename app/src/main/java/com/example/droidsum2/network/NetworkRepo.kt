package com.example.droidsum2.network

import com.example.droidsum2.data.PerfilData
import com.example.droidsum2.data.PerfilResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class NetworkRepository(private val service: SumService) {
    suspend fun acceso(matricula: String, contrasenia: String, tipoUsuario: String): String {
        return try {
            val response = service.acceso(createSoapRequestBody(matricula, contrasenia, tipoUsuario))
            extractJsonFromXml(response.string()).toString()
        } catch (e: IOException) {
            e.printStackTrace()
            "Error de conexión: ${e.message}"
        }
    }

    private fun extractJsonFromXml(xmlString: String): String? {
        try {
            val xmlDocument = InputSource(StringReader(xmlString))
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlDocument)
            val xpathFactory = XPathFactory.newInstance()
            val xpath = xpathFactory.newXPath()
            val expression = "//accesoLoginResponse/accesoLoginResult"
            val result = xpath.evaluate(expression, document, XPathConstants.NODE) as Element
            return result.textContent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    private fun getProfileData(jsonString: String?): PerfilData? {
        try {
            jsonString?.let {
                val json = JSONObject(it)
                val acceso = json.getBoolean("acceso")
                if (acceso) {
                    val estatus = json.getString("estatus")
                    val tipoUsuario = json.getInt("tipoUsuario")
                    val contrasenia = json.getString("contrasenia")
                    val matricula = json.getString("matricula")
                    return PerfilData(estatus, tipoUsuario, contrasenia, matricula)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun obtenerPerfil(): PerfilResponse? {
        return try {
            val body = """
                <?xml version="1.0" encoding="utf-8"?>
                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                  <soap:Body>
                    <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
                  </soap:Body>
                </soap:Envelope>
            """.trimIndent()
            val requestBody = body.toRequestBody("text/xml".toMediaTypeOrNull())
            service.obtenerPerfil(requestBody)
        } catch (e: Exception) {
            null
        }
    }

    private fun createSoapRequestBody(matricula: String, contrasenia: String, tipoUsuario: String): RequestBody {
        val bodyAccess = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>$matricula</strMatricula>
                  <strContrasenia>$contrasenia</strContrasenia>
                  <tipoUsuario>$tipoUsuario</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        return bodyAccess.toRequestBody("text/xml".toMediaTypeOrNull())
    }
}
