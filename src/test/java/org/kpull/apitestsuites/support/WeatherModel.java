package org.kpull.apitestsuites.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherModel {

    private String dt;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "dt='" + dt + '\'' +
                '}';
    }
}
