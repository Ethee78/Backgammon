import java.util.Arrays;

public class HumanPlayer{
	private Point selected;
	private int[] diceRolls;
	private int color; // 0 = white, 1 = black

	public HumanPlayer(int colorIn){
		selected = null;
		diceRolls = new int[4];
		color = colorIn;
	}

	public int getColor(){
		return color;
	}

	public Point getSelected(){
		return selected;
	}

	public void setSelected(Point p){
		selected = p;
	}

	public int[] getDiceRolls(){
		return diceRolls;
	}

	private void displayDiceRolls(){
		// this is created just for testing
		System.out.print("[");
		for (int i=0; i<4; i++){
			System.out.print(diceRolls[i]);
		}
		System.out.print("]");
		System.out.println();
	}

	public void setDiceRolls(int[] diceRollsIn){
		diceRolls = diceRollsIn;
	}

	public boolean isValidMove(Point p){
		int id1 = selected.getPointId();
		int id2 = p.getPointId();

		if (p.getNumCheckers() > 1){
			if (p.getCheckers().get(0).getColor() != this.color){
				// ensures that checkers cannot be put ontop of stacks of size more than one of the opposing color
				return false;
			}
		}

		if (id1 + diceRolls[0] == id2){
			return true;
		}
		if (id1 + diceRolls[1] == id2){
			return true;
		}
		if (id1 + diceRolls[0] + diceRolls[1] == id2){
			return true;
		}
		// These next two cases only occur if we have doubles
		if (id1 + diceRolls[0] + diceRolls[1] + diceRolls[2] == id2){
			return true;
		}
		if (id1 + diceRolls[0] + diceRolls[1] + diceRolls[2] + diceRolls[3] == id2){
			return true;
		}
		return false;
	}

	public void moveChecker(Point endPoint){
		int distance = Math.abs(selected.getPointId() - endPoint.getPointId());
		Checker c = selected.popChecker();
		c.putOnPoint(endPoint);

		// This next part removes the move from the list of avaiable dice rolls

		for (int i = 3; i >= 0; i--){
			if (diceRolls[i] == distance){
				diceRolls[i] = 0;
				if (Arrays.equals(diceRolls, new int[]{0,0,0,0})){
					System.out.println("Turn OVER");
					endTurn();
				}
				displayDiceRolls();
				return;
			}
		}

		if (diceRolls[2] + diceRolls[3] == distance){
			diceRolls[2] = 0;
			diceRolls[3] = 0;
		}
		else if (diceRolls[1] + diceRolls[2] + diceRolls[3] == distance){
			diceRolls[1] = 0;
			diceRolls[2] = 0;
			diceRolls[3] = 0;
		}
		else if (diceRolls[0] + diceRolls[1] + diceRolls[2] + diceRolls[3] == distance){
			diceRolls[0] = 0;
			diceRolls[1] = 0;
			diceRolls[2] = 0;
			diceRolls[3] = 0;
		}
		else if (diceRolls[0] + diceRolls[1] == distance){
			diceRolls[0] = 0;
			diceRolls[1] = 0;
		}

		if (Arrays.equals(diceRolls, new int[]{0,0,0,0})){
			System.out.println("Turn OVER");
			endTurn();
		}
	}

	public void endTurn(){
		if (selected != null){
			selected.unselectPoint();
			selected = null;
		}
		Backgammon.swapPlayers();	
	}    

}