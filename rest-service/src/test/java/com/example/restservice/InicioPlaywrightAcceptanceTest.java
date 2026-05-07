package com.example.restservice;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InicioPlaywrightAcceptanceTest {

    @LocalServerPort
    private int port;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(false) // Para ver el navegador durante la prueba
                .setSlowMo(1500) // Mantiene la lentitud para que le dé tiempo a cargar la UI
        );
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterEach
    void tearDown() {
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    void testFlujoCompletoEndToEnd() {
        // Generamos un usuario único para que no falle si la BD no se limpia entre tests
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        String testUser = "testuser" + uniqueSuffix;
        String testEmail = "test" + uniqueSuffix + "@test.com";

        // 1. REGISTRAR UN USUARIO NUEVO
        page.navigate("http://localhost:" + port + "/registro");
        page.getByLabel("Nombre de usuario").fill(testUser);
        page.getByLabel("Correo electrónico").fill(testEmail);
        page.getByLabel("Contraseña").fill("1234");
        page.getByText("Registrarse").click();

        // 2. INICIAR SESIÓN (Vaadin nos redirige al login tras el registro)
        page.waitForURL("**/login");
        page.fill("input[name='username']", testUser);
        page.fill("input[name='password']", "1234");
        page.press("input[name='password']", "Enter"); // Pulsamos Enter para enviar el formulario

        // 3. ESPERAR A QUE CARGUE EL INICIO
        page.waitForURL("**/inicio");
        assertThat(page).hasTitle("Inicio");

        // 4. Navegar a Categorías → crear una categoría
        page.locator("#menu-link-categorias").click();
        assertThat(page).hasTitle("Categorías");
        
        page.locator("#nombre-categoria input").fill("Deporte");
        page.locator("#btn-guardar-categoria").click();
        page.waitForTimeout(500); // Pequeña pausa para asegurar el guardado en BD

        // 5. Volver a la pagina de inicio (calendario)
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();

        // 6. Navegar a Tareas → crear una tarea usando esa categoría
        page.locator("#menu-link-tareas").click();
        assertThat(page).hasTitle("Gestión de Tareas");

        page.getByLabel("Título").first().fill("Entrenamiento de fútbol");
        page.getByLabel("Descripción").first().fill("Fuerza y velocidad");
        
        // Rellenar fechas mediante inyección de JS
        page.locator("#fecha-inicio-tarea").evaluate("el => { el.value = '2026-05-15T10:00'; el.dispatchEvent(new Event('change')); }");
        page.locator("#fecha-fin-tarea").evaluate("el => { el.value = '2026-05-15T12:00'; el.dispatchEvent(new Event('change')); }");

        // Seleccionar la categoría creada ("Deporte")
        page.locator("#categoria-tarea").click(); 
        page.locator("vaadin-combo-box-item").filter(new Locator.FilterOptions().setHasText("Deporte")).first().click();
        
        // Guardar la tarea
        page.locator("#btn-guardar-tarea").click();

        // 7. Mostrar el calendario
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();

        // 8. Modificar la tarea
        page.locator("#menu-link-tareas").click();
        
        // Abrir el desplegable de editar y seleccionar nuestra tarea
        page.locator("#selector-editar-tarea").click();
        page.locator("vaadin-combo-box-item").filter(new Locator.FilterOptions().setHasText("Entrenamiento de fútbol")).first().click();
        
        // Modificar el título y la descripción
        page.locator("#edit-titulo-tarea input").fill("Baloncesto (Modificada)");
        page.locator("#edit-desc-tarea input").fill("Mates y triples");
        page.locator("#btn-guardar-cambios-tarea").click();

        // 9. Mostrar de nuevo el calendario
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();

        // 10. Eliminar la tarea
        page.locator("#menu-link-tareas").click();
        
        // Abrir el desplegable de eliminar y seleccionar nuestra tarea ya modificada
        page.locator("#selector-eliminar-tarea").click();
        page.locator("vaadin-combo-box-item").filter(new Locator.FilterOptions().setHasText("Baloncesto (Modificada)")).first().click();
        
        // Clic en eliminar
        page.locator("#btn-eliminar-tarea").click();

        // 11. Volver a mostrar el calendario
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();
    }
}