package uk.gov.hmcts.futurehearings.pip.functional.lists;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.pip.functional.common.rest.RestClientTemplate;
import uk.gov.hmcts.futurehearings.pip.functional.common.test.FunctionalTest;
import uk.gov.hmcts.reform.demo.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.pip.functional.common.header.factory.HeaderFactory.createHeader;

@Slf4j
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("pip-functional")
public class ListAPITest extends FunctionalTest {

    @Value("${listApiRootContext}")
    protected String listApiRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
    }

    @Test
    public void testPOSTList() {
        Response response = RestClientTemplate.performRESTCall(createHeader(),
                                                               getAuthorizationToken(),
                                                               "Test",
                                                               listApiRootContext,
                                                               null, HttpMethod.POST);

        assertEquals(HttpStatus.CREATED.value(), response.statusCode());
    }

    @Test
    public void testPOSTListUnauthorized() {
        Response response = RestClientTemplate.performRESTCall(createHeader(),
                                                               "Test",
                                                               "Test",
                                                               listApiRootContext,
                                                               null, HttpMethod.POST);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }

    @Test
    public void testPUTList() {
        Response response = RestClientTemplate.performRESTCall(createHeader(),
                                                               getAuthorizationToken(),
                                                               "Test",
                                                               listApiRootContext,
                                                               null, HttpMethod.PUT);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
    }

    @Test
    public void testPUTListUnauthorized() {
        Response response = RestClientTemplate.performRESTCall(createHeader(),
                                                               "Test",
                                                               "Test",
                                                               listApiRootContext,
                                                               null, HttpMethod.PUT);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }

    @Test
    public void testDELETEList() {
        Response response = RestClientTemplate.performRESTCall(createHeader(),
                                                               getAuthorizationToken(),
                                                               "Test",
                                                               listApiRootContext,
                                                               null, HttpMethod.DELETE);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.statusCode());
    }

    @Test
    public void testDELETEListUnauthorized() {
        Response response = RestClientTemplate.performRESTCall(createHeader(),
                                                               "Test",
                                                               "Test",
                                                               listApiRootContext,
                                                               null, HttpMethod.DELETE);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
    }
}
