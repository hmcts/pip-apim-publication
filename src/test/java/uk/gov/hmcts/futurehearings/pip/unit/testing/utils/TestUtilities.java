package uk.gov.hmcts.futurehearings.pip.unit.testing.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.expect;

public class TestUtilities {

    public static String getToken(String grantType, String clientID, String clientSecret, String tokenURL, String scope) {
        final String body = String.format("grant_type=%s&client_id=%s&client_secret=%s&scope=%s", grantType, clientID, clientSecret, scope);
        Response response = expect().that().statusCode(200).
            given()
            .body(body)
            .contentType(ContentType.URLENC)
            .baseUri(tokenURL)
            .when()
            .post()
            .then()
            .extract()
            .response();

        return response.jsonPath().getString("access_token");

    }

}
