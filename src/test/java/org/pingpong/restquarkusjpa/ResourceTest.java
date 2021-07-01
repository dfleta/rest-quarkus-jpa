package org.pingpong.restquarkusjpa;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.assertj.core.api.Assertions;

@QuarkusTest
public class ResourceTest {
    
    @Inject
    Resource resources;


    /**
	 * Implementa una clase Resources que
	 * añada una API REST a nuestra app.
	 * Injecta el servicio en Resources.
     */

    @Test
    public void test_injeccion() {
        Assertions.assertThat(resources.service).isNotNull();
    }

    /**
     * En el endpoint /itemcrudos
     * recibimos un TEXT con el mensaje
     * "CRUD de Items!""
     */

    @Test
    public void test_wellcome() {
        given()
            .contentType(ContentType.TEXT)
        .when()
            .get("/itemcrudos")
        .then()
            .statusCode(200)
            .body(is("CRUD de Items!"));
    }

    /**
     * La peticion /item/name del controlador
     * ha de retornar el nombre y la quality del 
     * primer item indicado de la base de datos.
	 * 
	 * La consulta ha de redirigirse al servicio.
	 * El servicio utiliza el repositorio
	 * para hacer la consulta a la base de datos.
     */
    @Test
    public void test_get_item() throws Exception {

        // Si el item existe la respuesta es 200
        given()
            .pathParam("name", "Aged Brie")
        .when()
            .get("/item/{name}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("name", equalTo("Aged Brie"),
                  "quality", equalTo(10));

        // Si la item NO existe la respuesta es 404
        given()
            .pathParam("name", "Varita de Sauco")
        .when()
            .get("/item/{name}")
        .then()
            .statusCode(404);
	}

    /**
     * Guarda un item empleando sólo el método POST en la url
     *    /item/create/
     * Los parametros post necesarios, en un JSON, son:
     *      "name" con el nombre del item
	 *      "quality" con la calidad del item
	 *      "type" con el tio de item.
     * 
     * La peticion ha de retornar el item JSON
     * y status code 201 si ha sido generada 
     * o 404 en caso contrario.
     * 
     * El item devuelto ha de ser IDENTICO al que 
     * acabas de crear, con el mismo name, quality y type,
     * y no cualquier otro item de la base de datos
     * con el mismo nombre.
	 * 
	 * La peticion ha de redirigirse al servicio.
	 * El servicio utiliza el repositorio
	 * para hacer la consulta a la base de datos.
     */
	@Test
    public void test_post() throws Exception {

        // El item se crea si todas sus propiedades son NO nulas ni vacias
		given()
            .body("{\"name\": \"Aged Brie\", \"quality\": \"50\", \"type\": \"MagicalItem\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when()
            .post("/item")
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("name", equalTo("Aged Brie"),
                  "quality", equalTo(50),
                  "type", equalTo("MagicalItem"));
    }
        
    /**
     * Asegurate que el item no se crea 
     * si alguna de sus propiedades es nula o vacia.
     * El controlador devuelve 400
     */
    @Test
    public void test_post_ko() {

        given()
            .body("{\"name\": \"Sorting Hat\", \"quality\": \"50\", \"type\": \"\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when()
            .post("/item")
        .then()
            .statusCode(400);

        given()
            .body("{\"name\": \"\", \"quality\": \"50\", \"type\": \"\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
        .when()
            .post("/item")
        .then()
            .statusCode(400);
    }
}