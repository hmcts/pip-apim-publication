package uk.gov.hmcts.futurehearings.pip.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.pip.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSystemHeaderPart;
import static uk.gov.hmcts.futurehearings.pip.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMap;

import org.springframework.http.MediaType;
import java.util.Collections;
import java.util.Map;

public class CommonHeaderHelper {

    public static Map<String, String> createStandardPayloadHeader() {
        return createHeader(MediaType.APPLICATION_JSON_VALUE);
    }


    private static Map<String, String> createHeader(final String contentType) {
        return Collections.unmodifiableMap(convertToMap(
            buildStandardSystemHeaderPart(contentType,null,null,null,null)));
    }
}
