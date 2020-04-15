/*
 * Andrew Wood
 * 10/1/19
 * Main class for A*Search Programming Assignment.Uses the DriverA methods in order to function
 */
import java.util.*;
import java.lang.*;

public class AStarMain {

    public static int numRows=15;//row amount
    public static int numCols=15;//column amount
    
    public static int [][] map = new int [numRows][numCols]; //map holder
    
    public static Node [][] setup = new Node[numRows][numCols];
    
    public static Node beginning;//this will be the start node
    public static Node desired;//this will be the goal or desired node

    public static void main(String[]args)
    {
        DriverA star = new DriverA();//sets up an instance of the Driver class
        
        star.block(map);//calls the block method to make sure that 10% of the board is blocked
        star.transfer(map, setup);//sends the array into the nodes
        beginning=star.startingPoint(setup);//creates the beginning node
        desired=star.inputGoal(setup);//grabs the desired node
        star.measurement(setup, desired);//this method is used to get the heuristic of the process
        star.showGrid(setup);//this method displays the beginning map
        star.showH(setup);
        beginning.setG(0);//uses Node class to set the beginning node
        beginning.setH(setup[beginning.getRow()][beginning.getCol()].getH());//uses the setH from Node class to fill the map with values
        beginning.setF();//uses the setF from Node class to fill the map with values
 
        Node [][] duplicate = new Node[numRows][numCols];//duplicates the map node to show success

        for (int i=0;i<setup.length;i++)//initializes setup node
        {
            for (int j=0;j<setup[0].length;j++)
            {
            	duplicate[i][j]=setup[i][j];//used to fill the duplicate map with beginning map
            }
        }

        boolean keepGoing = true;//boolean used to keep searching for open spaces/stop looking

       
        ArrayList<Node> vacant = new ArrayList<>();//holds nodes that we will go to
        ArrayList<Node> old = new ArrayList<>();//holds nodes that we have been to

        vacant.add(beginning);//add beginning to open nodes to be visited
        

        while(keepGoing){//while we are still searching
            Node changing = vacant.remove(0);//takes away old nodes from nodes still left to visit

            if(changing.equals(desired)){//checks to see if goal node was a success
                System.out.println("Success!!!!!!!!!!!");
                keepGoing = false;//goal node found, so we are not searching anymore

                while(!changing.equals(beginning))
                {//while this node is equal to the starting node
                    System.out.print(changing.getParent().toString());//converts the values to string
                    changing = changing.getParent();//sets the parent node to the changing node
                    duplicate[changing.getRow()][changing.getCol()].setType(5);//fills duplicate map with correct path, 5 is used to show the current movement
                }
            }
            else
            {//finding new opportunities to move
                int newR = changing.getRow();
                int newC = changing.getCol();

                for(int i = newR - 1; i <= newR + 1; i++)//initializing searching opportunities
                {
                    for(int j = newC - 1; j <= newC + 1; j++) //moves around to find new places
                    {
                       
                        
                    	if((i >= 0 && i < numRows) && (j >= 0 && j < numRows) && (i != newR || j != newC) && (setup[i][j].getType() != 1))
                    	{//if statement and logic to find if there is a space available to move into
                            Node newNode = new Node(i, j, 0);//node at that position to judge the positions around current node
                            //System.out.println("Getting neighbors: " + newNode.toString());
                            newNode.setParent(changing);//sets parent node of changing node to the new node
                            int newG = 10;//G value incremented to 10 if it is moving up,down,left, or right
                            
                            if(Math.abs(i - newR) + Math.abs(j - newC) == 2)//returns absolute value
                            {
                                newG = 14;//diagonal value increments G by 14 for later calculation
                            }

                            newNode.setG(changing.getG() + newG);//new value of G from nodes is set to current node
                            newNode.setH(setup[beginning.getRow()][beginning.getCol()].getH());//heuristic values are set to the current node from columns, rows, and current getH method value
                            newNode.setF();//updated F value is found/set

                            if(star.comparison(newNode, old) == null)//if statement to check if the node is old
                            {
                                Node present = star.comparison(newNode, vacant);//looks at the current node to see if it is vacant
                                
                                if(present == null)
                                {
                                	vacant.add(newNode);//if it is not already there then it adds the current node to it
                                    
                                }
                                else//if it is there
                                {
                                    if(newNode.getG() < present.getG())//checking the G values to see which one is smaller
                                    {
                                    	present.setG(newNode.getG());//a shorter path has been found
                                    	present.setParent(changing);////rerouted and set to new parent node
                                    }
                                    
                                }
                            }
                        }
                    	
                    }
                }
                star.organize(vacant); old.add(changing);//instance list sorted for the smallest values and adds 
            }
            
        }
        
        System.out.println("Map progression where 5 is the moving target\n");
        star.showGrid(duplicate);//duplicated grid is finalized and printed to show the path taken
    }
}

