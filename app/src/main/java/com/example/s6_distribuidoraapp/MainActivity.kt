package com.example.s6_distribuidoraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.s6_distribuidoraapp.ui.theme.S6_distribuidoraappTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            S6_distribuidoraappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var totalCompra by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    val focusRequesterDistancia = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Calculadora de Despacho", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = totalCompra,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    totalCompra = newValue
                }
            },
            label = { Text("Total de la compra (CLP)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterDistancia.requestFocus() }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = distancia,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    distancia = newValue
                }
            },
            label = { Text("Distancia en kilómetros") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesterDistancia)
        )

        Button(
            onClick = {
                val total = totalCompra.toIntOrNull() ?: 0
                val km = distancia.toIntOrNull() ?: 0
                val costo = calcularDespacho(total, km)
                resultado = "Costo de despacho: $costo CLP"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular despacho")
        }

        Text(text = resultado, style = MaterialTheme.typography.bodyMedium)
    }
}

fun calcularDespacho(totalCompra: Int, distanciaKm: Int): Int {
    return when {
        totalCompra >= 50000 -> 0
        totalCompra in 25000..49999 -> distanciaKm * 150
        else -> distanciaKm * 300
    }
}
