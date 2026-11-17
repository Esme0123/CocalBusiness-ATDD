import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
// Historia de Usuario: Como usuario quiero cerrar mi sesión para invalidar el token y proteger mi cuenta
//
// Prueba de Aceptacion / Caso de Prueba CB-31 (Escenario 5): Cierre de sesión (Logout) e invalidación de JWT
//
// PASO 1. Iniciar sesión en /pagina-principal
// PASO 2. Hacer clic en "Cerrar sesión" y verificar que el token se elimina de localStorage
//
// Resultado Esperado: Redirige a /login y el token almacenado queda en null
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB31_CerrarSesionInvalidaTokenTest

public class CB31_CerrarSesionInvalidaTokenTest {

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
    public void cerrarSesionEliminaTokenTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.iniciarSesionAdmin(driver);

        /********** 2. Logica de la prueba **********/
        WebElement botonLogout = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Cerrar sesión')]"))
        );
        botonLogout.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        wait.until(ExpectedConditions.urlContains("/login"));
        Object tokenRestante = ((JavascriptExecutor) driver).executeScript("return window.localStorage.getItem('token');");
        Assert.assertNull(tokenRestante, "El token JWT debe eliminarse de localStorage al cerrar sesión");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
