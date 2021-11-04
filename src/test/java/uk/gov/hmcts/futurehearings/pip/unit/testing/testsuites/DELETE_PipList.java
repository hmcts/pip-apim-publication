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
import static uk.gov.hmcts.futurehearings.pip.unit.testing.utils.HealthCheckResponseVerifier.thenValidateResponseForDeleteList;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /list - PIP Delete List")
public class DELETE_PipList {

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
    @DisplayName("Test for Delete List from PIP")
    void testInvokeHealthCheckForPip() {
        final Response response = whenDeleteListIsInvoked();
        thenValidateResponseForDeleteList(response);
    }

    private Response whenDeleteListIsInvoked() {
        return retrieveResponseForDeleteList(targetInstance, listApiRootContext, headersAsMap);
    }

    private Response retrieveResponseForDeleteList(final String basePath, final String api, final Map<String, Object> headersAsMap) {
        return given()
            .auth()
            .oauth2(accessToken)
            .headers(headersAsMap)
            .baseUri(basePath)
            .basePath(api)
            .when().delete().then().extract().response();
    }

}
