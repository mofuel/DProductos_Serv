# Robot Asistente Tradicional sobre Ruedas

## Descripción

El **Robot Asistente Tradicional sobre Ruedas** está diseñado como un prototipo funcional para asistir a personas en tareas diarias como la entrega de medicamentos. Equipado con un sistema de control por voz y sensores para evitar obstáculos, este robot tiene la capacidad de navegar de forma autónoma en entornos controlados, facilitando la movilidad y aumentando la independencia de las personas que lo usan.

El robot es capaz de recibir comandos de voz, desplazarse sin chocar con objetos, y entregar medicamentos a la hora programada, sin la intervención de un tercero. La estructura modular del robot permite futuras ampliaciones, como la inclusión de cámaras y otros dispositivos interactivos para una experiencia más completa.

## Características

- **Movilidad Autónoma**: El robot se mueve de manera autónoma utilizando motores DC y sensores ultrasónicos para evitar obstáculos.
- **Entrega de Medicamentos**: Cuenta con un compartimento automático que abre en el momento indicado para que el usuario pueda acceder al medicamento sin esfuerzo.
- **Control por Voz**: Los usuarios pueden interactuar con el robot a través de comandos de voz, como "llevarme a la cocina" o "entregar medicamento".
- **Pantalla LCD para Videollamadas**: Integrado con una cámara y un altavoz, el robot puede realizar videollamadas mediante comandos de voz, brindando apoyo adicional.
- **Seguridad**: Los sensores de proximidad y los motores con retroalimentación aseguran que el robot se desplace de manera segura sin chocar con personas u objetos.
- **Interfaz de Control Remota**: Control mediante una aplicación web responsiva, permitiendo la gestión de horarios de medicación y la confirmación de entrega.

## Construido con

- **Hardware**:
  - Arduino (para controlar motores, sensores y sistema de apertura)
  - Raspberry Pi (para procesamiento de voz, videollamadas y control avanzado)
  - Sensores ultrasónicos
  - Motores DC
  - Cámara USB
  - Pantalla LCD
  - Altavoces y micrófono

- **Software**:
  - **Lenguajes**: Java, Python, HTML, JavaScript, CSS
  - **Frameworks**: Spring Boot (para el backend de la app web)
  - **Bases de datos**: MySQL (para registrar entregas y confirmaciones de medicamentos)
  - **Tecnologías**:
    - API REST para comunicación entre la aplicación web y el robot

## Instalación

Para ejecutar este proyecto, sigue los siguientes pasos:

1. **Hardware**:
   - Conecta el Arduino a la Raspberry Pi a través de USB o puerto serial.
   - Asegúrate de que los sensores ultrasónicos y motores DC estén conectados correctamente a los pines de Arduino.
   - Conecta la pantalla LCD, cámara, micrófono y altavoces a la Raspberry Pi.



3. **Acceder al robot**:
   - A través de la aplicación web, puedes gestionar los horarios de medicación, enviar comandos al robot y ver el estado de las entregas.


## Licencia

Este proyecto está bajo la Licencia MIT. Para más detalles, consulta el archivo `LICENSE`.

---
