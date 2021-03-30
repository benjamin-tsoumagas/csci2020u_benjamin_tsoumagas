package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    //Attributes for stage dimensions and base max value of the plot
    static int height = 600;
    static int width = 1000;
    static double max = 0;

    @Override
    public void start(Stage primaryStage) {
        //Set up a new canvas for GraphicsContext to draw on
        Group solution = new Group();
        Scene lineChart = new Scene(solution, width, height);
        Canvas lineCanvas = new Canvas(width, height);
        solution.getChildren().add(lineCanvas);
        //Instantiate a GraphicsContext object
        GraphicsContext gc = lineCanvas.getGraphicsContext2D();
        //Call drawLinePlot with the outputs of downloadStockPrices (double[]) as arguments
        drawLinePlot(gc, Controller.downloadStockPrices("GOOG"), Controller.downloadStockPrices("AAPL"));
        primaryStage.setTitle("Lab 09: Stock Performance");
        primaryStage.setScene(lineChart);

        primaryStage.show();
    }

    public static void drawLinePlot(GraphicsContext gc, double[] listA, double[] listB){

        //Determine max and min values for the plot
        for (double value : listA){
            if(value > max)
                max = value;
        }
        for (double value : listB){
            if(value > max)
                max = value;
        }

        //draw x and y axis 50px from bottom left edge
        gc.setLineWidth(1);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        ///x-axis
        gc.strokeLine(50,height-50,width-50,height-50);
        //y-axis
        gc.strokeLine(50,height-50,50,50);
        //call plotLine twice (once per stock)
        plotLine(gc, listA, Color.RED);
        plotLine(gc, listB, Color.BLUE);
    }

    public static void plotLine(GraphicsContext gc, double[] list, Color colour){
        gc.setLineWidth(1);
        gc.setFill(colour);
        gc.setStroke(colour);
        //Number of values to plot
        int length = list.length;
        //The vertical/horizontal distance between each plotted point
        double widthInterval = (width - 100.0) / length;
        double heightInterval = ((100.0 - height)/-max);
        //Plot lines from each current point to the next, iterate over number of values
        for(int i = 0; i < length-1; i++){
            gc.strokeLine(50+i*widthInterval, height-50-heightInterval*list[i], 50+(i+1)*widthInterval, height-50-heightInterval*list[i+1]);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
