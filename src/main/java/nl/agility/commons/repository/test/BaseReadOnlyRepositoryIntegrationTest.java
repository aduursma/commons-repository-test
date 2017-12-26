package nl.agility.commons.repository.test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public abstract class BaseReadOnlyRepositoryIntegrationTest extends BaseRestIntegrationTest {

    public static final String HAL_JSON = "application/hal+json;charset=UTF-8";

    @Value("${spring.data.rest.default-page-size}")
    protected Integer DEFAULT_PAGE_SIZE;

    protected abstract String resourceUri();

    protected abstract String existingResourceUri();

    protected abstract String nonExistingResourceUri();

    protected boolean isCollectionResource() {
        return true;
    }

    @Test
    public void verifyGetNonExistingUriIsHandled() {
        String nonExistingUri = "/non-existing";

        given()
                .when()
                .get(nonExistingUri)
                .then()
                .headers(RESPONSE_HEADERS)
                .statusCode(SC_NOT_FOUND)
                .body("timestamp", notNullValue())
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("exception", equalTo("org.springframework.web.servlet.NoHandlerFoundException"))
                .body("message", equalTo("No handler found for GET " + nonExistingUri))
                .body("path", equalTo(nonExistingUri))
        ;
    }

    @Test
    public void verifyGetCollectionResourceIsOk() {
        if (isCollectionResource()) {
            given()
                    .when()
                    .get(resourceUri())
                    .then()
                    .headers(RESPONSE_HEADERS)
                    .statusCode(SC_OK)
                    .contentType(HAL_JSON)
            ;
        }
    }

    @Test
    public void verifyGetCollectionResourceIsLessThanOrEqualToConfiguredMaximum() {
        if (isCollectionResource()) {
            given()
                    .when()
                    .get(resourceUri())
                    .then()
                    .headers(RESPONSE_HEADERS)
                    .statusCode(SC_OK)
                    .contentType(HAL_JSON)
                    .body("page.size", lessThanOrEqualTo(DEFAULT_PAGE_SIZE))
            ;
        }
    }

    @Test
    public void verifyPostCollectionResourceIsNotAllowed() {
        if (isCollectionResource()) {
            given()
                    .contentType(JSON)
                    .body("{}")
                    .when()
                    .post(resourceUri())
                    .then()
                    .headers(RESPONSE_HEADERS)
                    .statusCode(SC_METHOD_NOT_ALLOWED)
                    .contentType(HAL_JSON)
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(405))
                    .body("error", equalTo("Method Not Allowed"))
                    .body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException"))
                    .body("message", equalTo("Request method 'POST' not supported"))
                    .body("path", equalTo(resourceUri()))
            ;
        }
    }

    @Test
    public void verifyGetExistingItemResourceIsOk() {
        given()
                .when()
                .get(existingResourceUri())
                .then()
                .headers(RESPONSE_HEADERS)
                .statusCode(SC_OK)
                .contentType(HAL_JSON)
                .body(notNullValue())
        ;
    }

    @Test
    public void verifyGetNonExistingItemResourceIsNotFound() {
        given()
                .when()
                .get(nonExistingResourceUri())
                .then()
                .headers(RESPONSE_HEADERS)
                .statusCode(SC_NOT_FOUND)
                .body(isEmptyString())
        ;
    }

    @Test
    public void verifyPutItemResourceIsNotAllowed() {
        given()
                .contentType(JSON)
                .body("{}")
                .when()
                .put(existingResourceUri())
                .then()
                .headers(RESPONSE_HEADERS)
                .statusCode(SC_METHOD_NOT_ALLOWED)
                .contentType(HAL_JSON)
                .body("timestamp", notNullValue())
                .body("status", equalTo(405))
                .body("error", equalTo("Method Not Allowed"))
                .body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException"))
                .body("message", equalTo("Request method 'PUT' not supported"))
                .body("path", equalTo(existingResourceUri()))
        ;
    }

    @Test
    public void verifyPatchItemResourceIsNotAllowed() {
        given()
                .contentType(JSON)
                .body("{}")
                .when()
                .patch(existingResourceUri())
                .then()
                .headers(RESPONSE_HEADERS)
                .statusCode(SC_METHOD_NOT_ALLOWED)
                .contentType(HAL_JSON)
                .body("timestamp", notNullValue())
                .body("status", equalTo(405))
                .body("error", equalTo("Method Not Allowed"))
                .body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException"))
                .body("message", equalTo("Request method 'PATCH' not supported"))
                .body("path", equalTo(existingResourceUri()))
        ;
    }

    @Test
    public void verifyDeleteItemResourceIsNotAllowed() {
        given()
                .when()
                .delete(existingResourceUri())
                .then()
                .headers(RESPONSE_HEADERS)
                .statusCode(SC_METHOD_NOT_ALLOWED)
                .contentType(HAL_JSON)
                .body("timestamp", notNullValue())
                .body("status", equalTo(405))
                .body("error", equalTo("Method Not Allowed"))
                .body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException"))
                .body("message", equalTo("Request method 'DELETE' not supported"))
                .body("path", equalTo(existingResourceUri()))
        ;
    }

    @Test
    public void verifyGetAssociationResourceIsOk() {
        if (!isCollectionResource()) {
            given()
                    .when()
                    .get(resourceUri())
                    .then()
                    .headers(RESPONSE_HEADERS)
                    .statusCode(SC_OK)
                    .contentType(HAL_JSON)
            ;
        }
    }

    @Test
    public void verifyPutAssociationResourceIsNotAllowed() {
        if (!isCollectionResource()) {
            given()
                    .contentType(JSON)
                    .body("{}")
                    .when()
                    .put(existingResourceUri())
                    .then()
                    .headers(RESPONSE_HEADERS)
                    .statusCode(SC_METHOD_NOT_ALLOWED)
                    .contentType(HAL_JSON)
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(405))
                    .body("error", equalTo("Method Not Allowed"))
                    .body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException"))
                    .body("message", equalTo("Request method 'PUT' not supported"))
                    .body("path", equalTo(existingResourceUri()))
            ;
        }
    }

    @Test
    public void verifyDeleteAssociationResourceIsNotAllowed() {
        if (!isCollectionResource()) {
            given()
                    .when()
                    .delete(resourceUri())
                    .then()
                    .headers(RESPONSE_HEADERS)
                    .statusCode(SC_METHOD_NOT_ALLOWED)
                    .contentType(JSON)
                    .body("timestamp", notNullValue())
                    .body("status", equalTo(405))
                    .body("error", equalTo("Method Not Allowed"))
                    .body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException"))
                    .body("message", equalTo("Request method 'DELETE' not supported"))
                    .body("path", equalTo(resourceUri()))
            ;
        }
    }

}
