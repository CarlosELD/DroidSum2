package com.example.droidsum2.network

import com.example.droidsum2.data.AccessRequest
import com.example.droidsum2.data.AccessResponse
import com.example.droidsum2.data.PerfilRequest
import com.example.droidsum2.data.PerfilResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService : SumService {
    @Headers("SOAPAction: http://tempuri.org/accesoLogin")
    @POST("/ws/wsalumnos.asmx")
    suspend fun acceso(
        @Body request: AccessRequest,
        @Header("Content-Type") contentType: String = "text/xml; charset=utf-8"
    ): Call<AccessResponse>

    @Headers("SOAPAction: http://tempuri.org/getAlumnoAcademicoWithLineamiento")
    @POST("/ws/wsalumnos.asmx")
    suspend fun obtenerPerfil(
        @Body request: PerfilRequest,
        @Header("Content-Type") contentType: String = "text/xml; charset=utf-8"
    ): Call<PerfilResponse>
}
