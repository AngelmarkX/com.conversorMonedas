/**
 *
 * @author Miguel
 */
package com.conversormonedas;

import java.util.Map;

public class respuestaTasaDeCambios {
    private String result;
    private String documentation;
    private String terms_of_use;
    private long time_last_update_unix;
    private String time_last_update_utc;
    private long time_next_update_unix;
    private String time_next_update_utc;
    private String base_code;
    private Map<String, Double> conversion_rates;

    public String getResultado() {
        return result;
    }

    public Map<String, Double> getTasasDeConversion() {
        return conversion_rates;
    }
}
