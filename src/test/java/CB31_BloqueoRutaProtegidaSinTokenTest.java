import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: Como sistema debo impedir el acceso a rutas protegidas sin token de sesión
//
// Prueba de Aceptacion / Caso de Prueba CB-31 (Escenario 1): Bloqueo de /pagina-principal sin sesión
//
// PASO 1. Limpiar el almacenamiento local del navegador (localStorage)
// PASO 2. Intentar navegar directamente a /pagina-principal
//
// Resultado Esperado: El guardia de rutas redirige automáticamente a /login
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB31_BloqueoRutaProtegidaSinTokenTest

public class CB31_BloqueoRutaProtegidaSinTokenTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.TIMEOUT_SEGUNDOS));
    }

    @Test
    public void bloquearAccesoDirectoSinTokenTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");

        /********** 2. Logica de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/pagina-principal");

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Debe redirigir a /login si no hay token");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
