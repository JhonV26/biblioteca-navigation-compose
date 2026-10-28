# 📚 BibliotecaApp - Práctica de Navigation Compose (Jetpack Compose)

Aplicación nativa de Android desarrollada con **Jetpack Compose**, **Kotlin** y **Navigation Compose**, implementando una arquitectura limpia y modular con navegación declarativa, paso de argumentos simples y múltiples, y persistencia reactiva en memoria.

---

## 🚀 Flujos de Navegación Implementados

La aplicación integra dos flujos completos con un total de **8 pantallas interconectadas**:

### 1️⃣ Flujo 1: Exploración y Reserva de Libros
* **Inicio Biblioteca (`InicioScreen`)**: Pantalla de bienvenida con acceso directo a la biblioteca y a reservas.
* **Lista de Libros (`ListaLibrosScreen`)**: Listado optimizado con `LazyColumn` de libros disponibles (Clean Code, Kotlin para Android, Arquitectura de Software, Jetpack Compose).
* **Detalle del Libro (`DetalleLibroScreen`)**:
  * **Argumento recibido:** `nombreLibro` (String codificado).
  * Muestra portada, autor, categoría y descripción detallada del ejemplar.
* **Confirmación de Reserva (`ConfirmacionScreen`)**:
  * **Argumentos recibidos:** `nombreLibro`, `fechaReserva`.
  * Limpieza del historial mediante `popUpTo` para evitar ciclos en la pila al pulsar atrás.

### 2️⃣ Flujo 2: Gestión y Renovación de Mis Reservas
* **Mis Reservas (`MisReservasScreen`)**: Vista de reservas activas con su estado (`Activa`), fecha y libro.
* **Detalle de Reserva (`DetalleReservaScreen`)**:
  * **Múltiples argumentos recibidos (3):** `nombreLibro`, `fechaReserva`, `codigoReserva`.
  * Acciones: Renovar Reserva o Cancelar Reserva (con diálogo de confirmación y eliminación reactiva).
* **Renovar Reserva (`RenovarReservaScreen`)**:
  * Formulario con estado mutable (`remember { mutableStateOf(...) }`) para seleccionar o ingresar la nueva fecha.
* **Renovación Exitosa (`RenovacionExitosaScreen`)**:
  * **Argumentos recibidos:** `nombreLibro`, `fechaNueva`.
  * Botón para volver a la lista de reservas limpiando la pila intermedia.

---

## 🏛️ Arquitectura del Proyecto (Clean Architecture por Capas)

```text
com.sena.bibliotecaapp/
│
├── data/                          # CAPA DE DATOS (Single Source of Truth)
│   ├── model/                     # Data Classes inmutables
│   │   ├── Libro.kt               # Representa el catálogo de libros
│   │   └── Reserva.kt             # Representa el estado de una reserva
│   └── repository/                # Patrón Repository (Singleton)
│       └── BibliotecaRepository.kt # Centraliza consultas, altas, bajas y modificaciones
│
├── navigation/                    # CAPA DE NAVEGACIÓN
│   ├── AppScreens.kt              # Sealed class con rutas Type-Safe y URLEncoding
│   └── AppNavigation.kt           # NavHost central, NavController y configuración de navArgument
│
├── ui/                            # CAPA DE PRESENTACIÓN (UI Declarativa)
│   ├── screens/                   # Pantallas completas
│   │   ├── inicio/                # InicioScreen.kt
│   │   ├── libros/                # ListaLibrosScreen.kt, DetalleLibroScreen.kt, ConfirmacionScreen.kt
│   │   └── reservas/              # MisReservasScreen.kt, DetalleReservaScreen.kt, RenovarReservaScreen.kt, RenovacionExitosaScreen.kt
│   ├── components/                # Componentes reusables (BookCoverView, InfoBadge, FilaDetalle)
│   └── theme/                     # Material 3 Theme, Colores y Tipografías
│
└── MainActivity.kt                # Single Activity Pattern (alojamiento reactivo de AppNavigation)
```

---

## 🧩 Parámetros y Argumentos que Viajan

| Parámetro | Tipo | Pantallas donde viaja | Propósito |
| :--- | :--- | :--- | :--- |
| `nombreLibro` | `NavType.StringType` | Detalle, Confirmación, Reservas, Renovación | Identifica el ejemplar seleccionado. |
| `fechaReserva`| `NavType.StringType` | Confirmación, Detalle Reserva, Renovar | Registra la fecha de reserva inicial (`15/06/2026`). |
| `codigoReserva`| `NavType.StringType` | Detalle Reserva, Renovar | Código único del ticket (`RES-2026-001`). |
| `fechaNueva`  | `NavType.StringType` | Renovación Exitosa | Almacena la fecha extendida (`30/06/2026`). |

---

## 🌿 Estrategia de Ramas en Git (GitFlow)

* `main`: Versión final estable lista para producción.
* `develop`: Integración continua de funcionalidades.
* `feature/setup-arquitectura`: Estructura inicial, dependencias, modelos y repositorio.
* `feature/flujo-libros`: Pantallas 1 a 4 (Exploración, Detalle y Reserva inicial).
* `feature/flujo-reservas`: Pantallas 5 a 8 (Mis Reservas, Detalle con múltiples args, Renovar y Éxito).

---

## 🛠️ Tecnologías y Librerías

* **Kotlin 2.2.10**
* **Jetpack Compose Material 3**
* **Navigation Compose 2.10.2**
* **Material Icons Extended**
* **Gradle Version Catalog (`libs.versions.toml`)**
