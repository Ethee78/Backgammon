public class Move{
    private int startPoint;
    private int endPoint;
    private int dice;

    public Move(int startPointIn, int endPointIn, int diceIn){
        startPoint = startPointIn;
        endPoint = endPointIn;
        dice = diceIn;
    }

    public int getStartPoint(){
        return startPoint;
    }

    public int getEndPoint(){
        return endPoint;
    }

    public int getDice(){
        return dice;
    }

    public void print(){
        if (startPoint == -1){
            System.out.println("Move: Coming in on " + endPoint);
            return;
        }
        if (endPoint == 24){
            System.out.println("Move: Taking checker off of " + startPoint);
            return;
        }
        System.out.println("Move: " + startPoint + " -> " + endPoint);
    }

    public void doMove(){
        print();

        Point p1 = Backgammon.getPointAtId(startPoint);
        Point p2 = Backgammon.getPointAtId(endPoint);

        if (startPoint == -1){
            // This indicates that this is a move where a captured piece is being put back onto the board

            Player player = Backgammon.currentPlayer;

            if (p2.getCheckers().size() == 1){
                if (p2.getCheckers().get(0).getColor() != player.getColor()){
                    p2.getCheckers().get(0).capture();
                    p2.popChecker();
                }
            }

            Checker c;
            if (player.getColor() == 0){
                c = Checker.popCapturedW();
            }
            else{
                c = Checker.popCapturedB();
            }

            c.putOnPoint(p2);
            return;
        }

        if (endPoint == 24){
            // This indicates that this is a move where a piece is being taken off after all pieces are home

            Checker c = p1.popChecker();
            c.takeOff();
            return;
        }

        Checker c = p1.popChecker();
        if (p2.getCheckers().size() == 1){
            if (p2.getCheckers().get(0).getColor() != c.getColor()){
                p2.getCheckers().get(0).capture();
                p2.popChecker();
            }
        }
        c.putOnPoint(p2);
    }
}