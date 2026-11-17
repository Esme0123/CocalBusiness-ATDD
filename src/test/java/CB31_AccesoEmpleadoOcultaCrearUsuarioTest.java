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
// Historia de Usuario: Como sistema debo ocultar opciones administrativas a usuarios con rol Empleado
//
// Prueba de Aceptacion / Caso de Prueba CB-31 (Escenario 3): Restricción visual de rol EMPLEADO
//
// PASO 1. Iniciar sesión con rol EMPLEADO
// PASO 2. Inspeccionar los botones disponibles en /pagina-principal
//
// Resultado Esperado: El botón "Crear Usuario" no se renderiza en el DOM para el Empleado
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB31_AccesoEmpleadoOcultaCrearUsuarioTest

public class CB31_AccesoEmpleadoOcultaCrearUsuarioTest {

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
    public void ocultarBotonAdminAEmpleadoTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.iniciarSesionEmpleado(driver);

        /********** 2. Logica de la prueba **********/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        List<WebElement> botonesAdmin = driver.findElements(
            By.xpath("//button[contains(text(),'Crear Usuario')]")
        );

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        Assert.assertTrue(botonesAdmin.isEmpty(), "El empleado no debe ver el botón Crear Usuario");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
