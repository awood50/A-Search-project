/*
 * Andrew Wood
 * 10/1/19
 * Driver class for A*Search Programming Assignment. Methods here are used to calculate and feed information to the Main class
 */
import java.util.*;
import java.lang.*;

public class DriverA {

    //shows the beginning map
    public void showGrid(Node [][]grid) {
        
        for(int i=0;i<grid.length;i++) {
            System.out.print("\n");
          
            

            //filters through map and getst the type of each node
            for(int j=0;j<grid[0].length;j++) {
                System.out.print(grid[i][j].getType()+ " ");
                System.out.print(" ");
            }
            }System.out.println();
    }

    //10% of each node needs to be blocked
    public void block(int [][]blocked) {//method to block paths
        Random rnd = new Random();//random value
        for(int i=0;i<blocked.length;i++)
        {//sets up blocked node
            for(int j=0;j<blocked[0].length;j++)
            {

                float possible = rnd.nextFloat();//float used since decimals are used
                if (possible <= 0.10f){//if it is less than .10
                   
                    blocked[i][j]=1;//that path will be blocked at the position
            }
                }
            }
    }

    //the array used will be imported into the nodes
    public void transfer(int [][] first, Node [][]second){//array into node setup
        for (int i=0; i< first.length;i++)
        {//initialize
           
            for (int j=0; j<first[0].length;j++)
            {
                second[i][j] = new Node(i, j, first[i][j]);//node now contains correct elements and size
            }
        }
    }



    //beginning node is found
    public Node startingPoint(Node[][]q){
        Scanner input = new Scanner(System.in);//new scanner
        System.out.println("Enter the start row: ");
        int startRow = input.nextInt();//user input row 
        System.out.println("Enter the start column");
        int startColumn = input.nextInt();//user input column
        
       
        q[startRow][startColumn].setType(4);//starting node set up and given the type of 4

        System.out.println("Beginning node: "+ startRow +"," + startColumn+"\n");//prints beginning node location

        Node startNode = new Node(startRow, startColumn, 4);//places the number 4 into the beginning node position
        startNode.setG(0);//g value is set
        startNode.setF();//f value is set
        return startNode;//returns the node location
    }

    public Node inputGoal(Node [][]test){//method used to have the user enter the end location
        Scanner input = new Scanner(System.in);//scanner used
        System.out.println("Enter the goal row: ");
        int gRow = input.nextInt();//user input
        System.out.println("Enter the goal column: ");
        int gColumn = input.nextInt();//user input
        System.out.println("Goal node is: "+ gRow + ", " + gColumn +"\n");//prints user node input for reference  
        System.out.println("Starting node makred with a 4\n");
        test[gRow][gColumn].setType(9);//sets the type of the goal node to 9 for easy location on the map
        Node testing = new Node(gRow, gColumn, 3);//puts the desired goal into the node
        testing.setF();//sets value of f
        return testing;//returns node value

    }
     public static void measurement(Node [][]x, Node y)//method finds heuristic measurement using the nodes
     {
         for (int i=0;i<x.length; i++)//initializes
         {
             for(int j=0;j<x[0].length; j++)
             {
                 x[i][j].setH((10*(Math.abs(i-y.getRow())))+(10*(Math.abs(j-y.getCol()))));
                 //math statement to calculate heuristic and math using node locations
             }
             
         }
     }


    public void showH(Node [][]pic) {

        System.out.println("\nHeuristic map");
        for(int i=0;i<pic.length;i++) 
        {
            System.out.print("\n");//just for spacing
            

            //traverses and gets the value of type for the node
            for(int j=0;j<pic[0].length;j++) 
            {
                if ((pic[i][j].getH()>=10)&&(pic[i][j].getH()<=90))//getting the value for each node
                {
                System.out.print(pic[i][j].getH()+ "  ");//gets the value of H and adds space
                }

                else if (pic[i][j].getH()<10)//if H is less than 10
                {
                    System.out.print(pic[i][j].getH()+ "   ");//prints this
                }
                else
                    System.out.print(pic[i][j].getH()+ " ");//anything else is printed like this

                System.out.print(" ");//spacing
            }
            
        }
        System.out.println("\n");//spacing
    }

    //attempts to locate node within map
    public static Node comparison(Node no, ArrayList<Node> li){
        for(int i = 0; i < li.size(); i++)
        {
            if(li.get(i).equals(no))//if the list value found equals the node value
            {
                return li.get(i);//the location/value is returned
            }
            
        }
        return null;//nothing is returned if it is not found
    }

    //goes through arraylist and compares f values
    public static void organize(ArrayList<Node> nodeL){
        int low; //lowest value
        Node trial;//testing node
        for(int i = 0; i < nodeL.size(); i++)//setup
        {
        	low = i;//lowest value=low
            
            int b=nodeL.size() - 1;//looping through the list
            for(int w = i;w < b; w++) 
            {//goes from beginning or top to bottom
                if (nodeL.get(w + 1).getF() < nodeL.get(low).getF()) //getting f values
                {//looking for the smallest f value
                	low = w + 1;
                	}
            }
            trial = nodeL.get(i);//testing node
            nodeL.set(i, nodeL.get(low));//setting values with lowest f
            nodeL.set(low, trial);}//updating the node
    }
}