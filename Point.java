import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Point extends Polygon {
    private double centerX;
    private double centerY;
    
    public Point(double x, double y, int id){
    	super();

    	if (id < 12){
    		getPoints().addAll(new Double[]{
    		0.0,0.0,
    		80.0,00.0,
    		40.0,250.0,
    		});
    	}
    	else{
    		getPoints().addAll(new Double[]{
    		0.0,250.0,
    		80.0,250.0,
    		40.0,0.0,
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

}