package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.ZoneId;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        final String stockTicker = "GOOG";
        final String stockTicker2 = "AAPL";
        final String interval = "1mo";
        long start = LocalDate.of(2020,1,1)
                .atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        long end = LocalDate.of(2021,3,24)
                .atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

        System.out.println("https://query1.finance.yahoo.com/v7/finance/download/"+stockTicker+"?period1="+start+"&period2="+end+"&interval="+interval+"&events=history&includeAdjustedClose=true");

        try {
            URL url = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockTicker + "?period1=" + start + "&period2=" + end + "&interval=1mo&events=history&includeAdjustedClose=true");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //StringBuilder -> List
            StringBuilder lines = new StringBuilder();
            String line;
            while((line = read.readLine()) != null){
                lines.append(line);
                lines.append("\n");
            }
            read.close();
            System.out.println(lines.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
