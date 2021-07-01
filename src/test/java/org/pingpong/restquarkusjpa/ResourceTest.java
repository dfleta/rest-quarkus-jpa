package org.pingpong.restquarkusjpa;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;

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

}