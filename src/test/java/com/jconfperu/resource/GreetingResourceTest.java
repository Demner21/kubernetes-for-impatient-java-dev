package com.jconfperu.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus on Kubernetes!"));
    }

    @Test
    void testInfoEndpoint() {
        given()
          .when().get("/hello/info")
          .then()
             .statusCode(200)
             .body("name", is("Quarkus Kubernetes Demo"))
             .body("version", is("1.0.0-SNAPSHOT"))
             .body("event", is("JConf Peru 2025"));
    }
}
