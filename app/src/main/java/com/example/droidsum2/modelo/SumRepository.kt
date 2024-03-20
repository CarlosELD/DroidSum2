package com.example.droidsum2.modelo

import com.example.droidsum2.data.PerfilResponse
import com.example.droidsum2.network.NetworkRepository
class SumRepository(private val networkRepository: NetworkRepository) {
    suspend fun LoginAC(matricula: String, contrasenia: String, tipoUsuario: String): String {
        return networkRepository.acceso(matricula, contrasenia, tipoUsuario)
    }
    suspend fun obtenerPerfil(): PerfilResponse? {
        return networkRepository.obtenerPerfil()
    }
}