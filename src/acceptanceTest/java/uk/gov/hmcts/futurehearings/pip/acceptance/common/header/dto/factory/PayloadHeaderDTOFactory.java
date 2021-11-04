package uk.gov.hmcts.futurehearings.pip.acceptance.common.header.dto.factory;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import uk.gov.hmcts.futurehearings.pip.acceptance.common.header.dto.SystemHeaderDTO;

import java.util.HashMap;
import java.util.Map;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PayloadHeaderDTOFactory {

    public static SystemHeaderDTO buildStandardSystemHeaderPart(final String contentType,
                                                                     final String accept,
                                                                     final String authorization,
                                                                     final String contentEncoding,
                                                                     final String cacheControl) {
        return SystemHeaderDTO.builder()
            .contentType(contentType)
            .accept(accept)
            .authorization(authorization)
            .contentEncoding(contentEncoding)
            .cacheControl(cacheControl)
            .build();
    }

    public static Map<String, String> convertToMap(final SystemHeaderDTO systemHeaderDTO) {
        final Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", systemHeaderDTO.contentType());
        return headerMap;
    }
}
