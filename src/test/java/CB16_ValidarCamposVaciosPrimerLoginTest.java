import java.time.Duration;
import java.util.List;
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
// Historia de Usuario: Como sistema debo impedir guardar una contraseña vacía en el primer ingreso
//
// Prueba de Aceptacion / Caso de Prueba CB-16 (Escenario 2): Validación de campos obligatorios
//
// PASO 1. Abrir /password/primer-login sin ingresar contraseñas
// PASO 2. Hacer clic en "Guardar nueva contraseña"
//
// Resultado Esperado: Se muestran las alertas "La contraseña es obligatoria" y "Confirma tu contraseña"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB16_ValidarCamposVaciosPrimerLoginTest

public class CB16_ValidarCamposVaciosPrimerLoginTest {

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
    public void validarCamposVaciosCambioClaveTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.prepararPrimerLogin(driver, "empleado1@cocal.com");

        /********** 2. Logica de la prueba **********/
        WebElement botonGuardar = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))
        );
        botonGuardar.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        List<WebElement> errores = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("p.error-text"))
        );
        Assert.assertEquals(errores.size(), 2);
        Assert.assertEquals(errores.get(0).getText().trim(), "La contraseña es obligatoria");
        Assert.assertEquals(errores.get(1).getText().trim(), "Confirma tu contraseña");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
