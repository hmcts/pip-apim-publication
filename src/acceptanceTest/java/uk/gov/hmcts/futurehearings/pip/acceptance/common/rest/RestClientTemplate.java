package uk.gov.hmcts.futurehearings.pip.acceptance.common.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class RestClientTemplate {
    public static Response performRESTCall(final Map<String, String> headers,
                                           final String authorizationToken,
                                           final String payloadBody,
                                           final String requestURL,
                                           final Map<String, String> params,
                                           final HttpMethod httpMethod) {

        switch (httpMethod) {
            case POST:
                return RestAssured
                    .given()
                    .body(payloadBody)
                    .headers(headers)
                    .auth()
                    .oauth2(authorizationToken)
                    .basePath(requestURL)
                    .when()
                    .post()
                    .then().extract().response();
            case PUT:
                return RestAssured
                    .given()
                    .body(payloadBody)
                    .headers(headers)
                    .auth()
                    .oauth2(authorizationToken)
                    .basePath(requestURL)
                    .when()
                    .put()
                    .then().extract().response();
            case DELETE:
                return RestAssured
                    .given()
                    .body(payloadBody)
                    .headers(headers)
                    .auth()
                    .oauth2(authorizationToken)
                    .basePath(requestURL)
                    .when()
                    .delete()
                    .then().extract().response();
            case GET :
                if (Objects.isNull(params) || params.size() == 0) {
                    return RestAssured
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .when()
                        .get()
                        .then().extract().response();
                } else {
                    return RestAssured
                        .given()
                        .headers(headers)
                        .queryParams(params)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .when()
                        .get()
                        .then().extract().response();
                }
            default:
                throw new UnsupportedOperationException("This REST method is not supported!");
        }
    }
}
