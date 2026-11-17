import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Propósito del módulo: Simular sesiones autenticadas por rol (ADMIN, DIRECTOR, GERENTE, EMPLEADO)
 * en localStorage para evaluar permisos y flujos sin bloqueo por 2FA.
 */
public class SessionHelper {

    public static final String TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiY29ycmVvIjoiYWRtaW5AY29jYWwuY29tIiwicm9sIjoiQURNSU4iLCJpZF9lbXByZXNhIjoxLCJpYXQiOjE3NjMzMzY3MDAsImV4cCI6MTg5MzQ1NjAwMH0.-UilXt-Oo-lNLVGSKRDEf4XiwFoax-gkBzvSp0zUesk";
    public static final String TOKEN_DIRECTOR = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiY29ycmVvIjoiZGlyZWN0b3JAY29jYWwuY29tIiwicm9sIjoiRElSRUNUT1IiLCJpZF9lbXByZXNhIjoxLCJpYXQiOjE3NjMzMzY3MDAsImV4cCI6MTg5MzQ1NjAwMH0.SAqYh75u-RgtGFW6v_UfB-_W5VqU0fJFssXglKS92r0";
    public static final String TOKEN_GERENTE = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiY29ycmVvIjoiZ2VyZW50ZS50aUBjb2NhbC5jb20iLCJyb2wiOiJHRVJFTlRFIiwiaWRfZW1wcmVzYSI6MSwiaWF0IjoxNzYzMzM2NzAwLCJleHAiOjE4OTM0NTYwMDB9.wswJd0VkqK8bYBg10Eu32ViBmAvWx2EhUJitUKoCiH8";
    public static final String TOKEN_EMPLEADO = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwiY29ycmVvIjoiZW1wbGVhZG8xQGNvY2FsLmNvbSIsInJvbCI6IkVNUExFQURPIiwiaWRfZW1wcmVzYSI6MSwiaWF0IjoxNzYzMzM2NzAwLCJleHAiOjE4OTM0NTYwMDB9.r76BLCgwgPnRJT4AP94rweNMpZLsfol6r5MacCVbp3w";

    public static void iniciarSesionConRol(WebDriver driver, String rol, String correo, int idUsuario) {
        String token;
        switch (rol) {
            case "ADMIN":
                token = TOKEN_ADMIN;
                break;
            case "DIRECTOR":
                token = TOKEN_DIRECTOR;
                break;
            case "GERENTE":
                token = TOKEN_GERENTE;
                break;
            default:
                token = TOKEN_EMPLEADO;
                break;
        }

        String usuarioJson = String.format(
            "{\"id\":%d,\"nombre\":\"Usuario\",\"apellido\":\"%s\",\"correo\":\"%s\",\"rol\":\"%s\",\"id_empresa\":1}",
            idUsuario, rol, correo, rol
        );

        driver.get(TestConfig.BASE_URL + "/login");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('token', arguments[0]);", token);
        js.executeScript("window.localStorage.setItem('rol', arguments[0]);", rol);
        js.executeScript("window.localStorage.setItem('usuario', arguments[0]);", usuarioJson);

        driver.get(TestConfig.BASE_URL + "/pagina-principal");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SEGUNDOS));
        wait.until(ExpectedConditions.urlContains("/pagina-principal"));
    }

    public static void iniciarSesionAdmin(WebDriver driver) {
        iniciarSesionConRol(driver, "ADMIN", "admin@cocal.com", 1);
    }

    public static void iniciarSesionEmpleado(WebDriver driver) {
        iniciarSesionConRol(driver, "EMPLEADO", "empleado1@cocal.com", 4);
    }

    public static void prepararPrimerLogin(WebDriver driver, String correo) {
        driver.get(TestConfig.BASE_URL + "/login");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('correo_cambio', arguments[0]);", correo);
        driver.get(TestConfig.BASE_URL + "/password/primer-login");
    }
}
