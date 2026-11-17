# Pruebas de Aceptación (ATDD) - Cocal Business

Repositorio de pruebas de aceptación automatizadas desarrolladas con **Java, Selenium WebDriver y TestNG** para el sistema **Cocal Business**, ejecutadas contra el entorno de producción.

## Entorno de Pruebas (Producción)
* **Frontend (Vue.js en Vercel):** https://cocal-bussines.vercel.app
* **Backend API (Node.js/Express en Render):** https://cocalbussines.onrender.com/api/docs/

## Requisitos previos para ejecución
1. Tener instalado **Java JDK 17+**, **Apache Maven 3.10+** y **Google Chrome**.
2. Conexión a internet para interactuar con el despliegue en Vercel y Render.

## Comandos de ejecución
* **Ejecutar todas las pruebas (20 pruebas de aceptación):**
  `mvn clean compile test`
* **Ejecutar una prueba específica:**
  `mvn clean compile test -Dtest=CB01_LoginFormularioVisibleTest`

## Matriz de Trazabilidad (Entregable #2 → ATDD)

| Integrante | Flujo / Módulo | Caso Base (Entregable #2) | Clases de Prueba Implementadas (5 por integrante) |
| :--- | :--- | :--- | :--- |
| **Esmeralda Medina** | Autenticación y Validación de Login | `CB-1`, `CB-2` | `CB01_LoginFormularioVisibleTest`, `CB01_MostrarOcultarPasswordTest`, `CB02_LoginContrasenaIncorrectaTest`, `CB02_LoginCorreoInexistenteTest`, `CB02_LoginCamposVaciosTest` |
| **Joan Riveros** | Control de Sesiones, Logout y Roles | `CB-31` | `CB31_BloqueoRutaProtegidaSinTokenTest`, `CB31_AccesoAdminMuestraCrearUsuarioTest`, `CB31_AccesoEmpleadoOcultaCrearUsuarioTest`, `CB31_NavegarCrearUsuarioAdminTest`, `CB31_CerrarSesionInvalidaTokenTest` |
| **Alina Mollinedo** | Cambio Obligatorio de Contraseña | `CB-16` | `CB16_CargaVistaPrimerLoginTest`, `CB16_ValidarCamposVaciosPrimerLoginTest`, `CB16_ValidarLongitudMinimaPasswordTest`, `CB16_ValidarContrasenasNoCoincidenTest`, `CB16_MostrarOcultarNuevaPasswordTest` |
| **Raquel Osorio** | Recuperación de Contraseña | `CB-19` | `CB19_NavegarRecuperarContrasenaTest`, `CB19_ValidarCorreoVacioRecuperacionTest`, `CB19_ValidarFormatoCorreoRecuperacionTest`, `CB19_SolicitarRecuperacionCorreoNoRegistradoTest`, `CB19_VolverAlLoginDesdeRecuperacionTest` |

## Métricas de Automatización (Elemento de Competencia 3)

| Integrante | Pruebas Automatizadas | Tiempo Total Invertido | Tiempo Promedio por Caso |
| :--- | :---: | :---: | :---: |
| Esmeralda Medina | 5 | 125 min | 25 min |
| Joan Riveros | 5 | 120 min | 24 min |
| Alina Mollinedo | 5 | 130 min | 26 min |
| Raquel Osorio | 5 | 125 min | 25 min |
| **Total / Promedio General** | **20** | **500 min** | **25 min / caso** |
