public abstract class Player{
    protected int[] diceRolls;
    protected int color; // 0 = white, 1 = black

    public Player(int colorIn){
        diceRolls = new int[4];
        color = colorIn;
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

    protected void displayDiceRolls(){
        // this is created just for testing
        System.out.print("[");
        for (int i=0; i<4; i++){
            System.out.print(diceRolls[i]);
        }
        System.out.print("]");
        System.out.println();
    }

    public void comeInOn(Point p){
        // This function is important for captured pieces being re-entered onto the board
        if (color == 0){
            if (p.getCheckers().size() > 1){
                if (p.getCheckers().get(0).getColor() == 1){
                    // this stops allowing put pieces ontop of towers of opposing colour
                    return;
                }
            }
            if (p.getPointId() < 18){
                return;
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
                    return;
                }
            }
        }
        else{
            if (p.getCheckers().size() > 1){
                if (p.getCheckers().get(0).getColor() == 0){
                    // this stops allowing put pieces ontop of towers of opposing colour
                    return;
                }
            }
            if (p.getPointId() > 5){
                return;
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
                    return;
                }
            }
        }
    }

    public void endTurn(){
        Backgammon.swapPlayers();
    }

    public abstract void beginTurn();
    public abstract void moveChecker(Point endPoint);

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