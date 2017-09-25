package at.fhhgb.dab.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.text.SimpleDateFormat;
import java.util.Random;

@DynamoDBTable(tableName="PositionReport")
public class PositionReport {
    private String trackerId;
    private String timestamp;
    private float altitude;
    private float longitude;
    private float latitude;

    @DynamoDBIgnore
    public static SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @DynamoDBHashKey(attributeName="TrackerId")
    public String getTrackerId() {
        return this.trackerId;
    }
    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    @DynamoDBHashKey(attributeName = "ReportTime")
    public String getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = "Altitude")
    public float getAltitude() {
        return this.altitude;
    }
    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    @DynamoDBAttribute(attributeName = "Longitude")
    public float getLongitude() {
        return this.longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @DynamoDBAttribute(attributeName = "Latitude")
    public float getLatitude() {
        return this.latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
