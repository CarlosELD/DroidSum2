package com.example.droidsum2.modelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SumViewModel(private val repository: SumRepository) : ViewModel() {
    private val _loginResult = MutableStateFlow("")
    val loginResult: StateFlow<String> = _loginResult

    private val _perfilResponse = MutableStateFlow("")
    val perfilResponse: StateFlow<String> = _perfilResponse
    fun login(matricula: String, contrasenia: String) {
        viewModelScope.launch {
            try {
                val result = repository.login(matricula, contrasenia)
                _loginResult.value = result ?: "Error de conexión"
            } catch (e: Exception) {
                _loginResult.value = "Error: ${e.message}"
            }
        }
    }

    fun getPerfil(cookie: String) {
        viewModelScope.launch {
            try {
                val perfil = repository.obtenerPerfil(cookie)
                _perfilResponse.value = perfil ?: "Error: no se encuentra el perfil"
            } catch (e: Exception) {
                _perfilResponse.value = "Error: ${e.message}"
            }
        }
    }
}
