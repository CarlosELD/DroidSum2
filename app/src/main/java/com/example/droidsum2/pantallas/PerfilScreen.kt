package com.example.droidsum2.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.droidsum2.modelo.SumViewModel
import okhttp3.Cookie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(viewModel: SumViewModel, navController: NavController, onLogout: () -> Unit) {
    val cookie by remember { mutableStateOf("") }
    val resultado = viewModel.getPerfil(cookie)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        },
        content = {it
            if (!(resultado.equals(null))) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Datos del perfil: $resultado", color = Color.Black)
                }
            } else {
                CircularProgressIndicator()
                Text(text= "El perfil no se encontro", color = Color.Red)
            }
        }
    )
}

