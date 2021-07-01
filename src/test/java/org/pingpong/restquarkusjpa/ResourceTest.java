package org.pingpong.restquarkusjpa;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

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

}