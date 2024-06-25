package com.tax.test.domain;

import com.tax.test.domain.tax.TaxDetail;
import com.tax.test.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
public class TaxDetailTest {

    @Test
    @DisplayName("json convert 테스트")
    void testJson__when__ThenSuccess() {
        // given
        String json = "{\"종합소득금액\":1200000000,\"이름\":\"손권\",\"소득공제\":{\"국민연금\":[{\"월\":\"2023-01\",\"공제액\":\"454,545.45\"},{\"월\":\"2023-02\",\"공제액\":\"454,545.45\"},{\"월\":\"2023-03\",\"공제액\":\"454,545\"},{\"월\":\"2023-04\",\"공제액\":\"454,545.45\"},{\"월\":\"2023-05\",\"공제액\":\"454,545\"},{\"월\":\"2023-06\",\"공제액\":\"454,545.45\"},{\"월\":\"2023-07\",\"공제액\":\"454,545.45\"},{\"월\":\"2023-08\",\"공제액\":\"454,545\"},{\"월\":\"2023-10\",\"공제액\":\"909,090.91\"},{\"월\":\"2023-11\",\"공제액\":\"909,090.90\"}],\"신용카드소득공제\":{\"month\":[{\"01\":\"83,333.33\"},{\"02\":\"0\"},{\"03\":\"83,333.33\"},{\"05\":\"83,333\"},{\"06\":\"0\"},{\"10\":\"83,333.33\"},{\"11\":\"0\"}],\"year\":2023},\"세액공제\":\"300,000\"}}";

        // when
        TaxDetail converted = JsonUtil.jsonToObject(json, TaxDetail.class);

        // then
        assertThat(converted.name(), equalTo("손권"));

    }
}
