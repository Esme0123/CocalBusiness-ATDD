# Pruebas de Aceptación (ATDD) - Cocal Business

Repositorio de pruebas de aceptación automatizadas desarrolladas con **Java, Selenium WebDriver y TestNG** para el sistema **Cocal Business**, ejecutadas contra el entorno de producción.

## Entorno de Pruebas (Producción)
* **Frontend (Vue.js en Vercel):** https://cocal-bussines.vercel.app
* **Backend API (Node.js/Express en Render):** https://cocalbussines.onrender.com/api/docs/

## Requisitos previos para ejecución
1. Tener instalado **Java JDK 17+**, **Apache Maven 3.10+** y **Google Chrome**.
2. Conexión a internet para interactuar con el despliegue en Vercel y Render.

## Comandos de ejecución
* **Ejecutar todas las pruebas (20 pruebas de aceptación secuenciales mediante `testng.xml`):**
  `mvn clean compile test`
* **Ejecutar una prueba específica:**
  `mvn clean compile test -Dtest=CB01_LoginFormularioVisibleTest`

## Proceso Identificado y los 5 Casos de Prueba Base (Entregable #2)

* **Proceso / Flujo seleccionado:** Gestión de Autenticación, Control de Sesiones por Rol y Recuperación de Contraseñas.

| ID Caso (Entregable #2) | Nombre del Caso de Prueba Base | Descripción y Criterio de Aceptación |
| :--- | :--- | :--- |
| **`CB-1`** | Despliegue y funcionalidad del formulario de Login | Verifica que `/login` cargue los campos de correo, contraseña, el botón `Entrar` y permita alternar la visibilidad de la clave. |
| **`CB-2`** | Rechazo de inicio de sesión inválido | Valida que el sistema impida el acceso y muestre alertas ante campos vacíos, contraseña incorrecta o correo no registrado. |
| **`CB-16`** | Cambio obligatorio de contraseña en primer ingreso | Comprueba las validaciones en `/password/primer-login` (campos obligatorios, longitud mínima de 6 caracteres y coincidencia de claves). |
| **`CB-19`** | Solicitud de recuperación de contraseña | Verifica la navegación a `/reset-password`, validación de formato de correo, respuesta ante correos no registrados y retorno al login. |
| **`CB-31`** | Control de sesión activa, permisos por rol y Logout | Valida el bloqueo de `/pagina-principal` sin token, la visibilidad diferenciada entre roles (`ADMIN` vs `EMPLEADO`) y la eliminación del JWT al cerrar sesión. |

## Matriz de Trazabilidad (Entregable #2 → ATDD)

| Integrante | Flujo / Módulo | Caso Base | Clases de Prueba Implementadas (5 por integrante) |
| :--- | :--- | :--- | :--- |
| **Esmeralda Medina** | Autenticación y Validación de Login | `CB-1`, `CB-2` | `CB01_LoginFormularioVisibleTest`, `CB01_MostrarOcultarPasswordTest`, `CB02_LoginContrasenaIncorrectaTest`, `CB02_LoginCorreoInexistenteTest`, `CB02_LoginCamposVaciosTest` |
| **Joan Riveros** | Control de Sesiones, Logout y Roles | `CB-31` | `CB31_BloqueoRutaProtegidaSinTokenTest`, `CB31_AccesoAdminMuestraCrearUsuarioTest`, `CB31_AccesoEmpleadoOcultaCrearUsuarioTest`, `CB31_NavegarCrearUsuarioAdminTest`, `CB31_CerrarSesionInvalidaTokenTest` |
| **Alina Mollinedo** | Cambio Obligatorio de Contraseña | `CB-16` | `CB16_CargaVistaPrimerLoginTest`, `CB16_ValidarCamposVaciosPrimerLoginTest`, `CB16_ValidarLongitudMinimaPasswordTest`, `CB16_ValidarContrasenasNoCoincidenTest`, `CB16_MostrarOcultarNuevaPasswordTest` |
| **Raquel Osorio** | Recuperación de Contraseña | `CB-19` | `CB19_NavegarRecuperarContrasenaTest`, `CB19_ValidarCorreoVacioRecuperacionTest`, `CB19_ValidarFormatoCorreoRecuperacionTest`, `CB19_SolicitarRecuperacionCorreoNoRegistradoTest`, `CB19_VolverAlLoginDesdeRecuperacionTest` |

## Métricas de Tiempo (Automatización vs. Ejecución Real)

| Integrante | Pruebas | Tiempo Total Automatización (Programación) | Promedio Automatización / Caso | Tiempo Total Ejecución (TestNG Report) | Promedio Ejecución / Prueba |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Esmeralda Medina** | 5 | 125 min | 25 min | 11.14 s (11,142 ms) | 2.23 s |
| **Joan Riveros** | 5 | 120 min | 24 min | 6.17 s (6,172 ms) | 1.23 s |
| **Alina Mollinedo** | 5 | 130 min | 26 min | 6.65 s (6,654 ms) | 1.33 s |
| **Raquel Osorio** | 5 | 125 min | 25 min | 6.30 s (6,301 ms) | 1.26 s |
| **Total / Promedio General** | **20** | **500 min** | **25 min / caso** | **30.27 s (30,269 ms)** | **1.51 s / prueba** |
