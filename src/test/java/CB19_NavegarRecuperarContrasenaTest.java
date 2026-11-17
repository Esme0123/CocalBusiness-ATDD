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
// Historia de Usuario: Como usuario que olvidó su clave quiero acceder al formulario de recuperación desde el login
//
// Prueba de Aceptacion / Caso de Prueba CB-19 (Escenario 1): Navegación a /reset-password
//
// PASO 1. Ingresar a /login
// PASO 2. Hacer clic en el enlace "¿Olvidaste tu contraseña?"
//
// Resultado Esperado: Redirige a /reset-password y muestra el título "Recuperar contraseña"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB19_NavegarRecuperarContrasenaTest

public class CB19_NavegarRecuperarContrasenaTest {

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
    public void navegarFormularioRecuperacionTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");

        /********** 2. Logica de la prueba **********/
        WebElement linkRecuperar = wait.until(
            ExpectedConditions.elementToBeClickable(By.linkText("¿Olvidaste tu contraseña?"))
        );
        linkRecuperar.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        wait.until(ExpectedConditions.urlContains("/reset-password"));
        WebElement titulo = driver.findElement(By.tagName("h2"));
        Assert.assertEquals(titulo.getText().trim(), "Recuperar contraseña");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
