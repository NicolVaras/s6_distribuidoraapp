package com.example.s6_distribuidoraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Iniciar Sesión", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Aquí obtenemos la ubicación y la guardamos en Firebase
                                    getLocationAndSave()
                                } else {
                                    Toast.makeText(this@LoginActivity, "Login fallido", Toast.LENGTH_SHORT).show()
                                }
                            }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }
            }
        }
    }

    // Método para obtener la ubicación y guardarla en Firebase Realtime Database
    private fun getLocationAndSave() {
        fusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener { location ->
            if (location != null) {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                val database = FirebaseDatabase.getInstance().reference
                val locationData = mapOf(
                    "latitude" to location.latitude,
                    "longitude" to location.longitude
                )

                if (userId != null) {
                    // Guardamos la ubicación en la base de datos
                    database.child("users").child(userId).child("location").setValue(locationData)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Si la ubicación se guarda correctamente, navegar a MainActivity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error al guardar ubicación", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        })
    }
}
