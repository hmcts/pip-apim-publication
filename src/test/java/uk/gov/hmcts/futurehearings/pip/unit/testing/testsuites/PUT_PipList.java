package uk.gov.hmcts.futurehearings.pip.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
import static uk.gov.hmcts.futurehearings.pip.unit.testing.utils.HealthCheckResponseVerifier.thenValidateResponseForUpdateList;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("UPDATE /list - PIP Update List")
public class PUT_PipList {

    @Value("${listApiRootContext}")
    private String listApiRootContext;
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
    @DisplayName("Test for Update List from PIP")
    void testInvokeHealthCheckForPip() {
        final Response response = whenUpdateListIsInvoked();
        thenValidateResponseForUpdateList(response);
    }

    private Response whenUpdateListIsInvoked() {
        return retrieveResponseForUpdateList(targetInstance, listApiRootContext, headersAsMap);
    }

    private Response retrieveResponseForUpdateList(final String basePath, final String api, final Map<String, Object> headersAsMap) {
        return given()
            .auth()
            .oauth2(accessToken)
            .headers(headersAsMap)
            .baseUri(basePath)
            .basePath(api)
            .when().put().then().extract().response();
    }

}

