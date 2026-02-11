import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;

public class Backgammon extends Application {
    public static final double WIDTH = 1040.0;
    public static final double HEIGHT = 600.0;
    public static Player currentPlayer;
    private static Player p1;
    private static Player p2;
    private static ArrayList<Point> points = new ArrayList<>();
    private static ArrayList<Checker> checkers = new ArrayList<>();
    private static ArrayList<Dice> die = new ArrayList<>();
    private static boolean isPlaying = true;
    private static Stage primaryStage;
    private static Pane root;
    
    public static void initializeGame(){
        die.clear();
        points.clear();
        checkers.clear();

        Rectangle diceArea = new Rectangle(WIDTH, 0, 200, HEIGHT);
        diceArea.setFill(Color.web("#a82e23"));
        root.getChildren().add(diceArea);

        Rectangle captureArea = new Rectangle(6*(Point.WIDTH),0,Point.WIDTH,HEIGHT);
        captureArea.setFill(Color.web("#522e21"));
        root.getChildren().add(captureArea);

        Dice dice1 = new Dice((200-2*Dice.HEIGHT)/3+WIDTH, (HEIGHT-Dice.HEIGHT)/2);
        Dice dice2 = new Dice(WIDTH-(200-2*Dice.HEIGHT)/3-Dice.HEIGHT+200, (HEIGHT-Dice.HEIGHT)/2);
        die.add(dice1);
        die.add(dice2);
        Button rollDice = new Button("Roll the Dice!");
        rollDice.setLayoutY((HEIGHT-Dice.HEIGHT)/2 + Dice.HEIGHT + 10);
        rollDice.setLayoutX((200-2*Dice.HEIGHT)/3+WIDTH + 30);

        rollDice.setOnAction(event -> {
            if (currentPlayer.getClass().getName().equals("HumanPlayer")){
                dice1.roll();
                dice2.roll();
                if (Arrays.equals(currentPlayer.getDiceRolls(), new int[]{0,0,0,0})){
                    if (dice1.getValue() == dice2.getValue()){
                        currentPlayer.setDiceRolls(new int[]{dice1.getValue(), dice1.getValue(), dice1.getValue(), dice1.getValue()});
                    }
                    else{
                        currentPlayer.setDiceRolls(new int[]{dice1.getValue(), dice2.getValue(), 0,0});
                    }
                } 
            }
        });

        root.getChildren().addAll(dice1, dice2, rollDice);
        
        System.out.println("Backgammon game started!");

        makeBoard(root);
        p1 = new FarPlayer(0);
        p2 = new FarPlayer(1);
        currentPlayer = p1;
        currentPlayer.beginTurn();
    }

    @Override
    public void start(Stage primaryStage) {
        Backgammon.primaryStage = primaryStage;
        root = new Pane();
        root.setStyle("-fx-background-color: #70412f;");
        
        Scene scene = new Scene(root, WIDTH+200, HEIGHT);
        
        primaryStage.setTitle("Backgammon");
        primaryStage.setScene(scene);
        
        primaryStage.centerOnScreen();
        
        primaryStage.show();

        initializeGame();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public static void swapPlayers(){
        if (currentPlayer == p1){
            currentPlayer = p2;
        }
        else{
            currentPlayer = p1;
        }
        for (Dice dice : die){
            dice.reset();
        }
        currentPlayer.beginTurn();
    }

    public static ArrayList<Dice> getDie(){
        return die;
    }

    public static ArrayList<Checker> getCheckers(){
        return checkers;
    }

    public static ArrayList<Point> getPoints(){
        return points;
    }

    public static Point getPointAtId(int idIn){
        for (Point p : points){
            if (p.getPointId() == idIn){
                return p;
            }
        }
        return null;
    }

    public static void makeBoard(Pane root){
        points = new ArrayList<>();

        for (int i = 0; i < 6; i++){
            points.add(new Point(Point.WIDTH*i, 0, i));
        }
        for (int i = 6; i < 12; i++){
            points.add(new Point(Point.WIDTH+Point.WIDTH*i, 0, i));
        }

        for (int i = 0; i < 6; i++){
            points.add(new Point((WIDTH-Point.WIDTH)-Point.WIDTH*i, (HEIGHT-Point.HEIGHT), i+12));
        }
        for (int i = 6; i < 12; i++){
            points.add(new Point((WIDTH-2*Point.WIDTH)-Point.WIDTH*i, (HEIGHT-Point.HEIGHT), i+12));
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
        

        /*
        for (int i = 0; i < 6; i++){
            checkers.add(new Checker(0, points.get(i)));
            checkers.add(new Checker(1, points.get(23-i)));
        }
        */


        
        root.getChildren().addAll(checkers);
    }

    public static boolean isPlaying(){
        return isPlaying;
    }

    public static void gameOver(){
        if (currentPlayer.getColor() == 0){
            System.out.println("WHITE WINS GAME OVER");
        }
        else{
            System.out.println("BLACK WINS GAME OVER");
        }
        isPlaying = false;

        Platform.runLater(() -> showPopUpWindow());
    }

    private static void showPopUpWindow(){
        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setTitle("Game Over");
        
        String winner;
        if (currentPlayer.getColor() == 0) {
            winner = "WHITE";
        } else {
            winner = "BLACK";
        }
        Label label = new Label(winner + " WINS");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 20px;");
        
        Button rematchButton = new Button("Rematch");
        rematchButton.setOnAction(e -> {
            secondaryStage.close();
            root.getChildren().clear();
            isPlaying = true;
            initializeGame();
        });

        Button closeButton = new Button("Quit Game");
        closeButton.setOnAction(e -> {
            secondaryStage.close();
            primaryStage.close();
        });
        
        HBox buttonBox = new HBox(10, rematchButton, closeButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, label, buttonBox);
        vbox.setAlignment(Pos.CENTER);


        
        Scene secondaryScene = new Scene(vbox, 200, 150);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.showAndWait(); 
    }
}