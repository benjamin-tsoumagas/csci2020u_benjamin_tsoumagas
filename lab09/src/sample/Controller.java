package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Controller {

    //Attributes used in modifying the target stock URL
    static final private String interval = "1mo";
    static final private String event = "history";
    static final private boolean adjClose = true;
    private static final long  start = LocalDate.of(2010,1,1)
            .atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    private static final long end = LocalDate.of(2015,12,31)
            .atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

    public static double[] downloadStockPrices(String stockTicker){
        ArrayList<String> output = new ArrayList<>();

        try {
            URL url = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockTicker + "?period1=" + start + "&period2=" + end + "&interval="+interval+"&events="+event+"&includeAdjustedClose="+adjClose);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            //Reads each line of the CSV at the URL and creates a String arraylist
            //String arraylist contains 4th column a.k.a. close values
            String line;
            while((line = read.readLine()) != null){
                String[] data;
                data = line.split(",");
                output.add(data[4]);
            }
            //Close reader and remove first value ('close' header)
            read.close();
            output.remove(0);
            //Convert String arraylist to double array
            double[] result = new double[output.size()];
            for(int i = 0; i < output.size(); i++){
                result[i] = Double.parseDouble(output.get(i));
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
