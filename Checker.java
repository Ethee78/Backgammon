import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Checker extends Circle {
    public static final double RADIUS = 25;
    private Point currentPoint;
    private int color; // 0 = white, 1 = black
    

    public Checker(int colorIn, Point pointIn){
    	super(RADIUS);
    	currentPoint = pointIn;
        color = colorIn;

        if (colorIn == 0){
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            this.setStrokeWidth(3);
        }
        else{
            this.setFill(Color.BLACK);
            this.setStroke(Color.WHITE);
            this.setStrokeWidth(3);
        }
        putOnPoint(pointIn);
        this.setMouseTransparent(true); // Important to ensure that users can click on points without being blocked by a checker
    }

    public void putOnPoint(Point p){
        setLayoutX(p.getX()+RADIUS+(p.WIDTH/2-RADIUS));

        if (p.getPointId() < 12){
            setLayoutY(p.getNextPosY()+RADIUS);
        }
        else{
            setLayoutY(p.getNextPosY()-RADIUS); 
        }
        
        p.addChecker(this);
    }

    public Point getCurrentPoint(){
        return currentPoint;
    }

    public int getColor(){
        return color;
    }

}