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
// Historia de Usuario: Como Administrador quiero visualizar la opción de creación de usuarios en mi panel principal
//
// Prueba de Aceptacion / Caso de Prueba CB-31 (Escenario 2): Visibilidad de controles para rol ADMIN
//
// PASO 1. Iniciar sesión con rol ADMIN
// PASO 2. Verificar el contenido desplegado en /pagina-principal
//
// Resultado Esperado: Se muestra la bienvenida y el botón "Crear Usuario" exclusivo de Administrador
/****************************************/

// Para ejecutar en la linea de comando: mvn clean compile test -Dtest=CB31_AccesoAdminMuestraCrearUsuarioTest

public class CB31_AccesoAdminMuestraCrearUsuarioTest {

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
    public void verificarBotonAdminEnPaginaPrincipalTest() {

        /********** 1. Preparacion de la prueba **********/
        SessionHelper.iniciarSesionAdmin(driver);

        /********** 2. Logica de la prueba **********/
        WebElement titulo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        WebElement botonCrearUsuario = driver.findElement(
            By.xpath("//button[contains(text(),'Crear Usuario')]")
        );

        /********** 3. Verificacion de la situacion esperada - Assert **********/
        Assert.assertTrue(titulo.getText().contains("Bienvenido a la página principal"));
        Assert.assertTrue(botonCrearUsuario.isDisplayed(), "El botón Crear Usuario debe ser visible para ADMIN");
    }

    @AfterTest
    public void closeDriver() {
        if (driver != null) driver.quit();
    }
}
