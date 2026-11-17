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
// Historia de Usuario: Como sistema debo impedir enviar la solicitud de recuperación si el correo está vacío
//
// Prueba de Aceptacion / Caso de Prueba CB-19 (Escenario 2): Validación de campo correo obligatorio
//
// PASO 1. Abrir /reset-password dejando el campo de correo vacío
// PASO 2. Hacer clic en "Enviar enlace"
//
// Resultado Esperado: El navegador bloquea el envío y el campo presenta mensaje de validación obligatorio
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB19_ValidarCorreoVacioRecuperacionTest

public class CB19_ValidarCorreoVacioRecuperacionTest {

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
    public void validarCorreoObligatorioRecuperacionTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/reset-password");

        /********** 2. Logica de la prueba **********/
        WebElement inputCorreo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))
        );
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        String mensajeValidacion = inputCorreo.getAttribute("validationMessage");
        Assert.assertFalse(mensajeValidacion.isEmpty(), "Debe activarse la validación de campo obligatorio");
        Assert.assertTrue(driver.getCurrentUrl().contains("/reset-password"));
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
