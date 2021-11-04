package uk.gov.hmcts.futurehearings.pip.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.pip.unit.testing.utils.TestReporter;
import uk.gov.hmcts.futurehearings.pip.unit.testing.utils.TestUtilities;
import uk.gov.hmcts.reform.demo.Application;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.pip.unit.testing.utils.HealthCheckResponseVerifier.thenValidateResponseForHealthCheck;

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("pip-test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("GET /health - PIP Health Check")
public class GetPipHealthCheck {

    @Value("${healthCheckApiRootContext}")
    private String healthCheckApiRootContext;
    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${tokenURL}")
    private String tokenURL;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @Value("${grantType}")
    private String grantType;

    private static String accessToken;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeAll
    void setToken(){
        accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
    }

    @BeforeEach
    void initialiseValues() {
        headersAsMap.put("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Test for PIP Health Check")
    void testInvokeHealthCheckForPip() {
        final Response response = whenHeatlhCheckEndPointIsInvoked();
        thenValidateResponseForHealthCheck(response);
    }

    private Response whenHeatlhCheckEndPointIsInvoked() {
        return retrieveResourcesResponseForHealthCheck(targetInstance, healthCheckApiRootContext, headersAsMap);
    }

    private Response retrieveResourcesResponseForHealthCheck(final String basePath, final String api,
            final Map<String, Object> headersAsMap) {

        return given()
            .auth()
            .oauth2(accessToken)
            .headers(headersAsMap)
            .baseUri(basePath)
            .basePath(api)
            .when().get().then().extract().response();
    }

}
