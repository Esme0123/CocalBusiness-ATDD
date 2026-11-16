import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Propósito del módulo: Inyectar sesión de Administrador en localStorage
 * para omitir el 2FA y acceder directamente a /pagina-principal en producción.
 */
public class SessionHelper {

    private static final String TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiY29ycmVvIjoiYWRtaW5AY29jYWwuY29tIiwicm9sIjoiQURNSU4iLCJpZF9lbXByZXNhIjoxLCJpYXQiOjE3NjMzMzY3MDAsImV4cCI6MTg5MzQ1NjAwMH0.-UilXt-Oo-lNLVGSKRDEf4XiwFoax-gkBzvSp0zUesk";
    private static final String USER_JSON = "{\"id\":1,\"nombre\":\"Admin\",\"apellido\":\"Sistema\",\"correo\":\"admin@cocal.com\",\"rol\":\"ADMIN\",\"id_empresa\":1}";

    public static void iniciarSesionAdmin(WebDriver driver) {
        // 1. Cargar el dominio para habilitar localStorage
        driver.get(TestConfig.BASE_URL + "/login");

        // 2. Inyectar token y datos de usuario en el navegador
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('token', arguments[0]);", TOKEN_ADMIN);
        js.executeScript("window.localStorage.setItem('user', arguments[0]);", USER_JSON);
        js.executeScript("window.localStorage.setItem('usuario', arguments[0]);", USER_JSON);

        // 3. Redirigir a la ruta principal autenticada
        driver.get(TestConfig.BASE_URL + "/pagina-principal");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SEGUNDOS));
        wait.until(ExpectedConditions.urlContains("/pagina-principal"));
    }
}
