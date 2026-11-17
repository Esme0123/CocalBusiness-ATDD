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
// Historia de Usuario: Como sistema debo notificar al usuario si intenta recuperar una cuenta inexistente
//
// Prueba de Aceptacion / Caso de Prueba CB-19 (Escenario 4): Solicitud con correo no registrado
//
// PASO 1. Abrir /reset-password e ingresar "no.existe.atdd@cocal.com"
// PASO 2. Hacer clic en "Enviar enlace" y esperar la respuesta del servidor
//
// Resultado Esperado: Se despliega el mensaje informativo de respuesta en pantalla
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB19_SolicitarRecuperacionCorreoNoRegistradoTest

public class CB19_SolicitarRecuperacionCorreoNoRegistradoTest {

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
    public void solicitarRecuperacionCorreoInexistenteTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/reset-password");

        /********** 2. Logica de la prueba **********/
        WebElement inputCorreo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))
        );
        inputCorreo.sendKeys("no.existe.atdd@cocal.com");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        WebElement mensajeRespuesta = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.mt-4.text-center"))
        );
        Assert.assertFalse(mensajeRespuesta.getText().trim().isEmpty(), "Debe mostrarse el mensaje del servidor");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
