package uk.gov.hmcts.futurehearings.pip.functional.common.header.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HeaderFactory {

    public static Map<String,String> createHeader() {
        Map<String,String> headersAsMap = new HashMap<String,String>();
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        return Collections.unmodifiableMap(headersAsMap);
    }
}
