package com.example.droidsum2.modelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidsum2.data.PerfilData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SumViewModel(private val repository: SumRepository) : ViewModel() {
    private val _loginResult = MutableStateFlow("")
    val loginResult: StateFlow<String> = _loginResult

    private val _perfilData = MutableStateFlow<PerfilData?>(null)
    val perfilData: StateFlow<PerfilData?> = _perfilData

    fun login(matricula: String, contrasenia: String, tipousuario: String) {
        viewModelScope.launch {
            try {
                val result = repository.LoginAC(matricula, contrasenia, tipousuario)
                _loginResult.value = result ?: "Error de conexión"
                obtenerPerfil()
            } catch (e: Exception) {
                _loginResult.value = "Error: ${e.message}"
            }
        }
    }

    fun obtenerPerfil() {
        viewModelScope.launch {
            try {
                val result = repository.obtenerPerfil()
                println("Perfil: $result")
            } catch (e: Exception) {
                println("Error al obtener el perfil: ${e.message}")
            }
        }
    }
}
