/**
 * Position.java
 * A simple program which stores a position object which consists of a row and column.
 * The methods it contains other than the getters are toString(), equals(), and hashCode().
 *
 * @author Ali Malik
 * Professor Zhong CS 310-001
 * @since 03-18-2018
 */

class Position{
    // this is the class that represent one cell position in a 2D grid

    // row and column
    private int row;
    private int col;

    /**
     * Initialize the variables
     * @param row set this.row to row
     * @param col set this.col to col
     */
    public Position(int row, int col){
        // constructor to initialize your attributes
        this.col = col;
        this.row = row;
    }

    // accessors of row and column

    /**
     * @param none
     * @return return the number of rows
     */
    public int getRow(){ return this.row;}

    /**
     * @param none
     * @return the number of columns
     */
    public int getCol(){ return this.col;}

    /**
     * Convert the position in to <row, col> format
     * @return a String representation of <row, col>
     */
    public String toString(){
        // return string representation of a position
        // row R and col C must be represented as <R,C> with no spaces
        String s = String.format("<%d,%d>", this.row, this.col);
        return s;
    }

    /**
     * Checks if the toString() representation of the objects is equal
     * @param obj
     * @return a boolean, true: if the two objects are equal and false otherwise
     */
    @Override
    public boolean equals(Object obj){
        // check whether two positions are the same
        // return true if they are of the same row and the same column
        // return false otherwise
        if(obj.toString().equals(toString())) { //compare the toString() representations
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Use Math.abs() to make sure that the hashCode is positive
     * @return an integer which represents the hashCode of the object.
     * HashCode obtained by taking the hashCode() of the toString() representation
     */
    @Override
    public int hashCode(){
        // compute an integer hash code for this object
        // must follow hash contract and distribute well
        return Math.abs(toString().hashCode()); //take the hashCode() of the toString() representation and make sure it's positive
    }



    //----------------------------------------------------
    // example testing code... make sure you pass all ...
    // and edit this as much as you want!


    public static void main(String[] args){
        Position p1 = new Position(3,5);
        Position p2 = new Position(3,6);
        Position p3 = new Position(2,6);
        Position p4 = new Position(2,6);

        if (p1.getRow()==3 && p1.getCol()==5 && "<3,5>".equals(p1.toString())){
            System.out.println("Yay 1");
        }

        if (!p1.equals(p2) && !p2.equals(p3) && p1.equals(new Position(3,5))){
            System.out.println("Yay 2");
        }

        if (p1.hashCode()!=p3.hashCode() && p1.hashCode()!=(new Position(5,3)).hashCode() &&
                p1.hashCode() == (new Position(3,5)).hashCode() && p1.hashCode() != p2.hashCode()){
            System.out.println("Yay 3");
        }

        if (p3.hashCode() == p4.hashCode()) {
            System.out.println("Yay 4");
        }


    }

}
