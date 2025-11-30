# App Apoyo Emocional

# Integrantes del grupo:
* Ma. Alejandra Marambio
* Felipe Sanchez
* Pamela Toro

# Objetivo del proyecto:
Brindar acompañamiento emocional accesible, segura y personalizado a través de una aplicación móvil, 
con el de mejorar el bienestar psicológico de jóvenes y adultos.

* Fomentar el autocuidado emocional mediante herramientas interactivas y educativas.
* Ofrecer un espacio seguro y privado para el registro de emociones.
* Educar sobre salud mental con contenido validado, accesible y amigable.
 
# Breve descripción técnica:

Esta aplicación móvil, desarrollada en Kotlin con Jetpack Compose, ofrece un conjunto de herramientas y recursos para ayudar a los usuarios a gestionar su bienestar emocional. Permite el registro de usuarios, el reconocimiento facial a través de la cámara, ejercicios de respiración, y una comunidad para compartir estados de ánimo. 

# Funcionalidades:
La aplicación cuenta con las siguientes características principales:
* # Autenticación de usuarios:
* Registro de nuevas cuentas de usuario (nombre, correo, contraseña).
* Inicio de sesión seguro mediante correo y contraseña.
* Manejo de sesión a través de tokens JWT.
* # Reconocimiento facial de emociones:
* Utiliza la cámara del disposito para detectar el rostro del usuario.
* Analiza la expresión facial para identificar y mostrar la emoción predominante.
* # Registro y seguimiento de emociones:
* Permite al usuario registrar manualmente su estado emocional actual.
* Guarda un historial de las emociones registradas, asociado al perfil del usuario.
* # Muro de publicaciones:
* Una sección donde los usuarios pueden ver publicaciones de otros miembros (posts).
* Funciona como un "muro".
* # Recursos de bienestar:
* Módulo de respiración: Guía al usuario a través de ejercicios de respiración para calmar la ansiedad y el estrés.
* Sección de recursos: Ofrece información y herramientas adicionales para el manejo emocional.
* # Gestión de perfil:
* Los usuarios pueden ver y gestionar la información de su perfil.

# Endpoints usados:
La aplicación se comunica con un backend a través de una API REST para gestionar la autenticación, los usuarios y las publicaciones.

 # Endpoints propios (API del proyecto): (EN MODIFICACIÓN)

La URL base de la API se configura en RetroFitCliente.kt

 * POST /auth/login
   * Descripción: Autentica a un usuario y devuelve un token JWT.
   * Request Body: { "correo": "user@example.com", "clave": "password123" }
   * Response Body: { "token": "ey..." }
     
  * POST /usuarios/
    * Descripción: Registra un nuevo usuario en el sistema.
    * Request Body: { "nombre": "...", "correo": "...", "clave": "..." }
      
  * GET /posts/
    * Descripción: Obtiene el listado de publicaciones para el muro de la comunidad. Requiere autenticación.
    * Headers: Authorization: Bearer <token>
    
  * GET /usuarios/{nombreUsuario}
    * Descripción: Obtiene la información del perfil de un usuario específico.
      
  * POST /emociones/
    * Descripción: Guarda un registro de emoción para el usuario autenticado.
 
 # Endpoints externos:
 
 * Modelo de reconocimiento facial: La aplicación parece utilizar un modelo de machine learning para el analisis de emociones. Este modelo se ejecuta localmente en el dispositivo y no requiere un endpoint externo.
 

# Instrucciones para ejecutar el proyecto:
Sigue estos pasos para compilar y ejecutar la aplicación en Android Studio:
* # 1. Clonar el repositorio:
  
  *     git clone <URL-del-repositorio>
    cd AppApoyoEmocional
    
* # 2. Abrir en Android Studio:
  
  * Abre Android Studio
  * Selecciona File > Open y navega hasta la carpeta del proyecto clonado.
  * Espera a que Gradle sincronice todas las dependencias.

* # 3. Configurar el Backend:(EN MODIFICACIÓN)
  
  * Asegúrate de que el servidor del backend (los microservicios) esté en ejecución.
  * Abre el archivo app/src/main/java/com/example/appapoyoemocional/network/RetroFitCliente.kt.
  * Modificar la variable BASE_URL para que apunte a la dirección IP y puerto de tu servidor local.
    - Importante: Si ejecutas en un emulador de Android, no uses localhost o 127.0.0.1. Usa la dirección IP especial 10.0.2.2.
    - Ejemplo: private const val BASE_URL = "http://10.0.2.2:8080/"
      
* # 4. Ejecutar la aplicación:

  * Selecciona un emulador de Android o conecta un dispositivo físico.
  * Presiona el botón Run 'app' (el icono de play verde) en la barra de herramientas de Android Studio.
 
    
* # APK Firmado y ubicación del archivo.jks:
  
* APK firmado: El archivo APK firmado para la instalación directa se encuentra en la ruta: app/release/app-release.apk.
* Almacén de claves(.jks): El archivo de claves Java (.jks) keystore/upload-kestore.jks.
   * Credenciales: Las credenciales (alias y contraseña) para acceder a este almacén se gestionan de forma segura y no están incluidas en el repositoriio. Se pueden encontar en el archivo keystore.properties, que está listado en .gitignore para no ser subido al control de versiones.

* # Código fuente:(EN MODIFICACIÓN)
  
  *  Aplicación móvil: El código fuente completo de la aplicación Android se encuentra en la raíz de este repositorio.
  *  Microservicios: El código fuente de los microservicios del backend se encuentra en un repositorio separado.
     * URL del repositorio de backend: [enlace del repositorio de los microservicios]
       
* # Notas:
  * La aplicacion requiere permiso para usar la cámara para la funcionalidad de foto de perfil y  reconocimiento facial.
  * Todos los datos del usuario (nombre, foto de perfil) se gestionan localmente en la aplicación.
    No hay almacenamiento en la nube.

* # Evidencia de trabajo colaborativo:

* El trabjo en este proyecto fue distribuido entre los miembros del equipo. Para revisar las contribuciones individuales, se puede utilizar el historial de Git.

  # Comandos útiles de Git:
  * # Ver commits por autor:
    bash

    Reemplaza "NombreDelAutor" con el nombre de usuario de Git del colaborador
    git log --author="NombreDelAutor" --oneline

    bash git shortlog -s -n ```
    Esto proporcionará una visión clara de los aportes de cada integrante del equipo a lo largo del desarrollo del proyecto.
    


