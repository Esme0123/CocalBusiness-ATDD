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
// Historia de Usuario: Como sistema debo verificar que la confirmación coincida con la nueva contraseña
//
// Prueba de Aceptacion / Caso de Prueba CB-16 (Escenario 4): Error cuando las contraseñas no coinciden
//
// PASO 1. Abrir /password/primer-login
// PASO 2. Ingresar "ClaveSegura2026" en nueva contraseña y "OtraClave999" en confirmación, luego guardar
//
// Resultado Esperado: Se muestra el mensaje "Las contraseñas no coinciden"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB16_ValidarContrasenasNoCoincidenTest

public class CB16_ValidarContrasenasNoCoincidenTest {

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
    public void validarContrasenasDiferentesTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.prepararPrimerLogin(driver, "empleado1@cocal.com");

        /********** 2. Logica de la prueba **********/
        WebElement inputNueva = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("(//form//input)[1]"))
        );
        WebElement inputConfirmar = driver.findElement(By.xpath("(//form//input)[2]"));

        inputNueva.sendKeys("ClaveSegura2026");
        inputConfirmar.sendKeys("OtraClave999");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        WebElement errorCoincidencia = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.error-text"))
        );
        Assert.assertEquals(errorCoincidencia.getText().trim(), "Las contraseñas no coinciden");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
