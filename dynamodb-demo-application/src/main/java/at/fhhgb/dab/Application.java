package at.fhhgb.dab;


import at.fhhgb.dab.service.MockPositionTrackingService;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Application {

    static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
            new ProfileCredentialsProvider()));

    static String positionReportTableName = "PositionReport";

    public static void main(String[] args) throws Exception {
        printMenu();
    }

    private static void printMenu() {
        boolean exit = false;

        System.out.println("DynamoDB demo application");

        while (!exit) {
            System.out.println("1 ... Send PositionReports to database for 10 seconds");
            System.out.println("2 ... Read PositionReports of the last 10 minutes from database");
            System.out.println("3 ... Exit");

            Scanner scanner = new Scanner(System.in);

            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    sendReports();
                    break;

                case 2:
                    readPositionReports();
                    break;

                case 3:
                    exit = true;
                    break;

                default:
                    break;
            }
        }
    }

    private static void sendReports() {
        MockPositionTrackingService mockPositionTrackingService = new MockPositionTrackingService();

        try {
            mockPositionTrackingService.startTracking();

            sleep(10000);

            mockPositionTrackingService.stopTracking();
        } catch (Exception e) {
            System.err.println("Error sending reports");
            System.err.println(e.getMessage());
        }
    }

    private static void readPositionReports() {
        // select table
        Table table = dynamoDB.getTable(positionReportTableName);

        long tenMinutesAgoMilli = (new Date()).getTime() - (10L*60L*1000L);
        Date tenMinutesAgo = new Date();
        tenMinutesAgo.setTime(tenMinutesAgoMilli);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String tenMinutesAgoStr = df.format(tenMinutesAgo);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("TrackerId = :t_id and ReportTime > :t_timestamp")
                .withValueMap(new ValueMap()
                        .withString(":t_id", "ABCDEFGH")
                        .withString(":t_timestamp", tenMinutesAgoStr))
                .withConsistentRead(true)
                ;

        ItemCollection<QueryOutcome> items = table.query(spec);

        System.out.println("GetItem: printing results...");

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJSONPretty());
        }
    }
}
