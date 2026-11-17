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
// Historia de Usuario: Como sistema debo validar el formato de correo electrónico antes de procesar la recuperación
//
// Prueba de Aceptacion / Caso de Prueba CB-19 (Escenario 3): Rechazo de formato de correo inválido
//
// PASO 1. Abrir /reset-password e ingresar "usuario_sin_arroba"
// PASO 2. Hacer clic en "Enviar enlace"
//
// Resultado Esperado: Se bloquea el envío por formato de correo inválido
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB19_ValidarFormatoCorreoRecuperacionTest

public class CB19_ValidarFormatoCorreoRecuperacionTest {

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
    public void validarFormatoEmailRecuperacionTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/reset-password");

        /********** 2. Logica de la prueba **********/
        WebElement inputCorreo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))
        );
        inputCorreo.sendKeys("usuario_sin_arroba");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        String mensajeValidacion = inputCorreo.getAttribute("validationMessage");
        Assert.assertFalse(mensajeValidacion.isEmpty(), "Debe rechazar un correo sin @");
        Assert.assertTrue(driver.getCurrentUrl().contains("/reset-password"));
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
