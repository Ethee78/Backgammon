import java.util.Arrays;
import java.util.ArrayList;

public class FarPlayer extends Player{
    public FarPlayer(int colorIn){
        super(colorIn);
    }

    public void beginTurn(){
        System.out.println("Selecting move");
        rollDice();

        ArrayList<Checker> allCheckers = Backgammon.getCheckers();
        ArrayList<Checker> myCheckers = new ArrayList<>();

        for(Checker c : allCheckers){
            System.out.println(c.getCurrentPoint().getPointId());
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