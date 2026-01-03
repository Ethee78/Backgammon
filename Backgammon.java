import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Backgammon extends Application {
    public static final double HEIGHT = 1040.0;
    public static final double WIDTH = 600.0;
    private ArrayList<Point> points;
    private ArrayList<Checker> checkers;
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #70412f;");
        
        Scene scene = new Scene(root, HEIGHT, WIDTH);
        
        primaryStage.setTitle("Backgammon");
        primaryStage.setScene(scene);
        
        primaryStage.centerOnScreen();
        
        primaryStage.show();
        
        System.out.println("Backgammon game started!");

        makeBoard(root);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void makeBoard(Pane root){
        points = new ArrayList<>();

        for (int i = 0; i < 6; i++){
            points.add(new Point(Point.WIDTH*i, 0, i));
        }
        for (int i = 6; i < 12; i++){
            points.add(new Point(Point.WIDTH+Point.WIDTH*i, 0, i));
        }

        for (int i = 0; i < 6; i++){
            points.add(new Point((HEIGHT-Point.WIDTH)-Point.WIDTH*i, (WIDTH-Point.HEIGHT), i+12));
        }
        for (int i = 6; i < 12; i++){
            points.add(new Point((HEIGHT-2*Point.WIDTH)-Point.WIDTH*i, (WIDTH-Point.HEIGHT), i+12));
        }
        root.getChildren().addAll(points);

        checkers = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            checkers.add(new Checker(0, points.get(5)));
            checkers.add(new Checker(1, points.get(11)));
            checkers.add(new Checker(0, points.get(12)));
            checkers.add(new Checker(1, points.get(18)));
        }

        for (int i = 0; i < 3; i++){
            checkers.add(new Checker(0, points.get(7)));
            checkers.add(new Checker(1, points.get(16)));
        }

        for (int i = 0; i < 2; i++){
            checkers.add(new Checker(1, points.get(0)));
            checkers.add(new Checker(0, points.get(23)));
        }

        
        root.getChildren().addAll(checkers);
    }
}