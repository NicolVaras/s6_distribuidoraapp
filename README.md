# DistribuidoraApp

Aplicación Android desarrollada para una empresa distribuidora de alimentos que ha incorporado despacho a domicilio con cálculo automático de tarifa y validación de temperatura para productos refrigerados.

## 📱 Descripción del Proyecto

Esta aplicación móvil permite:

- Autenticación de usuarios mediante correo electrónico y contraseña con Firebase.
- Cálculo automático del costo de despacho basado en la distancia y el valor de la compra.
- Registro de la ubicación GPS del cliente al iniciar sesión.
- Alerta por temperatura si el congelador del camión supera el límite.
- Adaptabilidad a dispositivos con Android Lollipop (administrador) y Oreo (clientes).

## 🚚 Reglas de negocio

- Despacho gratuito por compras sobre $50.000 dentro de un radio de 20 km.
- Compras entre $25.000 y $49.999: tarifa de $150 por kilómetro.
- Compras menores a $25.000: tarifa de $300 por kilómetro.

## 🔐 Autenticación

- Firebase Authentication por correo y contraseña.
- Redirección a `MenuActivity` si el login es exitoso.

## 📍 Registro GPS

- Al iniciar sesión, se guarda automáticamente la ubicación GPS del usuario en Firebase Realtime Database.
- 

## 🧰 Tecnologías usadas

- **Kotlin**
- **Firebase Authentication**
- **Firebase Realtime Database**
- **Android Studio (API 21+)**
- **Git y GitHub para control de versiones**

## 🛠️ Instalación

1. Clonar este repositorio:
   ```bash
   git clone https://github.com/NicolVaras/s6_distribuidoraapp.git
