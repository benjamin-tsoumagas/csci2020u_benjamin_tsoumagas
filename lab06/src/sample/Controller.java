package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    public GraphicsContext gc;

    @FXML
    private void initialize() {
        gc = canvas.getGraphicsContext2D();

        drawBarChart(500,300, avgHousingPricesByYear, avgCommercialPricesByYear, Color.RED, Color.BLUE);
        drawPieChart(gc);
    }

    //two double arrays must be same length for function to work
    private void drawBarChart(int height, int width, double[]yVal, double[]yVal2, Color colour, Color colour2) {

        //create a new double array containing both arrays
        int index = 0;
        double[]newArr = new double[yVal.length + yVal2.length+1];
        newArr[0] = 0;
        //append value from array 1 then array 2 until both are iterated through
        for(int i = 1; i < (yVal.length+yVal2.length) ;i++){
            newArr[i] = yVal[index];
            i++;
            newArr[i] = yVal2[index];
            index++;
        }

        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        for (double value : newArr){
            if(value > max)
                max = value;
            if(value < min)
                min = value;
        }

        double x = 50.0;
        double barWidth = width/newArr.length;
        int j = 0;
        for (double val : newArr){
            double barHeight = ((val - min) / (max - min)) * height;
            gc.fillRect(x, (height - barHeight), barWidth, barHeight);
            if(j%2 == 0) {
                gc.setFill(colour);
                x += (1.5 * barWidth);
            } else {
                gc.setFill(colour2);
                x += barWidth;
            }
            j++;
        }
    }

    private void drawPieChart(GraphicsContext gc) {
        int numOfStudents = 0;
        for (int numStudentsOfDegree: purchasesByAgeGroup)
            numOfStudents += numStudentsOfDegree;

        double startAngle = 0.0;
        for (int i = 0; i < purchasesByAgeGroup.length; i++) {
            double studentPercentage = (double) purchasesByAgeGroup[i] / (double) numOfStudents;
            double sweepAngle = studentPercentage * 360.0;

            gc.setFill(pieColours[i]);
            gc.fillArc(600,150,300,300,startAngle, sweepAngle, ArcType.ROUND);

            startAngle += sweepAngle;
        }
    }

    private static final double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };

    private static final double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    private static final String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };

    private static final int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };

    private static final Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };
}
