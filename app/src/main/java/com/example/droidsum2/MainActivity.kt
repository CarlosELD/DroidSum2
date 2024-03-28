package com.example.droidsum2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.droidsum2.modelo.SumRepository
import com.example.droidsum2.modelo.SumViewModel
import com.example.droidsum2.modelo.ViewModelFactory
import com.example.droidsum2.navegation.Navega
import com.example.droidsum2.network.NetworkRepository
import com.example.droidsum2.network.RetrofitCliente
import com.example.droidsum2.network.SumService
import com.example.droidsum2.ui.theme.DroidSum2Theme

class MainActivity : ComponentActivity() {
    private lateinit var Servicio: SumService
    private lateinit var viewModel: SumViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Servicio = RetrofitCliente.instance
        viewModel = SumViewModel(SumRepository(NetworkRepository(Servicio)))
        setContent {
            DroidSum2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navega(Servicio, viewModel)
                }
            }
        }
    }
}