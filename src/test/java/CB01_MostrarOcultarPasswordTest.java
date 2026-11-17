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
// Historia de Usuario: Como usuario quiero alternar la visibilidad de mi contraseña antes de iniciar sesión
//
// Prueba de Aceptacion / Caso de Prueba CB-1 (Escenario 2): Mostrar y ocultar contraseña en Login
//
// PASO 1. Ingresar a /login y escribir una contraseña
// PASO 2. Hacer clic en el botón "Mostrar" y luego en "Ocultar"
//
// Resultado Esperado: El atributo type del campo cambia de "password" a "text" y regresa a "password"
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB01_MostrarOcultarPasswordTest

public class CB01_MostrarOcultarPasswordTest {

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
    public void alternarVisibilidadPasswordLoginTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");
        WebElement inputPassword = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("(//form//input)[2]"))
        );
        inputPassword.sendKeys("Emp2026!");

        /********** 2. Logica de la prueba **********/
        WebElement botonToggle = driver.findElement(By.xpath("//label//button[@type='button']"));
        botonToggle.click();
        String tipoMostrado = inputPassword.getAttribute("type");

        botonToggle.click();
        String tipoOculto = inputPassword.getAttribute("type");

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        Assert.assertEquals(tipoMostrado, "text", "La contraseña debe mostrarse en texto plano");
        Assert.assertEquals(tipoOculto, "password", "La contraseña debe volver a ocultarse");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
