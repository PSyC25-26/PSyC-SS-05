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
                .setHeadless(true)
                .setSlowMo(1500)
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
        // 1. Entrar desde la página de inicio (CORREGIDO A /inicio)
        page.navigate("http://localhost:" + port + "/inicio");
        assertThat(page).hasTitle("Inicio");

        // 2. Navegar a Categorías → crear una categoría
        page.locator("#menu-link-categorias").click();
        assertThat(page).hasTitle("Categorías");
        
        page.locator("#nombre-categoria input").fill("Deporte");
        page.locator("#btn-guardar-categoria").click();

        // 3. Volver a la pagina de inicio (calendario)
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();

        // 4. Navegar a Tareas → crear una tarea usando esa categoría
        page.locator("#menu-link-tareas").click();
        assertThat(page).hasTitle("Gestión de Tareas");

        page.getByLabel("Título").first().fill("Entrenamiento de fútbol");
        page.getByLabel("Descripción").first().fill("Fuerza y velocidad");
        
        // Rellenar fechas mediante inyección de JS (como tienes en tu ejemplo)
        page.locator("#fecha-inicio-tarea").evaluate("el => { el.value = '2026-04-15T10:00'; el.dispatchEvent(new Event('change')); }");
        page.locator("#fecha-fin-tarea").evaluate("el => { el.value = '2026-04-15T12:00'; el.dispatchEvent(new Event('change')); }");

        // Seleccionar la categoría creada ("Deporte")
        page.locator("#categoria-tarea").click(); 
        page.locator("vaadin-combo-box-item").filter(new Locator.FilterOptions().setHasText("Deporte")).first().click();
        
        // Guardar la tarea
        page.locator("#btn-guardar-tarea").click();

        // 5. Mostrar el calendario
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();

        // 6. Modificar la tarea
        page.locator("#menu-link-tareas").click();
        
        // Abrir el desplegable de editar y seleccionar nuestra tarea
        page.locator("#selector-editar-tarea").click();
        page.locator("vaadin-combo-box-item").filter(new Locator.FilterOptions().setHasText("Entrenamiento de fútbol")).first().click();
        
        // Modificar el título y la descripción
        page.locator("#edit-titulo-tarea input").fill("Baloncesto (Modificada)");
        page.locator("#edit-desc-tarea input").fill("Mates y triples");
        page.locator("#btn-guardar-cambios-tarea").click();

        // 7. Mostrar de nuevo el calendario
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();

        // 8. Eliminar la tarea
        page.locator("#menu-link-tareas").click();
        
        // Abrir el desplegable de eliminar y seleccionar nuestra tarea ya modificada
        page.locator("#selector-eliminar-tarea").click();
        page.locator("vaadin-combo-box-item").filter(new Locator.FilterOptions().setHasText("Baloncesto (Modificada)")).first().click();
        
        // Clic en eliminar
        page.locator("#btn-eliminar-tarea").click();

        // 9. Volver a mostrar el calendario
        page.locator("#menu-link-inicio").click();
        assertThat(page.locator("#calendario-principal")).isVisible();
    }
}