import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.input.MouseButton;


public class Point extends Polygon {
    public static final double HEIGHT = 250.0;
    public static final double WIDTH = 80.0;
    private int numCheckers;
    private double x;
    private double y;
    private int id;
    private ArrayList<Checker> checkers;
    private boolean selected;

    public Point(double xIn, double yIn, int idIn){
    	super();
        numCheckers = 0;
        x = xIn;
        y = yIn;
        id = idIn;
        checkers = new ArrayList<>();
        selected = false;

    	if (id < 12){
    		getPoints().addAll(new Double[]{
    		0.0,0.0,
    		WIDTH,00.0,
    		WIDTH/2,HEIGHT,
    		});
    	}
    	else{
    		getPoints().addAll(new Double[]{
    		0.0,HEIGHT,
    		WIDTH,HEIGHT,
    		WIDTH/2,0.0,
    		});
    	}
    	
    	if (id % 2 == 0){
    		this.setFill(Color.RED);
    	}
    	else{
    		this.setFill(Color.GREEN);
    	}
    	
    	setLayoutX(x);
    	setLayoutY(y);

        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                this.clicked();
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                this.rightClicked();
            }
        });
    }

    public double getX(){
        return x;
    }

    public int getPointId(){
        return id;
    }

    public int getNumCheckers(){
        return numCheckers;
    }

    public void incrementNumCheckers(){
        numCheckers = numCheckers + 1;
    }

    public void decrementNumCheckers(){
        numCheckers = numCheckers - 1;
    }

    public double getNextPosY(){
        // This function finds the next y-position to put a checker
        if (id < 12){
            return y + (WIDTH/2 * numCheckers);
        }
        else{
            return y - (WIDTH/2 * numCheckers) + HEIGHT;
        }
        
    }

    public void addChecker(Checker c){
        checkers.add(c);
        incrementNumCheckers();
    }

    public Checker popChecker(){
        if (numCheckers == 0){
            return null;
        }
        decrementNumCheckers();
        return checkers.remove(checkers.size() - 1);
    }

    public ArrayList<Checker> getCheckers(){
        return checkers;
    }

    public void selectPoint(){
        this.setStroke(Color.YELLOW);
        this.setStrokeWidth(3);
        selected = true;
    }

    public void unselectPoint(){
        this.setStrokeWidth(0);
        selected = false;
    }

    public void clicked(){
        Player player = Backgammon.currentPlayer;

        // TEST FEATURE

        //System.out.println(id);
        //System.out.println(this.getNumCheckers());

        player.displayDiceRolls();

        // END OF TEST

        if (!(player.getClass().getName().equals("HumanPlayer"))){
            return; // This ensures that clicking on a point does nothing if the current player is AI
        }

        if (selected){
            // If the player clicked on a selected point, then the point becomes unselected
            this.setStrokeWidth(0);
            player.setSelected(null);
            selected = false;
            return;
        }

        if (player.getSelected() == null){
            if ((player.getColor() == 0) && (Checker.numCapturedWhite != 0)||
                (player.getColor() == 1) && (Checker.numCapturedBlack != 0)){
                // this deals with situation where player has captured pieces and must come in first
                player.comeInOn(this);
                return;
            }
            // If player clicks on a point, and the player has no point selected, then we try to select it
            if (this.numCheckers == 0){
                // this ensures that players cannot select points with no checkers
                return;
            }
            if (this.checkers.get(0).getColor() != player.getColor()){
                // this ensures that players cannot select points with checkers of the opponents color
                return;
            }
            player.setSelected(this);
            this.selectPoint();
        }
        else{
            if (player.isValidMove(this)){
                // If the player has selected a point, and this point is a valid move for one of those checkers then we move it
                player.moveChecker(this);
                if (player.getSelected() != null){
                    if (player.getSelected().numCheckers == 0){
                        // this ensures that if all checkers are moved off a point then it is unselected
                        player.getSelected().unselectPoint();
                        player.setSelected(null);
                    }
                }   
            }
            else{
                System.out.println("INVALID MOVE!!");
            }
            
        }
    }



    public void rightClicked(){
        Player player = Backgammon.currentPlayer;

        if (player.isAllHome()){
            if (player.getColor() == 0){
                int[] rolls = player.getDiceRolls();
                Arrays.sort(rolls);
                for (int i = 0; i < 4; i++){
                    if (rolls[i] - 1 >= this.getPointId()){
                        if (this.checkers.get(0) == null){
                            return;
                        }
                        else if (this.checkers.get(0).getColor() == 0){
                            Checker c = this.popChecker();
                            c.takeOff();
                            rolls[i] = 0;
                            player.setDiceRolls(rolls);
                            player.updateAllHome();
                            break;
                        }
                    }
                }
            }
            else{
                int[] rolls = player.getDiceRolls();
                Arrays.sort(rolls);
                for (int i = 0; i < 4; i++){
                    if (23 - rolls[i] < this.getPointId()){
                        if (this.checkers.get(0) == null){
                            return;
                        }
                        else if (this.checkers.get(0).getColor() == 1){
                            Checker c = this.popChecker();
                            c.takeOff();
                            rolls[i] = 0;
                            player.setDiceRolls(rolls);
                            player.updateAllHome();
                            break;
                        }
                    }
                }
            }
        }
    }
}