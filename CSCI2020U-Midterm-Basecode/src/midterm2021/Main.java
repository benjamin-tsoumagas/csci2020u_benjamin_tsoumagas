package midterm2021;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    //Initialize variables used in the animation process
    private final int frameWidth = 32;
    private final int frameHeight = 36;
    private int sourceHeightOffset = 0;
    private int sourceWidthOffset = 288;
    private int frameIndex = 0;
    private final int numFrames = 24;


    /**
     *
     * @param primaryStage the stage that is displayed to the user
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CSCI2020U - Midterm");

        //Initialize initials label
        Label graphicsLabel  = new Label("\n \nBT");
        graphicsLabel.setAlignment(Pos.BOTTOM_CENTER);

        //Main grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        //Animation grid
        GridPane animationGrid = new GridPane();
        animationGrid.setAlignment(Pos.CENTER);
        animationGrid.setHgap(10);
        animationGrid.setVgap(10);

        //Graphics grid
        GridPane graphicsGrid = new GridPane();
        graphicsGrid.setAlignment(Pos.CENTER);
        graphicsGrid.setHgap(10);
        graphicsGrid.setVgap(10);

        //About grid
        GridPane aboutGrid = new GridPane();
        aboutGrid.setAlignment(Pos.CENTER);
        aboutGrid.setHgap(10);
        aboutGrid.setVgap(10);

//      Creating the menu buttons
        Button animationButton = new Button("Animation");
        animationButton.setPrefWidth(200);
        Button graphicsButton = new Button("2D Graphics");
        graphicsButton.setPrefWidth(200);
        Button aboutButton = new Button("About");
        aboutButton.setPrefWidth(200);
        Button homeButton = new Button("Back to Main");
        homeButton.setPrefWidth(200);

        // Main App Scene
        Scene mainScene = new Scene(grid, 500, 400);

        //About Scene
        Scene aboutScene = new Scene(aboutGrid, 500, 400);

        //Event handling for Animation page
        animationButton.setOnAction(new EventHandler<>() {
            /**
             * Takes user to animation page and displays the duck animation
             * Offers user a button to return to main screen
             *
             * @param actionEvent activates on button press
             */
            @Override
            public void handle(ActionEvent actionEvent) {
//                TODO: Replace the scene or the root
//                      Display the "Animation" in the CENTER,
//                      and a "Back to Main" on the TOP
                Group animation = new Group();
                Scene animationScene = new Scene(animation, 500, 400);
                Canvas animationCanvas = new Canvas();
                //Set canvas width/height equal to stage width/height
                animationCanvas.widthProperty().bind(primaryStage.widthProperty());
                animationCanvas.heightProperty().bind(primaryStage.heightProperty());
                //add canvas and button to the same group
                animation.getChildren().add(animationCanvas);
                animation.getChildren().add(homeButton);
                //create a GraphicsContext object for use in animating
                GraphicsContext gc = animationCanvas.getGraphicsContext2D();
                drawAnimation(gc);
                primaryStage.setTitle("Animation");
                primaryStage.setScene(animationScene);
            }
        });

        //Event handling for Graphics page
        graphicsButton.setOnAction(new EventHandler<>() {
            /**
             * Takes user to graphics page and shows a label of my initials
             * Shows user my initials drawn with lines
             * Offers user button to return to main screen
             *
             * @param actionEvent activates on button press
             */
            @Override
            public void handle(ActionEvent actionEvent) {
//                TODO: Replace the scene or the root
//                    Display the "2D Drawing" in the CENTER,
//                    and a "Back to Main" on the TOP
                Group graphics = new Group();
                Scene graphicsScene = new Scene(graphics, 500, 400);
                Canvas graphicsCanvas = new Canvas();
                //Set canvas width/height equal to stage width/height
                graphicsCanvas.widthProperty().bind(primaryStage.widthProperty());
                graphicsCanvas.heightProperty().bind(primaryStage.heightProperty());
                //add canvas button and label to the same group
                graphics.getChildren().add(graphicsCanvas);
                graphics.getChildren().add(homeButton);
                graphics.getChildren().add(graphicsLabel);
                //create a GraphicsContext object for use in animating
                GraphicsContext gc = graphicsCanvas.getGraphicsContext2D();
                drawInitials(gc);
                primaryStage.setTitle("Graphics");
                primaryStage.setScene(graphicsScene);
            }
        });

        //Event handling for About page
        aboutButton.setOnAction(new EventHandler<>() {
            /**
             * Takes user to about page and shows them information about me and the project
             * Offers user button to return to main screen
             *
             * @param actionEvent activates when button is clicked
             */
            @Override
            public void handle(ActionEvent actionEvent) {
//                TODO: Replace the scene or the root
//                    Display the "About" in the CENTER,
//                    and a "Back to Main" on the TOP
                System.out.println("Clicked on About button");
                primaryStage.setScene(aboutScene);
                primaryStage.setTitle("About");
                //add button and center it
                aboutGrid.add(homeButton, 0, 1);
                GridPane.setHalignment(homeButton, HPos.CENTER);
                //Instantiate a controller object
                Controller ctrl = new Controller();
                String[] newInfo = ctrl.readXML();
                //Creating About TextField
                TextField id = new TextField(newInfo[0]);
                TextField name = new TextField(newInfo[1]);
                TextField email = new TextField(newInfo[2]);
                Text desc = new Text(newInfo[3]);
                //Adding TextFields
                aboutGrid.add(id, 0, 2);
                aboutGrid.add(name, 0, 3);
                aboutGrid.add(email, 0, 4);
                aboutGrid.add(desc, 0, 5);
            }
        });

        //Event Handling for Main Page
        homeButton.setOnAction(new EventHandler<>() {
            /**
             * Occurs when user returns to main after visiting one of the three:
             * animation, graphics, about
             * Replicates the main page with working buttons
             *
             * @param event activates when button is clicked
             */
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainScene);
                primaryStage.setTitle("CSCI2020U - Midterm");
                //Add the three navigation buttons
                grid.add(animationButton, 0, 1);
                grid.add(graphicsButton, 0, 2);
                grid.add(aboutButton, 0, 3);
                System.out.println("Returned to Main");
            }
        });

