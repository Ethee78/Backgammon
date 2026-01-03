import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;


public class Point extends Polygon {
    public static final double HEIGHT = 250.0;
    public static final double WIDTH = 80.0;
    private int numCheckers;
    private double x;
    private double y;
    private int id;

    public Point(double xIn, double yIn, int idIn){
    	super();
        numCheckers = 0;
        x = xIn;
        y = yIn;
        id = idIn;

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
    }

    public double getX(){
        return x;
    }

    public int getPointId(){
        return id;
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
}