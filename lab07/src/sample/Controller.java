package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Controller {

    @FXML
    private Label maxFine;
    @FXML
    private Label minFine;
    @FXML
    private Canvas canvas;
    private String line = "";
    private static final String comma = ",";
    private Map<String, Integer> occurrences = new TreeMap<>();
    private String[] cols;
    private ArrayList<Integer>numDisasters = new ArrayList<Integer>();
    private ArrayList<String>typeDisasters = new ArrayList<String>();
    public GraphicsContext gc;


    @FXML
    public ArrayList<String> readCSV(){
        ArrayList<String> disasters = new ArrayList<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("weatherwarnings-2015.csv"));
            br.readLine();
            while((line = br.readLine()) != null) {
                cols = line.split(comma);
                disasters.add(cols[5]);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return disasters;
    }

    @FXML
    private void initialize(){
        readCSV();

        Iterator<String> i = readCSV().iterator();
        while(i.hasNext()){
            String current = i.next().toString();
            System.out.print(i.next()+" ");
            if(occurrences.containsKey(current)){
                int prev = occurrences.get(current);
                occurrences.put(current,prev+1);
            } else {
                occurrences.put(current,1);
            }
        }

        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            System.out.println("key ->" + entry.getKey() + ", value->" + entry.getValue());
            numDisasters.add(entry.getValue());
            typeDisasters.add(entry.getKey());
        }

        System.out.println(numDisasters);
        System.out.println(typeDisasters);


        gc = canvas.getGraphicsContext2D();
        drawPieChart(gc);
        drawLegend(gc);
        }

    private static final Color[] pieColours = {
            javafx.scene.paint.Color.AQUA, javafx.scene.paint.Color.GOLD, javafx.scene.paint.Color.DARKORANGE, javafx.scene.paint.Color.DARKSALMON, javafx.scene.paint.Color.LAWNGREEN, Color.PLUM
    };

    private void drawPieChart(GraphicsContext gc) {
        int numFrac = 0;
        for (int pieFragments: numDisasters)
            numFrac += pieFragments;

        double startAngle = 0.0;
        for (int i = 0; i < numDisasters.size(); i++) {
            double studentPercentage = (double) numDisasters.get(i) / (double) numFrac;
            double sweepAngle = studentPercentage * 360.0;

            gc.setFill(pieColours[i]);
            gc.fillArc(600,150,300,300,startAngle, sweepAngle, ArcType.ROUND);

            startAngle += sweepAngle;
        }
    }

    private void drawLegend(GraphicsContext gc){
        int height = 10;
        int i = 0;
        for(String categories: typeDisasters){
            gc.setFill(pieColours[i]);
            gc.fillRect(30,height,70,70);
            gc.strokeText(categories, 150, height+50);
            height += 80;
            i++;

        }
    }



}
