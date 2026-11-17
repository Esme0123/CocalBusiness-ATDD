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
// Historia de Usuario: Como sistema debo rechazar el acceso cuando la contraseña ingresada es errónea
//
// Prueba de Aceptacion / Caso de Prueba CB-2 (Escenario 1): Rechazo por contraseña incorrecta
//
// PASO 1. Ingresar a /login
// PASO 2. Ingresar un correo válido y una contraseña errónea, luego hacer clic en "Entrar"
//
// Resultado Esperado: Se muestra un mensaje de error en pantalla y no se redirige a /pagina-principal
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB02_LoginContrasenaIncorrectaTest

public class CB02_LoginContrasenaIncorrectaTest {

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
    public void rechazarContrasenaIncorrectaTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");

        /********** 2. Logica de la prueba **********/
        WebElement inputCorreo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))
        );
        inputCorreo.sendKeys("empleado1@cocal.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("ClaveIncorrecta999!");
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
            Assert.assertFalse(mensajeRespuesta.getText().trim().isEmpty(), "Debe mostrarse mensaje de error");
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Debe permanecer en /login");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
