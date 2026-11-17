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
// Historia de Usuario: Como Administrador quiero navegar al módulo de alta de usuarios desde el panel principal
//
// Prueba de Aceptacion / Caso de Prueba CB-31 (Escenario 4): Navegación a /create-user desde panel Admin
//
// PASO 1. Iniciar sesión como ADMIN en /pagina-principal
// PASO 2. Hacer clic en el botón "Crear Usuario"
//
// Resultado Esperado: La aplicación redirige a la vista /create-user
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB31_NavegarCrearUsuarioAdminTest

public class CB31_NavegarCrearUsuarioAdminTest {

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
    public void navegarVistaCrearUsuarioTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.iniciarSesionAdmin(driver);

        /********** 2. Logica de la prueba **********/
        WebElement botonCrearUsuario = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Crear Usuario')]"))
        );
        botonCrearUsuario.click();

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        wait.until(ExpectedConditions.urlContains("/create-user"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/create-user"), "Debe navegar a /create-user");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
