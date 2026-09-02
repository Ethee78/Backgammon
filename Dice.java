import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Dice extends ImageView{
	public static final double HEIGHT = 60.0;
	private int diceVal;
	private Timeline rollAnimation;
	private Image[] dicefaces = new Image[6];

	public Dice(double x, double y){
		super();
		loadDiceImages();
		setImage(dicefaces[0]);
		setX(x);
		setY(y);
		diceVal = -1; // value of -1 represents that the dice has not been rolled yet

		DropShadow glow = new DropShadow();
		glow.setColor(Color.CYAN);
		glow.setRadius(20);
		glow.setSpread(0.5);

		setEffect(glow);
	}

	private void loadDiceImages(){
		dicefaces[0] = new Image("file:dice\\one.png");
        dicefaces[1] = new Image("file:dice\\two.png");
        dicefaces[2] = new Image("file:dice\\three.png");
        dicefaces[3] = new Image("file:dice\\four.png");
        dicefaces[4] = new Image("file:dice\\five.png");
        dicefaces[5] = new Image("file:dice\\six.png");
	}

	public void roll(){
		if(diceVal == -1){
			setEffect(null);
			diceVal = (int)(Math.random() * 6) + 1;

			rollAnimation = new Timeline();

			for (int i = 0; i < 10; i++){
				final int frameIndex = i;
				KeyFrame keyFrame = new KeyFrame(
					Duration.millis(i * 100),
					e -> {
						if (frameIndex < 9){
							setImage(dicefaces[(int)(Math.random() * 6)]);
						} 
						else{
							setImage(dicefaces[diceVal - 1]);
						}
					}
				);
				rollAnimation.getKeyFrames().add(keyFrame);
			}
			rollAnimation.setCycleCount(1);
			rollAnimation.play();
		}
	}

	public int getValue(){
		return diceVal;
	}

	public void reset(){
		diceVal = -1;
		DropShadow glow = new DropShadow();
		glow.setColor(Color.CYAN);
		glow.setRadius(20);
		glow.setSpread(0.5);
		setEffect(glow);
	}
}