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
// Historia de Usuario: Como sistema debo exigir al menos 6 caracteres en la nueva contraseña
//
// Prueba de Aceptacion / Caso de Prueba CB-16 (Escenario 3): Validación de longitud mínima
//
// PASO 1. Abrir /password/primer-login
// PASO 2. Ingresar "12345" (5 caracteres) en ambos campos y pulsar "Guardar nueva contraseña"
//
// Resultado Esperado: Se despliega el error "Debe tener al menos 6 caracteres"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB16_ValidarLongitudMinimaPasswordTest

public class CB16_ValidarLongitudMinimaPasswordTest {

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
    public void validarLongitudMinimaClaveTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.prepararPrimerLogin(driver, "empleado1@cocal.com");

        /********** 2. Logica de la prueba **********/
        WebElement inputNueva = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("(//form//input)[1]"))
        );
        WebElement inputConfirmar = driver.findElement(By.xpath("(//form//input)[2]"));

        inputNueva.sendKeys("12345");
        inputConfirmar.sendKeys("12345");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        WebElement errorLongitud = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.error-text"))
        );
        Assert.assertEquals(errorLongitud.getText().trim(), "Debe tener al menos 6 caracteres");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
