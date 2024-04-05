package org.DataImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class newRadio {
    private String radio_sap_site;
    private int radio_max_bw;
    private int radio_min_bw;

    private nrCategory radio_category;
}
