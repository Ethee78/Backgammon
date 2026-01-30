import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.animation.PauseTransition;
import javafx.util.Duration; 
import java.util.Iterator;


public class FarPlayer extends Player{
    /* This is a very basic AI agent, that will always pick moves
    by prioritizing moving the pieces furthest away from their home
    fist.
    */

    public FarPlayer(int colorIn){
        super(colorIn);
    }

    public void beginTurn(){
        if (color == 0){
            System.out.println("Currently White's turn");
        }
        else{
            System.out.println("Currently Black's turn");
        }

        System.out.println("AI is Selecting move");
        rollDice();

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            makeMoves();
        });
        pause.play();
    }

    public void makeMoves(){
        while (!(Arrays.equals(this.getDiceRolls(), new int[]{0,0,0,0}))){
            ArrayList<Move> moves = findAllMoves();
            if (moves.size() == 0){
                break;
            }
            Move m = pickMove(moves);
            for (int i = 0; i < 4; i++){
                if (diceRolls[i] == 0){
                    continue;
                }
                if (diceRolls[i] == m.getDice()){
                    m.doMove();

                    updateAllHome();
                    diceRolls[i] = 0;

                    break;
                }
            }
        }

        endTurn();
    }

    public Move pickMove(ArrayList<Move> moves){
        moves = sortMoves(moves);
        return moves.get(0);
    }

    public ArrayList<Move> sortMoves(ArrayList<Move> moves){
        if (color == 0){
            moves.sort(Comparator.comparingInt(
                (Move m) -> m.getStartPoint()
            ).reversed());
        }
        else{
            moves.sort(Comparator.comparingInt(
                (Move m) -> m.getStartPoint()
            ));
        }
        return moves;
    }

    public ArrayList<Move> findAllMoves(){
        ArrayList<Point> points = Backgammon.getPoints();
        ArrayList<Move> nextMoves = new ArrayList<>();
        ArrayList<Checker> capturedPieces = new ArrayList<>();

        if (color == 0){
            capturedPieces = Checker.getCapturedW();
        }
        else{
            capturedPieces = Checker.getCapturedB();
        }

        if (capturedPieces.size() != 0){
            for (Checker c : capturedPieces){
                for (int i = 0; i < 4; i++){
                    if (diceRolls[i] == 0){
                        continue;
                    }
                    if (color == 0){
                        if (Backgammon.getPointAtId(24 - diceRolls[i]).getCheckers().size() > 1){
                            if (Backgammon.getPointAtId(24 - diceRolls[i]).getCheckers().get(0).getColor() != this.color){
                                continue;
                            }
                        }
                        nextMoves.add(new Move(-1, 24 - diceRolls[i], diceRolls[i]));
                    }
                    else{
                        if (Backgammon.getPointAtId(diceRolls[i] - 1).getCheckers().size() > 1){
                            if (Backgammon.getPointAtId(diceRolls[i] - 1).getCheckers().get(0).getColor() != this.color){
                                continue;
                            }
                        }
                        nextMoves.add(new Move(-1, diceRolls[i] - 1, diceRolls[i]));
                    }
    
                }
            }
            return nextMoves;
        }

        if (this.isAllHome()){
            for(int i = 0; i < 4; i++){
                if (diceRolls[i] == 0){
                    continue;
                }
                if (color == 0){
                    for (Checker c : myCheckers){
                        if (c.getPointId() == 24){
                            continue;
                        }
                        if (c.getPointId() + 1 <= diceRolls[i]){
                            nextMoves.add(new Move(c.getPointId(),24,diceRolls[i]));
                        }
                    }
                }
                else{
                    for (Checker c : myCheckers){
                        if (c.getPointId() == 24){
                            continue;
                        }
                        if (24 - c.getPointId() <= diceRolls[i]){
                            nextMoves.add(new Move(c.getPointId(),24,diceRolls[i]));
                        }
                    }        
                }
            }
        }

        for (Point p : points){
            if (p.getCheckers().size() == 0){
                continue;
            }
            if (p.getCheckers().get(0).getColor() != this.color){
                continue;
            }
            Checker c = p.getCheckers().get(0);
            for (int i = 0; i < 4; i++){
                if (diceRolls[i] == 0){
                    continue;
                }
                Move m;
                if (this.color == 0){
                    if ((c.getPointId() - diceRolls[i]) < 0){
                        continue;
                    } 
                    m = new Move(c.getPointId(), c.getPointId() - diceRolls[i], diceRolls[i]);
                }
                else{
                    if ((c.getPointId() - diceRolls[i]) > 23){
                        continue;
                    } 
                    m = new Move(c.getPointId(), c.getPointId() + diceRolls[i], diceRolls[i]);
                }
                nextMoves.add(m);
            } 
        }

        ArrayList<Move> filteredMoves = new ArrayList<>();

        for (Move m : nextMoves){
            if (m.getEndPoint() == 24){
                filteredMoves.add(m);
                continue;
            }
            Point p = Backgammon.getPointAtId(m.getEndPoint());
            if (p == null){
                continue;
            }

            if (p.getCheckers().size() <= 1){
                filteredMoves.add(m);
                continue;
            }
            if (p.getCheckers().get(0).getColor() == this.color){
                filteredMoves.add(m);
                continue;
            }
        }

        return filteredMoves;
    }

    private void rollDice(){
        ArrayList<Dice> die = Backgammon.getDie();
        Dice dice1 = die.get(0);
        Dice dice2 = die.get(1);
        dice1.roll();
        dice2.roll();
        diceRolls = new int[]{0,0,0,0};
        if (dice1.getValue() == dice2.getValue()){
            this.setDiceRolls(new int[]{dice1.getValue(), dice1.getValue(), dice1.getValue(), dice1.getValue()});
        }
        else{
            this.setDiceRolls(new int[]{dice1.getValue(), dice2.getValue(), 0,0});
        }
        displayDiceRolls();
    }
}