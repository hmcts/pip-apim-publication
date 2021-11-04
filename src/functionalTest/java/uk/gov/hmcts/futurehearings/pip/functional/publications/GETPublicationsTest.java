package uk.gov.hmcts.futurehearings.pip.functional.publications;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.pip.functional.common.test.FunctionalTest;
import uk.gov.hmcts.reform.demo.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.pip.functional.common.header.factory.HeaderFactory.createHeader;
import static uk.gov.hmcts.futurehearings.pip.functional.common.rest.RestClientTemplate.performRESTCall;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-functional")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GETPublicationsTest extends FunctionalTest {

    @Value("${publicationsRootContext}")
    private String publicationsRootContext;

    @Test
    void testGetPublication() {
        Response response = performRESTCall(createHeader(),
                                            getAuthorizationToken(),
                                            "Test",
                                            publicationsRootContext,
                                            null,
                                            HttpMethod.GET);

        assertEquals(HttpStatus.OK.value(), response.statusCode());
    }

    @Test
    void testGetPublicationNotAuthorized() {
        Response response = performRESTCall(createHeader(),
                                            "invalid token",
                                            "Test",
                                            publicationsRootContext,
                                            null,
                                            HttpMethod.GET);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }
}
