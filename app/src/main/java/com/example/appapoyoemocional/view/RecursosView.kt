package com.example.appapoyoemocional.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appapoyoemocional.data.Resource // Asegúrate que esta clase y paquete existen
import com.example.appapoyoemocional.viewModel.RecursosViewModel

/**
 * Interfaz de usuario que muestra la lista de recursos de apoyo emocional.
 */
@OptIn(ExperimentalMaterial3Api::class) // Anotación para usar TopAppBar
@Composable
fun RecursosView(
    viewModel: RecursosViewModel = viewModel()
) {
    val recursos by viewModel.recursos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            // --- CORRECCIÓN 1: TopAppBar con la sintaxis de Material 3 ---
            TopAppBar(
                title = { Text("Recursos de Apoyo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                isLoading -> {
                    // Muestra un indicador de progreso mientras se cargan los datos
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    Text("Cargando recursos...", modifier = Modifier.padding(top = 8.dp))
                }
                error != null -> {
                    // Muestra un mensaje de error si la carga falla
                    // Pasamos el ViewModel para poder llamar a la función de reintentar
                    ErrorContent(errorMessage = error!!, viewModel = viewModel)
                }
                recursos.isEmpty() -> {
                    Text("No hay recursos de apoyo disponibles.", style = MaterialTheme.typography.titleMedium)
                }
                else -> {
                    RecursosList(recursos = recursos)
                }
            }
        }
    }
}

/**
 * Componente que muestra la lista de recursos en un LazyColumn.
 */
@Composable
fun RecursosList(recursos: List<Resource>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Aumentado para mejor espaciado
    ) {
        items(recursos) { recurso ->
            RecursoItem(recurso = recurso)
        }
    }
}

/**
 * Diseño para cada elemento de recurso.
 */
@Composable
fun RecursoItem(recurso: Resource) {
    // --- CORRECCIÓN 2: Card con la sintaxis de Material 3 ---
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = recurso.content,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ID: ${recurso.id}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Componente para mostrar un mensaje de error.
 */
@Composable
fun ErrorContent(errorMessage: String, viewModel: RecursosViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "¡Oh, no! Ocurrió un error:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        // --- CORRECCIÓN 3: El botón de reintentar ahora es funcional ---
        Button(onClick = { viewModel.cargarRecursos() }) { // Llama a la función del ViewModel
            Text("Reintentar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecursosViewSinDatos() {
    // Vista previa cuando no hay datos
    RecursosView()
}
