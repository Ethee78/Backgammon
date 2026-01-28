import java.util.Arrays;

public class HumanPlayer extends Player{
	private Point selected;

	public HumanPlayer(int colorIn){
		super(colorIn);
		selected = null;
	}

	@Override
	public Point getSelected(){
		return selected;
	}

	@Override
	public void setSelected(Point p){
		selected = p;
	}

	@Override
	public void beginTurn(){
		if (color == 0){
			System.out.println("Currently White's turn");
		}
		else{
			System.out.println("Currently Black's turn");
		}
	}

	@Override
	public boolean isValidMove(Point p){
		// this checks if the move clicked on is valid. Only needed for Human player for this reason
		int id1 = selected.getPointId();
		int id2 = p.getPointId();

		if ((color == 0) && (Checker.numCapturedWhite != 0)||
			(color == 1) && (Checker.numCapturedBlack != 0)){
			return false;
		}

		if (p.getNumCheckers() > 1){
			if (p.getCheckers().get(0).getColor() != this.color){
				// ensures that checkers cannot be put ontop of stacks of size more than one of the opposing color
				return false;
			}
		}

		if (color == 1){
			if (id1 + diceRolls[0] == id2){
				return true;
			}
			if (id1 + diceRolls[1] == id2){
				return true;
			}
			if (id1 + diceRolls[0] + diceRolls[1] == id2){
				if (Backgammon.getPointAtId(id1 + diceRolls[0]).getNumCheckers() == 0){
					return true;
				}
				else if (Backgammon.getPointAtId(id1 + diceRolls[1]).getNumCheckers() == 0){
					return true;
				} 
				else if ((Backgammon.getPointAtId(id1 + diceRolls[0]).getNumCheckers() == 1) && 
					(Backgammon.getPointAtId(id1 + diceRolls[0]).getCheckers().get(0).getColor() != this.color)){
						
					Backgammon.getPointAtId(id1 + diceRolls[0]).getCheckers().get(0).capture();
					Backgammon.getPointAtId(id1 + diceRolls[0]).popChecker();
					return true;
				}
				else if ((Backgammon.getPointAtId(id1 + diceRolls[1]).getNumCheckers() == 1) && 
					(Backgammon.getPointAtId(id1 + diceRolls[1]).getCheckers().get(0).getColor() != this.color)){
						
					Backgammon.getPointAtId(id1 + diceRolls[1]).getCheckers().get(0).capture();
					Backgammon.getPointAtId(id1 + diceRolls[1]).popChecker();
					return true;
				}
			}
			// These next two cases only occur if we have doubles
			if (id1 + diceRolls[0] + diceRolls[1] + diceRolls[2] == id2){
				return true;
			}
			if (id1 + diceRolls[0] + diceRolls[1] + diceRolls[2] + diceRolls[3] == id2){
				return true;
			}
		}
		else{
			if (id1 - diceRolls[0] == id2){
				return true;
			}
			if (id1 - diceRolls[1] == id2){
				return true;
			}
			if (id1 - diceRolls[0] - diceRolls[1] == id2){
				if (Backgammon.getPointAtId(id1 - diceRolls[0]).getNumCheckers() == 0){
					return true;
				}
				else if (Backgammon.getPointAtId(id1 - diceRolls[1]).getNumCheckers() == 0){
					return true;
				} 
				else if ((Backgammon.getPointAtId(id1 - diceRolls[0]).getNumCheckers() == 1) && 
					(Backgammon.getPointAtId(id1 - diceRolls[0]).getCheckers().get(0).getColor() != this.color)){
						
					Backgammon.getPointAtId(id1 - diceRolls[0]).getCheckers().get(0).capture();
					Backgammon.getPointAtId(id1 - diceRolls[0]).popChecker();
					return true;
				}
				else if ((Backgammon.getPointAtId(id1 - diceRolls[1]).getNumCheckers() == 1) && 
					(Backgammon.getPointAtId(id1 - diceRolls[1]).getCheckers().get(0).getColor() != this.color)){
						
					Backgammon.getPointAtId(id1 - diceRolls[1]).getCheckers().get(0).capture();
					Backgammon.getPointAtId(id1 - diceRolls[1]).popChecker();
					return true;
				}
			}
			// These next two cases only occur if we have doubles
			if (id1 - diceRolls[0] - diceRolls[1] - diceRolls[2] == id2){
				return true;
			}
			if (id1 - diceRolls[0] - diceRolls[1] - diceRolls[2] - diceRolls[3] == id2){
				return true;
			}
		}
		return false;
	}

	public void moveChecker(Point endPoint){
		// this moves an actual checker piece, it is not for verifying a moves validity (see above for this)
		if (endPoint.getNumCheckers() == 1){
			System.out.println(endPoint.getCheckers().get(0).getColor());
			if (endPoint.getCheckers().get(0).getColor() != this.color){
				endPoint.getCheckers().get(0).capture();
				endPoint.popChecker();
			}
		}

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

	@Override
	public void endTurn(){
		if (selected != null){
			selected.unselectPoint();
			selected = null;
		}
		Backgammon.swapPlayers();	
	}

}