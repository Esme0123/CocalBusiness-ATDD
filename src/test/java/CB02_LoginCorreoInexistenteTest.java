import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
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
// Historia de Usuario: Como sistema debo impedir el ingreso de correos que no existen en la base de datos
//
// Prueba de Aceptacion / Caso de Prueba CB-2 (Escenario 2): Rechazo por correo inexistente
//
// PASO 1. Ingresar a /login
// PASO 2. Ingresar correo no registrado usuario.falso@cocal.com y pulsar "Entrar"
//
// Resultado Esperado: El backend rechaza la solicitud y muestra alerta de error en /login
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB02_LoginCorreoInexistenteTest

public class CB02_LoginCorreoInexistenteTest {

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
    public void rechazarCorreoInexistenteTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");

        /********** 2. Logica de la prueba **********/
        WebElement inputCorreo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))
        );
        inputCorreo.sendKeys("usuario.falso.atdd@cocal.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Emp2026!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        wait.until(ExpectedConditions.or(
            ExpectedConditions.alertIsPresent(),
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form p.text-center"))
        ));
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {
            WebElement mensajeRespuesta = driver.findElement(By.cssSelector("form p.text-center"));
            Assert.assertFalse(mensajeRespuesta.getText().trim().isEmpty(), "Debe mostrarse mensaje de rechazo");
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "No debe permitir el acceso");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
