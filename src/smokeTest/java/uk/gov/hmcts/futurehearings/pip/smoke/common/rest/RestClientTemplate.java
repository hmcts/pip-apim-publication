package uk.gov.hmcts.futurehearings.pip.smoke.common.rest;

import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestClientTemplate {

    public static Response makeGetRequest(final Map<String, String> headers,
                                          final String rootContext) {
        return given()
            .headers(headers)
            .basePath(rootContext)
            .when().get();
    }
}
