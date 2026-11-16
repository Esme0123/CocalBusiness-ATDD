# Pruebas de Aceptación (ATDD) - Cocal Business

Repositorio de pruebas de aceptación automatizadas desarrolladas con **Java, Selenium WebDriver y TestNG** para el sistema **Cocal Business**.

## Requisitos previos para ejecución
1. Tener instalado **Java JDK 17+**, **Maven** y **Google Chrome**.
2. Tener levantado el servidor Backend (Node.js/Express + PostgreSQL) y Frontend (Vue.js en `http://localhost:5173`).

## Comandos de ejecución
* **Ejecutar todas las pruebas (20 pruebas):**
  `mvn clean compile test`
* **Ejecutar una prueba específica:**
  `mvn clean compile test -Dtest=CB01_LoginExitosoTest`

## Matriz de Trazabilidad (Entregable #2 → ATDD)

| Integrante | Flujo / Módulo | Caso Base (Entregable #2) | Clases de Prueba Implementadas (5 por integrante) |
| :--- | :--- | :--- | :--- |
| **Esmeralda Medina** | Autenticación y Sesiones | `CB-1` | `CB01_LoginExitosoTest`, `CB01_LoginDirectorTest`, `CB02_PasswordIncorrectoTest`, `CB02_CorreoInexistenteTest`, `CB31_CerrarSesionTest` |
| **Joan Riveros** | Gestión Organizacional | `CB-4` | `CB04_CrearDepartamentoTest`, `CB04_ValidarCamposDeptoTest`, `CB05_RestriccionAccesoDeptoTest`, `CB35_CrearEquipoTest`, `CB38_BloquearEliminarDeptoTest` |
| **Alina Mollinedo** | Calendario Colaborativo | `CB-6` | `CB06_CrearEventoDeptoTest`, `CB06_ValidarCamposEventoTest`, `CB07_ConflictoHorarioTest`, `CB08_CambiarVistaCalendarioTest`, `CB42_FiltrarEventosNivelTest` |
| **Raquel Osorio** | Gestión de Recursos | `CB-10`, `CB-11` | `CB09_RegistrarRecursoTest`, `CB10_ReservarSalaDisponibleTest`, `CB10_PrevenirReservaDuplicadaTest`, `CB11_BloqueoMantenimientoTest`, `CB48_CancelarReservaTest` |
