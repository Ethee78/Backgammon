import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Backgammon extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #70412f;");
        
        Scene scene = new Scene(root, 1040, 600);
        
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
        ArrayList<Point> points = new ArrayList<>();

        for (int i = 0; i < 6; i++){
            points.add(new Point(80*i, 0, i));
        }
        for (int i = 6; i < 12; i++){
            points.add(new Point(80+80*i, 0, i));
        }

        for (int i = 0; i < 6; i++){
            points.add(new Point(960-80*i, 350, i+12));
        }
        for (int i = 6; i < 12; i++){
            points.add(new Point(880-80*i, 350, i+12));
        }
        root.getChildren().addAll(points);
    }
}