import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: Como usuario quiero regresar al inicio de sesión desde la pantalla de recuperación
//
// Prueba de Aceptacion / Caso de Prueba CB-19 (Escenario 5): Enlace "Volver al inicio de sesión"
//
// PASO 1. Abrir /reset-password
// PASO 2. Hacer clic en el enlace "← Volver al inicio de sesión"
//
// Resultado Esperado: La aplicación redirige de vuelta a /login
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB19_VolverAlLoginDesdeRecuperacionTest

public class CB19_VolverAlLoginDesdeRecuperacionTest {

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
    public void volverALoginDesdeResetPasswordTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/reset-password");

        /********** 2. Logica de la prueba **********/
        WebElement linkVolver = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']"))
        );
        linkVolver.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Debe regresar a /login");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
