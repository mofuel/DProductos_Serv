# 🤖 Robot Asistente Tradicional sobre Ruedas

![Estado del proyecto](https://img.shields.io/badge/Estado-En%20desarrollo-yellow)
![Licencia](https://img.shields.io/badge/Licencia-MIT-green)
![Tecnología](https://img.shields.io/badge/Hecho%20con-Arduino%20%7C%20Spring%20Boot-blue)

## 🚀 Descripción

El **Robot Asistente Tradicional sobre Ruedas** es un prototipo funcional enfocado en la entrega automatizada de medicamentos. Su diseño permite asistir a personas en el hogar, brindándoles mayor independencia y seguridad. Se controla mediante comandos de voz y una aplicación web responsiva, combinando movilidad autónoma con inteligencia programada.

El robot puede desplazarse por ambientes controlados, evitar obstáculos, y entregar medicamentos en horarios programados sin necesidad de intervención humana directa. Además, permite la integración de mejoras futuras como cámaras inteligentes o sensores adicionales.

## ✨ Características

| Funcionalidad                  | Descripción                                                                 |
|-------------------------------|-----------------------------------------------------------------------------|
| 🕹️ Movilidad Autónoma         | Se desplaza con sensores ultrasónicos y motores DC para evitar obstáculos. |
| 💊 Entrega Programada         | Compartimento que se abre automáticamente a la hora indicada.              |
| 🎤 Control por Voz            | Comandos como “entregar medicamento” o “ir a la sala”.                     |
| 📹 Videollamadas              | Pantalla LCD con cámara y altavoz para comunicación remota.                |
| 🔒 Seguridad Integrada        | Evita colisiones gracias a sensores de proximidad.                         |
| 🌐 App Web de Control         | Interfaz responsiva para gestionar horarios y confirmar entregas.          |

## 🧰 Construido con

### 🔌 Hardware
- Arduino UNO/Nano
- Raspberry Pi 4
- Sensores ultrasónicos
- Motores DC
- Módulo de control de motor L298N
- Pantalla LCD
- Cámara USB
- Micrófono y altavoces
- Servomotor para apertura de compartimento

### 💻 Software
- **Lenguajes**: Java, Python, HTML, JavaScript, CSS
- **Frameworks**: Spring Boot (backend), Bootstrap (frontend)
- **Base de Datos**: MySQL
- **Otras tecnologías**: API REST, comunicación serial Pi ↔ Arduino

## 🧪 Escenarios de Prueba

1. ✅ **El robot entrega el medicamento a la hora programada.**
2. ✅ **El usuario puede revisar el historial de entregas y confirmaciones.**
3. ✅ **El compartimento se abre correctamente al recibir orden desde la app o por voz.**

## 📦 Instalación

1. **Configuración de Hardware**:
   - Conecta el Arduino a la Raspberry Pi por USB o puerto serial.
   - Conecta sensores, motores, LCD y servos a Arduino según el esquema.
   - Instala la cámara, micrófono y altavoces en la Raspberry Pi.

2. **Aplicación Web**:
   - Clona el repositorio.
   - Configura el archivo `application.properties` con los datos de conexión a MySQL.
   - Ejecuta el backend con `Spring Boot`.
   - Accede desde el navegador a la app en `http://localhost:8080`.

3. **Control del Robot**:
   - Desde la web: programa horarios y verifica historial.
   - Por voz: activa comandos predefinidos conectados al backend.

## 🤝 Contribuciones

¡Toda ayuda es bienvenida! Puedes hacer fork del proyecto, proponer mejoras o reportar errores.

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más información.

