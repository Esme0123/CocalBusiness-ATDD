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
// Historia de Usuario: Como usuario quiero recibir alertas claras si intento enviar el login vacío
//
// Prueba de Aceptacion / Caso de Prueba CB-2 (Escenario 3): Validación de campos obligatorios en Login
//
// PASO 1. Ingresar a /login dejando correo y contraseña vacíos
// PASO 2. Hacer clic en el botón "Entrar"
//
// Resultado Esperado: Se muestran los mensajes "El correo es obligatorio" y "La contraseña es obligatoria"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB02_LoginCamposVaciosTest

public class CB02_LoginCamposVaciosTest {

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
    public void validarCamposVaciosLoginTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");

        /********** 2. Logica de la prueba **********/
        WebElement botonEntrar = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))
        );
        botonEntrar.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        List<WebElement> errores = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("p.error-text"))
        );
        Assert.assertEquals(errores.size(), 2, "Deben mostrarse dos mensajes de validación");
        Assert.assertEquals(errores.get(0).getText().trim(), "El correo es obligatorio");
        Assert.assertEquals(errores.get(1).getText().trim(), "La contraseña es obligatoria");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
