package com.example.droidsum2.network

import okhttp3.Cookie
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SumService {
    @Headers(
        "Content-Type: text/xml;charset=utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin",
        "Cookie: .ASPXANONYMOUS=MaWJCZ-X2gEkAAAAODU2ZjkyM2EtNWE3ZC00NTdlLWFhYTAtYjk5ZTE5MDlkODIzeI1pCwvskL6aqtre4eT8Atfq2Po1;.ASPXANONYMOUS=MaWJCZ-X2gEkAAAAODU2ZjkyM2EtNWE3ZC00NTdlLWFhYTAtYjk5ZTE5MDlkODIzeI1pCwvskL6aqtre4eT8Atfq2Po1;"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun acceso(@Body soap: RequestBody): ResponseBody

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAlumnoAcademicoWithLineamiento",
        "Cookie: .ASPXANONYMOUS=MaWJCZ-X2gEkAAAAODU2ZjkyM2EtNWE3ZC00NTdlLWFhYTAtYjk5ZTE5MDlkODIzeI1pCwvskL6aqtre4eT8Atfq2Po1;.ASPXANONYMOUS=MaWJCZ-X2gEkAAAAODU2ZjkyM2EtNWE3ZC00NTdlLWFhYTAtYjk5ZTE5MDlkODIzeI1pCwvskL6aqtre4eT8Atfq2Po1;"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun perfil(@Body soap: RequestBody): ResponseBody
}
fun crearPeticionAcceso(matricula: String, contrasenia: String): RequestBody {
    val tipoUsuarioAlumno: Boolean = true // true para ALUMNO
    val tipoUsuarioDocente: Boolean = false // false para DOCENTE
    val tipoUsuario = if(tipoUsuarioAlumno)"ALUMNO" else "Docente"
    val body = """
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
    return body.toRequestBody("text/xml".toMediaTypeOrNull())
}
fun crearPeticionPerfil(cookie: String): RequestBody {
    val body = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
                <getAlumnoAcademicoWithLineamientoResult>$cookie</getAlumnoAcademicoWithLineamientoResult>
    </getAlumnoAcademicoWithLineamientoResponse>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
    return body.toRequestBody("text/xml".toMediaTypeOrNull())
}