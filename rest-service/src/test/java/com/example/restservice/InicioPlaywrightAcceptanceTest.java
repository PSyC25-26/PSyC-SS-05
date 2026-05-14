package com.example.restservice;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * @class InicioPlaywrightAcceptanceTest
 * @brief Prueba de aceptación End-To-End utilizando Playwright.
 * 
 * Verifica el funcionamiento completo de la aplicación
 * simulando la interacción real de un usuario desde el navegador.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InicioPlaywrightAcceptanceTest {

    @LocalServerPort
    private int port;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    /**
     * @brief Inicializa el navegador antes de cada prueba.
     */
    @BeforeEach
    void setUp() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setSlowMo(1500)
        );

        browserContext = browser.newContext();

        page = browserContext.newPage();
    }

    /**
     * @brief Cierra el navegador y libera recursos tras cada prueba.
     */
    @AfterEach
    void tearDown() {

        if (browserContext != null) browserContext.close();

        if (browser != null) browser.close();

        if (playwright != null) playwright.close();
    }

    /**
     * @brief Comprueba el flujo completo de uso de la aplicación.
     * 
     * El test realiza:
     * - Registro de usuario
     * - Inicio de sesión
     * - Creación de categorías
     * - Creación de tareas
     * - Modificación de tareas
     * - Eliminación de tareas
     * - Visualización del calendario
     */
    @Test
    void testFlujoCompletoEndToEnd() {

        String uniqueSuffix = String.valueOf(System.currentTimeMillis());

        String testUser = "testuser" + uniqueSuffix;

        String testEmail = "test" + uniqueSuffix + "@test.com";

        // Registro de usuario
        page.navigate("http://localhost:" + port + "/registro");

        page.getByLabel("Nombre de usuario").fill(testUser);

        page.getByLabel("Correo electrónico").fill(testEmail);

        page.getByLabel("Contraseña").fill("1234");

        page.getByText("Registrarse").click();

        // Inicio de sesión
        page.waitForURL("**/login");

        page.fill("input[name='username']", testUser);

        page.fill("input[name='password']", "1234");

        page.press("input[name='password']", "Enter");

        // Comprobación de carga del inicio
        page.waitForURL("**/inicio");

        assertThat(page).hasTitle("Inicio");

        // Creación de categoría
        page.locator("#menu-link-categorias").click();

        assertThat(page).hasTitle("Categorías");
        
        page.locator("#nombre-categoria input").fill("Deporte");

        page.locator("#btn-guardar-categoria").click();

        page.waitForTimeout(500);

        // Visualización del calendario
        page.locator("#menu-link-inicio").click();

        assertThat(page.locator("#calendario-principal")).isVisible();

        // Creación de tarea
        page.locator("#menu-link-tareas").click();

        assertThat(page).hasTitle("Gestión de Tareas");

        page.getByLabel("Título").first().fill("Entrenamiento de fútbol");

        page.getByLabel("Descripción").first().fill("Fuerza y velocidad");
        
        page.locator("#fecha-inicio-tarea")
            .evaluate("el => { el.value = '2026-05-15T10:00'; el.dispatchEvent(new Event('change')); }");

        page.locator("#fecha-fin-tarea")
            .evaluate("el => { el.value = '2026-05-15T12:00'; el.dispatchEvent(new Event('change')); }");

        page.locator("#categoria-tarea").click();

        page.locator("vaadin-combo-box-item")
            .filter(new Locator.FilterOptions().setHasText("Deporte"))
            .first()
            .click();
        
        page.locator("#btn-guardar-tarea").click();

        // Comprobación del calendario
        page.locator("#menu-link-inicio").click();

        assertThat(page.locator("#calendario-principal")).isVisible();

        // Modificación de tarea
        page.locator("#menu-link-tareas").click();
        
        page.locator("#selector-editar-tarea").click();

        page.locator("vaadin-combo-box-item")
            .filter(new Locator.FilterOptions().setHasText("Entrenamiento de fútbol"))
            .first()
            .click();
        
        page.locator("#edit-titulo-tarea input").fill("Baloncesto (Modificada)");

        page.locator("#edit-desc-tarea input").fill("Mates y triples");

        page.locator("#btn-guardar-cambios-tarea").click();

        // Comprobación del calendario tras modificación
        page.locator("#menu-link-inicio").click();

        assertThat(page.locator("#calendario-principal")).isVisible();

        // Eliminación de tarea
        page.locator("#menu-link-tareas").click();
        
        page.locator("#selector-eliminar-tarea").click();

        page.locator("vaadin-combo-box-item")
            .filter(new Locator.FilterOptions().setHasText("Baloncesto (Modificada)"))
            .first()
            .click();
        
        page.locator("#btn-eliminar-tarea").click();

        // Comprobación final del calendario
        page.locator("#menu-link-inicio").click();

        assertThat(page.locator("#calendario-principal")).isVisible();
    }
}