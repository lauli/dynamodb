package at.fhhgb.dab.service;

import at.fhhgb.dab.models.PositionReport;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MockPositionTrackingService {
    protected AmazonDynamoDB client;
    protected Timer timer;

    protected TimerTask sendRandomReportsTask = new TimerTask() {
        @Override
        public void run() {
            // send position reports
            PositionReport randomPositionReport = generateRandomPositionReport();
            savePositionReport(randomPositionReport);
            System.out.println("Position report sent!");
        }
    };

    public MockPositionTrackingService() {
        this.client = AmazonDynamoDBClientBuilder.standard().build();
    }

    public void startTracking() {
        timer = new Timer();
        timer.scheduleAtFixedRate(sendRandomReportsTask, 0, 1000);
    }

    public void stopTracking() {
        timer.cancel();
    }

    protected PositionReport generateRandomPositionReport() {
        DateFormat dateFormatter = PositionReport.getDateFormatter();

        PositionReport randomPositionReport = new PositionReport();

        randomPositionReport.setTrackerId("ABCDEFGH");
        randomPositionReport.setTimestamp(dateFormatter.format(new Date()));
        randomPositionReport.setAltitude((float) ThreadLocalRandom.current().nextDouble(0, 3000));
        randomPositionReport.setLatitude((float) ThreadLocalRandom.current().nextDouble(-90, 90));
        randomPositionReport.setLongitude((float) ThreadLocalRandom.current().nextDouble(-90, 90));

        return randomPositionReport;
    }

    protected void savePositionReport(PositionReport positionReport) {
        DynamoDBMapper mapper = new DynamoDBMapper(this.client);
        mapper.save(positionReport);
    }
}
