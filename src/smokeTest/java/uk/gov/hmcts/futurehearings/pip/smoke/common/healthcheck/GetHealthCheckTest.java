package uk.gov.hmcts.futurehearings.pip.smoke.common.healthcheck;

import io.restassured.response.Response;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.reform.demo.Application;
import uk.gov.hmcts.futurehearings.pip.smoke.common.test.SmokeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.pip.smoke.common.header.factory.HeaderFactory.createHeader;
import static uk.gov.hmcts.futurehearings.pip.smoke.common.rest.RestClientTemplate.makeGetRequest;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("pip-smoke")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetHealthCheckTest extends SmokeTest {

    @Value("${healthCheckRootContext}")
    private String healthCheckRootContext;

    @Test
    void testGetHealthCheck() {
        Response response = makeGetRequest(createHeader(),
                                           healthCheckRootContext);

        assertEquals(HttpStatus.OK.value(), response.statusCode());
    }
}
