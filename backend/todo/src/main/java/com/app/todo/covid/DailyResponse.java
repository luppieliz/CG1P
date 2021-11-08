package com.app.todo.covid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
@Setter
@Getter
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
