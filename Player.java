import java.util.ArrayList;

public abstract class Player{
    protected int[] diceRolls;
    protected int color; // 0 = white, 1 = black
    protected boolean allHome;
    protected ArrayList<Checker> myCheckers;

    public Player(int colorIn){
        diceRolls = new int[4];
        color = colorIn;
        allHome = false;

        ArrayList<Checker> allCheckers = Backgammon.getCheckers();
        myCheckers = new ArrayList<>();
        for(Checker c : allCheckers){
            if (c.getColor() == color){
                myCheckers.add(c);
            }
        }
    }

    public boolean isAllHome(){
        return allHome;
    }

    public int getColor(){
        return color;
    }

    public int[] getDiceRolls(){
        return diceRolls;
    }

    public void setDiceRolls(int[] diceRollsIn){
        diceRolls = diceRollsIn;
    }

    public void updateAllHome(){
        if (color == 0){
            for (Checker c : myCheckers){
                if (c.isTakenOff()){
                    continue;
                }
                if (c.getCurrentPoint().getPointId() > 5){
                    allHome = false;
                    return;
                }
            }
        }
        else{
            for (Checker c : myCheckers){
                if (c.isTakenOff()){
                    continue;
                }
                if (c.getCurrentPoint().getPointId() < 18){
                    allHome = false;
                    return;
                }
            }
        }
        allHome = true;
    }

    protected void displayDiceRolls(){
        // this is created just for testing
        System.out.print("[");
        for (int i=0; i<4; i++){
            System.out.print(diceRolls[i]);
        }
        System.out.print("]");
        System.out.println();
    }

    public boolean comeInOn(Point p){
        // This function is important for captured pieces being re-entered onto the board
        if (color == 0){
            if (p.getCheckers().size() > 1){
                if (p.getCheckers().get(0).getColor() == 1){
                    // this stops allowing put pieces ontop of towers of opposing colour
                    return false;
                }
            }
            if (p.getPointId() < 18){
                return false;
            }
            for (int i = 3; i >= 0; i--){
                if (diceRolls[i] == 24 - p.getPointId()){
                    if ((p.getCheckers().size() == 1) && (p.getCheckers().get(0).getColor() == 1)){
                        // this section deals with capturing on the same move that you enter
                        p.getCheckers().get(0).capture();
                        p.popChecker();
                    }
                    diceRolls[i] = 0;
                    Checker c = Checker.popCapturedW();
                    c.putOnPoint(p);
                    allHome = false;
                    return true;
                }
            }
        }
        else{
            if (p.getCheckers().size() > 1){
                if (p.getCheckers().get(0).getColor() == 0){
                    // this stops allowing put pieces ontop of towers of opposing colour
                    return false;
                }
            }
            if (p.getPointId() > 5){
                return false;
            }
            for (int i = 3; i >= 0; i--){
                if (diceRolls[i] == 1 + p.getPointId()){
                    if ((p.getCheckers().size() == 1) && (p.getCheckers().get(0).getColor() == 0)){
                        // this section deals with capturing on the same move that you enter
                        p.getCheckers().get(0).capture();
                        p.popChecker();
                    }
                    diceRolls[i] = 0;
                    Checker c = Checker.popCapturedB();
                    c.putOnPoint(p);
                    allHome = false;
                    return true;
                }
            }
        }
        return false;
    }

    public void endTurn(){
        Backgammon.swapPlayers();
        if (allHome){
            System.out.println("Ready to start taking pieces off!");
        }
        else{
            System.out.println("Still some pieces need to come home");
        }
    }

    public abstract void beginTurn();

    public void moveChecker(Point endPoint){
        return;
    }

    public Point getSelected(){
        return null;
    }

    public void setSelected(Point p){
        return;
    }

    public boolean isValidMove(Point p){
        return false;
    }
}