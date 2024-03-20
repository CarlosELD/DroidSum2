package com.example.droidsum2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.droidsum2.modelo.SumRepository
import com.example.droidsum2.modelo.SumViewModel
import com.example.droidsum2.modelo.ViewModelFactory
import com.example.droidsum2.network.NetworkRepository
import com.example.droidsum2.network.SumService

@Composable
fun LoginScreen(Servicio: SumService) {
    val viewModel: SumViewModel = viewModel(
        factory = ViewModelFactory(SumRepository(NetworkRepository(Servicio)))
    )
    var matricula by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var tipousuario by remember { mutableStateOf("") }
    val resultado by viewModel.loginResult.collectAsState()
    Surface(modifier = Modifier, color = Color.Yellow) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "DROID SUM", modifier = Modifier, textAlign = TextAlign.Center, color = Color.Black)
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = contrasenia,
                onValueChange = { contrasenia = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = tipousuario,
                onValueChange = { tipousuario = it },
                label = { Text("TipoUsuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = { viewModel.login(matricula, contrasenia, tipousuario) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
            if (resultado.isNotEmpty()) {
                Text(text = resultado, modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