//        Add the menu buttons to the grid
        grid.add(animationButton, 0,1);
        grid.add(graphicsButton, 0,2);
        grid.add(aboutButton, 0,3);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    //Method to draw animation
    /**
     * loops through a set of views of images in a png
     * this code displays an animation of a grey duck
     * @param gc an object of the GraphicsContext class
     */
    public void drawAnimation(GraphicsContext gc){
        Image image = new Image(getClass().getClassLoader().getResource("ducks.png").toString());
        //Set a timeline that runs forever and changes key frames every 4 seconds
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4000), actionEvent -> {
            //Creates a coloured rectangle at the coordinate
            gc.setFill(Color.LIGHTSEAGREEN);
            gc.fillRect(250, 200, frameWidth, frameHeight);
            //Puts the key frame image at the coordinate on the rectangle
            gc.drawImage(image, sourceWidthOffset, sourceHeightOffset, frameWidth, frameHeight, 250, 200, frameWidth, frameHeight);
            //Keep track of what key frame is being displayed
            frameIndex = (frameIndex + 1) % numFrames;
            //Reset to the top-left most image at the end of every loop
            if (frameIndex == 0) {
                sourceWidthOffset = 288;
                sourceHeightOffset = 0;
            }
            //Increment width by one frame at the end of every column
            if (frameIndex + 1 % 8 == 0) {
                sourceWidthOffset += frameWidth;
            }
            //Increment height for every image in the column, at 8 reset
            sourceHeightOffset = frameHeight * (frameIndex % 8);
        }));
        //At the end of the key frames start again (our timeline runs indefinitely)
        timeline.playFromStart();
    }

    //Method to draw initials
    /**
     * Uses line drawings to display my initials
     * @param gc object of the GraphicsContext class
     */
    public void drawInitials(GraphicsContext gc){
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(10);
        gc.strokeLine(100,100,100,200);
        gc.strokeLine(300,100,400,100);
        gc.strokeLine(350,100,350,200);
        gc.strokeArc(65,100,75,50,270,180, ArcType.OPEN);
        //gc.fillArc(65,100,75,50,270,180, ArcType.OPEN);
        gc.strokeArc(65,150,75,50,270,180, ArcType.OPEN);
        //gc.fillArc(65,150,75,50,270,180, ArcType.OPEN);
    }
    public static void main(String[] args) {
        launch(args);
    }
}