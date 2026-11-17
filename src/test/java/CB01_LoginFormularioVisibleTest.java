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
// Historia de Usuario: Como usuario registrado quiero acceder al formulario de inicio de sesión de Cocal Business
//
// Prueba de Aceptacion / Caso de Prueba CB-1 (Escenario 1): Despliegue correcto del formulario de login
//
// PASO 1. Navegar a la URL de producción /login
// PASO 2. Localizar los campos de correo electrónico, contraseña y el botón Entrar
//
// Resultado Esperado: Todos los elementos del formulario son visibles y están habilitados
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB01_LoginFormularioVisibleTest

public class CB01_LoginFormularioVisibleTest {

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
    public void verificarFormularioLoginVisibleTest() {

        /********** 1. Preparacion de la prueba **********/
        driver.get(TestConfig.BASE_URL + "/login");

        /********** 2. Logica de la prueba **********/
        WebElement inputCorreo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))
        );
        WebElement inputPassword = driver.findElement(By.cssSelector("input[type='password']"));
        WebElement botonEntrar = driver.findElement(By.cssSelector("button[type='submit']"));

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        Assert.assertTrue(inputCorreo.isDisplayed() && inputCorreo.isEnabled(), "El campo correo debe estar visible");
        Assert.assertTrue(inputPassword.isDisplayed() && inputPassword.isEnabled(), "El campo contraseña debe estar visible");
        Assert.assertEquals(botonEntrar.getText().trim(), "Entrar");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
