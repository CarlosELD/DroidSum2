package com.example.droidsum2.network

import okhttp3.Cookie
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class NetworkRepository(private val service: SumService) {
    suspend fun acceso(matricula: String, contrasenia: String): String {
        return try {
            val response = service.acceso(crearPeticionAcceso(matricula, contrasenia)).string()
            extractJsonFromXmlAcceso(response).toString()
        }catch (e: IOException) {
            e.printStackTrace()
            "Error de conexión: ${e.message}"
        }
    }

    suspend fun obtenerPerfil(cookie: String): String {
        return try {
            val response = service.perfil(crearPeticionPerfil(cookie))
            val responseBody = response.string()
            println("Response body: $responseBody") // Imprime el cuerpo de la respuesta para depuración
            if (!(response.equals(null))) {
                extractJsonFromXmlPerfil(responseBody).toString()
            } else {
                "Error en la solicitud: ${response}"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Error de conexión: ${e.message}"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error desconocido: ${e.message}"
        }
    }

    private fun extractJsonFromXmlAcceso(xmlString: String): String? {
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
    private fun extractJsonFromXmlPerfil(xmlString: String): String? {
        try {
            val xmlDocument = InputSource(StringReader(xmlString))
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlDocument)
            val xpathFactory = XPathFactory.newInstance()
            val xpath = xpathFactory.newXPath()
            val expression = "//getAlumnoAcademicoWithLineamientoResponse/getAlumnoAcademicoWithLineamientoResult"
            val result = xpath.evaluate(expression, document, XPathConstants.NODE) as Element
            return result.textContent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
