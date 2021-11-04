package uk.gov.hmcts.futurehearings.pip.functional.common.test;

import io.restassured.RestAssured;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.reform.demo.Application;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.pip.functional.common.header.factory.HeaderFactory.createHeader;
import static uk.gov.hmcts.futurehearings.pip.functional.common.security.OAuthTokenGenerator.generateOAuthToken;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-functional")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class FunctionalTest {

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

    private Map<String, String> headersAsMap = new HashMap<>();

    private String authorizationToken;

    @BeforeAll
    public void initialiseValues() {
        RestAssured.config = RestAssured.config()
            .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();

        this.authorizationToken = generateOAuthToken(token_apiURL,
                                                     token_apiTenantId,
                                                     grantType, clientID,
                                                     clientSecret,
                                                     scope,
                                                     HttpStatus.OK);

        this.setAuthorizationToken(authorizationToken);
        headersAsMap = createHeader();
    }
}
