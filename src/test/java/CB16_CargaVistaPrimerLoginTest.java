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
// Historia de Usuario: Como nuevo usuario quiero ver mi correo asociado al ingresar al cambio obligatorio de clave
//
// Prueba de Aceptacion / Caso de Prueba CB-16 (Escenario 1): Carga de vista /password/primer-login
//
// PASO 1. Configurar el correo de cambio en sesión y abrir /password/primer-login
// PASO 2. Verificar el título y el correo asociado desplegado
//
// Resultado Esperado: Se muestra "Cambiar contraseña" junto al correo del usuario
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB16_CargaVistaPrimerLoginTest

public class CB16_CargaVistaPrimerLoginTest {

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
    public void verificarVistaPrimerLoginTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.prepararPrimerLogin(driver, "empleado1@cocal.com");

        /********** 2. Logica de la prueba **********/
        WebElement titulo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h2"))
        );
        WebElement textoCorreo = driver.findElement(By.xpath("//p[contains(.,'Correo asociado:')]"));

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        Assert.assertEquals(titulo.getText().trim(), "Cambiar contraseña");
        Assert.assertTrue(textoCorreo.getText().contains("empleado1@cocal.com"));
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
