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
// Historia de Usuario: Como usuario quiero poder visualizar la nueva contraseña que estoy escribiendo
//
// Prueba de Aceptacion / Caso de Prueba CB-16 (Escenario 5): Botón Mostrar/Ocultar en cambio de clave
//
// PASO 1. Abrir /password/primer-login e ingresar texto en Nueva contraseña
// PASO 2. Hacer clic en el botón "Mostrar" del primer campo
//
// Resultado Esperado: El campo cambia su atributo type a "text"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB16_MostrarOcultarNuevaPasswordTest

public class CB16_MostrarOcultarNuevaPasswordTest {

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
    public void mostrarNuevaContrasenaTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.prepararPrimerLogin(driver, "empleado1@cocal.com");

        /********** 2. Logica de la prueba **********/
        WebElement inputNueva = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("(//form//input)[1]"))
        );
        inputNueva.sendKeys("NuevaClave2026!");

        WebElement botonMostrar = driver.findElement(By.xpath("(//form//button[@type='button'])[1]"));
        botonMostrar.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        Assert.assertEquals(inputNueva.getAttribute("type"), "text");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
