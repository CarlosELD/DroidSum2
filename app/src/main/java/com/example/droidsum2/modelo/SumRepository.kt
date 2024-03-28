package com.example.droidsum2.modelo

import com.example.droidsum2.network.NetworkRepository
import okhttp3.Cookie
import okhttp3.ResponseBody

class SumRepository(private val networkRepository: NetworkRepository) {
    suspend fun login(matricula: String, contrasenia: String): String {
        return networkRepository.acceso(matricula, contrasenia)
    }

    suspend fun obtenerPerfil(cookie: String): String {
        return networkRepository.obtenerPerfil(cookie)
    }
}