package Exceptions;

public class InfiniteRecursionError extends Error{

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public InfiniteRecursionError(int x1, int y1){
        this.x1 = x1;
        this.y1 = y1;
        printException();
    }



    private void printException(){
        String firstCell = (char)('A' + y1)+"";
        firstCell+=(++x1);

        System.out.println("Error: Infinite recursion found!\nCell " + firstCell + " refer to the cell, while this cell refer to the " + firstCell);
    }
}
