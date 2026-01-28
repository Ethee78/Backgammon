import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.animation.PauseTransition;
import javafx.util.Duration; 


public class FarPlayer extends Player{
    /* This is a very basic AI agent, that will always pick moves
    by prioritizing moving the pieces furthest away from their home
    fist.
    */

    public FarPlayer(int colorIn){
        super(colorIn);
    }

    public void beginTurn(){
        System.out.println("Selecting move");
        rollDice();

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            makeMoves();
        });
        pause.play();
    }

    public void makeMoves(){
        ArrayList<Checker> allCheckers = Backgammon.getCheckers();
        ArrayList<Checker> myCheckers = new ArrayList<>();

        for(Checker c : allCheckers){
            if (c.getColor() == color){
                myCheckers.add(c);
            }
        }

        sortCheckers(myCheckers);
        while (!(Arrays.equals(this.getDiceRolls(), new int[]{0,0,0,0}))){
            Checker c = myCheckers.get(0);

            for (int i = 0; i < 4; i++){
                if (diceRolls[i] == 0){
                    continue;
                }
                int possibleMove = 0;
                if (color == 0){
                    //white
                    possibleMove = c.getCurrentPoint().getPointId() - diceRolls[i];
                }
                else{
                    //black
                    possibleMove = c.getCurrentPoint().getPointId() + diceRolls[i];
                }
                Point p = Backgammon.getPointAtId(possibleMove);
                if (p == null){
                    continue;
                }
                if (isValidMove(p)){
                    System.out.println("Moved from point " + c.getCurrentPoint().getPointId() + " to point " + p.getPointId());
                    c.getCurrentPoint().popChecker().putOnPoint(p);
                    diceRolls[i] = 0; 
                }
            }
            myCheckers.remove(c);
        }
        endTurn();
    }

    public void sortCheckers(ArrayList<Checker> checkerArr){
        if (color == 0){
            checkerArr.sort(Comparator.comparingInt(
                (Checker c) -> c.getCurrentPoint().getPointId()
            ).reversed());
        }
        else{
            checkerArr.sort(Comparator.comparingInt(
                (Checker c) -> c.getCurrentPoint().getPointId()
            ));
        }
    }

    @Override
    public boolean isValidMove(Point p){
        /* this function works quite differently to the equivalent in the HumanPlayer
        in that one we need to check if the move is reachable by dice, whilst here the
        function is only ever called on moves that are reachable by dice
        */

        if (p.getCheckers().size() == 0){
            return true;
        }
        else if (p.getCheckers().get(0).getColor() == this.color){
            return true;
        }
        else{
            if (p.getCheckers().size() == 1){
                // code for capturing goes here 
                p.getCheckers().get(0).capture();
                p.popChecker();
                return true;
            }
            else{
                return false;
            }
        }
    }

    private void rollDice(){
        ArrayList<Dice> die = Backgammon.getDie();
        System.out.println(die.size());
        Dice dice1 = die.get(0);
        Dice dice2 = die.get(1);
        dice1.roll();
        dice2.roll();
        if (Arrays.equals(this.getDiceRolls(), new int[]{0,0,0,0})){
            if (dice1.getValue() == dice2.getValue()){
                this.setDiceRolls(new int[]{dice1.getValue(), dice1.getValue(), dice1.getValue(), dice1.getValue()});
            }
            else{
                this.setDiceRolls(new int[]{dice1.getValue(), dice2.getValue(), 0,0});
            }
        }
        displayDiceRolls();
    }

    public void moveChecker(Point endPoint){
        return;
    }
}