package com.example.droidsum2.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.droidsum2.pantallas.LoginScreen
import com.example.droidsum2.pantallas.PerfilScreen
import com.example.droidsum2.modelo.SumViewModel
import com.example.droidsum2.network.SumService

@Composable
fun Navega(service: SumService, viewModel: SumViewModel){
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = "LoginScreen"){
        composable("LoginScreen"){
            LoginScreen(Servicio = service, controller)
        }
        composable("PerfilScreen"){
            PerfilScreen(viewModel = viewModel, controller ,onLogout = {controller.navigate("LoginScreen")})
        }
    }
}