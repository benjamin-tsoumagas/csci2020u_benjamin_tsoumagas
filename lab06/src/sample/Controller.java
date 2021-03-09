package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.awt.*;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext bc = canvas.getGraphicsContext2D();

        drawBarChart(bc);
        drawPieChart(gc);
    }

    private void drawBarChart(GraphicsContext bc) {
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

    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };

    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };

    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };
}
