public class HumanPlayer{
	private Point selected;
	private int[] diceRolls;

	public HumanPlayer(){
		selected = null;
		diceRolls = new int[4];
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

	public void setDiceRolls(int[] diceRollsIn){
		diceRolls = diceRollsIn;
		System.out.println(diceRolls[0]);
		System.out.println(diceRolls[1]);
		System.out.println(diceRolls[2]);
		System.out.println(diceRolls[3]);
	}

	public boolean isValidMove(Point p){
		int id1 = selected.getPointId();
		int id2 = p.getPointId();

		if (id1 + diceRolls[0] == id2){
			return true;
		}
		if (id1 + diceRolls[1] == id2){
			return true;
		}
		if (id1 + diceRolls[0] + diceRolls[1] == id2){
			return true;
		}
		return false;
	}

	public void moveChecker(Point endPoint){
		Checker c = selected.popChecker();
		c.putOnPoint(endPoint);
	}


}