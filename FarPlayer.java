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
        ArrayList<Checker> unCapturedCheckers = new ArrayList<>();
        ArrayList<Checker> capturedCheckers = new ArrayList<>();
        Iterator<Checker> iterator = myCheckers.iterator();


        if (this.isAllHome()){
            sortCheckers(myCheckers);
            for (Checker c : myCheckers){
                for (int i = 0; i < 4; i++){
                    if (c.getCurrentPoint() == null){
                        continue;
                    }
                    if (((diceRolls[i] == c.getCurrentPoint().getPointId() + 1) && (color == 0)) ||
                        (diceRolls[i] == 24 - c.getCurrentPoint().getPointId()) && (color == 1)){

                        Checker r = c.getCurrentPoint().popChecker();
                        System.out.println("Taken checker off of point " + r.getCurrentPoint().getPointId());
                        r.goHome();
                        diceRolls[i] = 0;
                    }
                }
            }

            while (iterator.hasNext()){
                Checker c = iterator.next();
                if (c.isHome()){
                    iterator.remove();
                }
            }

            endTurn();
            return;
        }

        for(Checker c : myCheckers){
            if (c.getCurrentPoint() == null){
                capturedCheckers.add(c);
            } 
            else{
                unCapturedCheckers.add(c);
            }
        }

        int replacedCounter = 0;
        if (capturedCheckers.size() != 0){
            for (Checker c : capturedCheckers){
                if (color == 0){
                    for (int i = 0; i < 4; i++){
                        if (diceRolls[i] == 0){
                            continue;
                        }
                        if (comeInOn(Backgammon.getPointAtId(24 - diceRolls[i]))){
                            replacedCounter = replacedCounter + 1;
                            break;
                        }
                    }
                }
                else{
                    for (int i = 0; i < 4; i++){
                        if (diceRolls[i] == 0){
                            continue;
                        }
                        if (comeInOn(Backgammon.getPointAtId(diceRolls[i] - 1))){
                            replacedCounter = replacedCounter + 1;
                            break;
                        }
                    }
                }
            }
        }

        if (capturedCheckers.size() != replacedCounter){
            // this ensures that if there are any checkers that cannot be placed then the turn ends
            endTurn();
            return;
        }

        sortCheckers(unCapturedCheckers);
        while (!(Arrays.equals(this.getDiceRolls(), new int[]{0,0,0,0}))){
            if (unCapturedCheckers.size() == 0){
                System.out.println("No available moves - skipping turn now");
                endTurn();
                return;
            }
            Checker c = unCapturedCheckers.get(0);

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
                    updateAllHome();
                    diceRolls[i] = 0; 
                }
            }
            unCapturedCheckers.remove(c);
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