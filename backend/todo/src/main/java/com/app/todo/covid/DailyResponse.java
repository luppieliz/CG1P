package com.app.todo.covid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
public class DailyResponse implements Serializable {
    @SerializedName("country")
    private String country;

    @SerializedName("code")
    private String code;

    @SerializedName("confirmed")
    private int confirmedCase;

    @SerializedName("recovered")
    private int recoveredCase;

    @SerializedName("critical")
    private int criticalCase;

    @SerializedName("deaths")
    private int deathCase;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("latitude")
    private Double latitude;

    @JsonSerialize(using = LocalDateSerializer.class)
    @SerializedName("lastChange")
    private Date lastChange;

    @JsonSerialize(using = LocalDateSerializer.class)
    @SerializedName("lastUpdate")
    private Date lastUpdate;

    public DailyResponse() {}

    public DailyResponse(String country, String code, int confirmedCase, int recoveredCase, int criticalCase, int deathCase, Double longitude, Double latitude, Date lastChange, Date lastUpdate) {
        this.country = country;
        this.code = code;
        this.confirmedCase = confirmedCase;
        this.recoveredCase = recoveredCase;
        this.criticalCase = criticalCase;
        this.deathCase = deathCase;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lastChange = lastChange;
        this.lastUpdate = lastUpdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getConfirmedCase() {
        return confirmedCase;
    }

    public void setConfirmedCase(int confirmedCase) {
        this.confirmedCase = confirmedCase;
    }

    public int getRecoveredCase() {
        return recoveredCase;
    }

    public void setRecoveredCase(int recoveredCase) {
        this.recoveredCase = recoveredCase;
    }

    public int getCriticalCase() {
        return criticalCase;
    }

    public void setCriticalCase(int criticalCase) {
        this.criticalCase = criticalCase;
    }

    public int getDeathCase() {
        return deathCase;
    }

    public void setDeathCase(int deathCase) {
        this.deathCase = deathCase;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "DailyResponse{" +
                "country='" + country + '\'' +
                ", code='" + code + '\'' +
                ", confirmedCase=" + confirmedCase +
                ", recoveredCase=" + recoveredCase +
                ", criticalCase=" + criticalCase +
                ", deathCase=" + deathCase +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", lastChange=" + lastChange +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
