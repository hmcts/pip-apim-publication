package uk.gov.hmcts.futurehearings.pip.acceptance.lists;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.pip.acceptance.common.rest.RestClientTemplate;
import uk.gov.hmcts.futurehearings.pip.acceptance.common.test.PIPCommonTest;
import uk.gov.hmcts.reform.demo.Application;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.pip.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.pip.acceptance.common.security.OAuthTokenGenerator.generateOAuthToken;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListValidationTest extends PIPCommonTest {
    @Value("${listApiRootContext}")
    private String listApiRootContext;

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${token_apiURL}")
    private String token_apiURL;

    @Value("${token_apiTenantId}")
    private String token_apiTenantId;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    @BeforeAll
    public void initialiseValues() {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
            .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        String authorizationToken = generateOAuthToken (token_apiURL,
                                                        token_apiTenantId,
                                                        grantType, clientID,
                                                        clientSecret,
                                                        scope,
                                                        HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);
    }

    @Test
    void testPostList() {
        Response response = RestClientTemplate.performRESTCall(createStandardPayloadHeader(),
                                                               getAuthorizationToken(),
                                                               "Test",
                                                               listApiRootContext,
                                                               null,
                                                               HttpMethod.POST);

        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    @Test
    void testPostListUnauthorized() {
        Response response = RestClientTemplate.performRESTCall(createStandardPayloadHeader(),
                                                               "Test",
                                                               "Test",
                                                               listApiRootContext,
                                                               null,
                                                               HttpMethod.POST);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }

    @Test
    void testPutList() {
        Response response =  RestClientTemplate.performRESTCall(createStandardPayloadHeader(),
                                                                getAuthorizationToken(),
                                                                "Test",
                                                                listApiRootContext,
                                                                null,
                                                                HttpMethod.PUT);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
    }

    @Test
    void testPutListUnauthorized() {
        Response response =  RestClientTemplate.performRESTCall(createStandardPayloadHeader(),
                                                                "Test",
                                                                "Test",
                                                                listApiRootContext,
                                                                null,
                                                                HttpMethod.PUT);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }

    @Test
    void testDeleteList() {
        Response response = RestClientTemplate.performRESTCall(createStandardPayloadHeader(),
                                                               getAuthorizationToken(),
                                                               "Test",
                                                               listApiRootContext,
                                                               null,
                                                               HttpMethod.DELETE);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
    }

    @Test
    void testDeleteListUnauthorized() {
        Response response = RestClientTemplate.performRESTCall(createStandardPayloadHeader(),
                                                               "Test",
                                                               "Test",
                                                               listApiRootContext,
                                                               null,
                                                               HttpMethod.DELETE);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }
}
