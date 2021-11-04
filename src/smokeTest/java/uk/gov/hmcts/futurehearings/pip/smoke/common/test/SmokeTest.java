package uk.gov.hmcts.futurehearings.pip.smoke.common.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import java.util.HashMap;
import java.util.Map;
import io.restassured.RestAssured;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.reform.demo.Application;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static uk.gov.hmcts.futurehearings.pip.smoke.common.header.factory.HeaderFactory.createHeader;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-smoke")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SmokeTest {

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

    private Map<String,String> headers = new HashMap<>();

    private String rootContext;

    @BeforeAll
    public void beforeAll(TestInfo info) {
        if(info.getTestClass().isPresent()){
            log.debug("Test execution Class Initiated: " + info.getTestClass().get().getName());
        } else {
            log.debug("Could not find class");
        }
    }

    @BeforeAll
    public void initialiseValues() {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
            .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        headers = createHeader();
    }
}
