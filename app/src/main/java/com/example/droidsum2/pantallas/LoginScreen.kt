package com.example.droidsum2.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.droidsum2.modelo.SumRepository
import com.example.droidsum2.modelo.SumViewModel
import com.example.droidsum2.modelo.ViewModelFactory
import com.example.droidsum2.network.NetworkRepository
import com.example.droidsum2.network.SumService

@Composable
fun LoginScreen(Servicio: SumService, navController: NavController) {
    val viewModel: SumViewModel = viewModel(
        factory = ViewModelFactory(SumRepository(NetworkRepository(Servicio)))
    )
    var passwordVisible by remember { mutableStateOf(false) }
    var matricula by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    val resultado by viewModel.loginResult.collectAsState()
    Surface(modifier = Modifier, color = Color.Cyan) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "DROID SUM", modifier = Modifier, textAlign = TextAlign.Center, color = Color.Black)
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = contrasenia,
                onValueChange = { contrasenia = it},
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        val icon: ImageVector = if (passwordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility
                        Icon(icon, contentDescription = if (passwordVisible) "Ocultar" else "Mostrar")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    if(matricula.isNotEmpty() && contrasenia.isNotEmpty()){
                        viewModel.login(matricula, contrasenia)}
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            if(matricula.isNotEmpty() && contrasenia.isNotEmpty()) {
                if (resultado.isNotEmpty() && !(resultado.equals(null))) {
                    //Text(text = resultado, modifier = Modifier.padding(top = 16.dp))
                    CircularProgressIndicator()
                    navController.navigate("PerfilScreen")
                }else if(resultado.equals(null)) {
                    CircularProgressIndicator()
                    Text("El usuario o contraseña es incorrecto")
                }
            }else if(matricula.isEmpty() && contrasenia.isEmpty()){
                Text(text = "Favor de ingresar los datos solicitados")
            }
        }
    }
}

