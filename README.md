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

 # Endpoints propios (API del proyecto): 

La URL base de la API se configura en RetroFitCliente.kt
1. UserController - /api/users
   * POST /api/users
     - Descripción: Registra un nuevo usuario.
     - Request Body: { "nombre": "Juan", "correo": "j@mail.com", "clave": "pass123" }
     - Response Body: User (objeto creado)

 * GET /api/users
   - Descripción: Lista todos los usuarios registrados (puede requerir autorización según tu lógica).
   - Headers: Authorization: Bearer <token> (opcional)
   - Response Body: [ User, ... ]
     
2. EmotionController /api/emotions
   * POST /api/emotions
   - Descripción: Guarda un registro de emoción para el usuario autenticado.
   - Headers: Authorization: Bearer <token>
   - Request Body: { "tipo": "tristeza|alegria|ansiedad", "intensidad": 1..10, "nota": "opcional" }
   - Response Body: Emocion (id, tipo, intensidad, fecha, usuario)

  * GET /api/emotions
    - Descripción: Obtiene el historial de emociones del usuario autenticado (paginado opcional).
    - Headers: Authorization: Bearer <token>
    - Query params (opcionales): page, size, desde, hasta
    - Response Body: [ Emocion, ... ]

  * GET /api/emotions/{id}
    - Descripción: Obtiene un registro de emoción específico (debe pertenecer al usuario o permiso admin).
    - Headers: Authorization: Bearer <token>
    - Response Body: Emocion

  * DELETE /api/emotions/{id} (opcional)
    - Descripción: Elimina un registro de emoción (propietario o admin).
    - Headers: Authorization: Bearer <token>
    - Response: 200/204
 
3. ResourceController - /api/posts
  * GET /api/posts
    - Descripción: Obtiene el listado de publicaciones para el muro de la comunidad. Requiere autenticación según tu diseño.
    - Headers: Authorization: Bearer <token> (si aplica)
    - Query params (opcionales): page, size, filtro
    - Response Body: [ PostItem { id, titulo, contenido, autor, fecha }, ... ]

  * POST /api/posts
    - Descripción: Crea una nueva publicación (usuario autenticado).
    - Headers: Authorization: Bearer <token>
    - Request Body: { "titulo": "...", "contenido": "...", "adjuntos": [...] }
    - Response Body: PostItem (objeto creado)

  * GET /api/posts/{id}
    - Descripción: Obtiene una publicación por id.
    - Response Body: PostItem

  * PUT /api/posts/{id}
    - Descripción: Actualiza una publicación (propietario/admin).
    - Headers: Authorization: Bearer <token>
    - Request Body: { "titulo": "...", "contenido": "..." }
    - Response Body: PostItem actualizado

  * DELETE /api/posts/{id}
    - Descripción: Elimina una publicación (propietario/admin).
    - Headers: Authorization: Bearer <token>
    - Response: 200/204
   
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

* # 3. Configurar el Backend:
  
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

* # Código fuente:
  
  *  Aplicación móvil: El código fuente completo de la aplicación Android se encuentra en la raíz de este repositorio.
  *  Microservicios: El código fuente de los microservicios del backend se encuentra en un repositorio separado.
     * URL del repositorio de backend: [https://github.com/FeiSCaroca/backend-appmovil]
       
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
    


