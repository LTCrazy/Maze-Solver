import java.util.ArrayList;
import java.util.LinkedList;

//import com.sun.corba.se.spi.orbutil.fsm.State;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD
		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		//...
		// Search for starting point
		State start = new State (maze.getPlayerSquare(),null,0,0);
		
		
		queue.add(start);
		cost = 1;
		maxSizeOfFrontier = 1;
		// Loop through until find a solution or run out of states in Frontier
		while (!queue.isEmpty()) {
				// TODO return true if find a solution
			State current = queue.pop();
			ArrayList<State> successors = new ArrayList<State>();
			noOfNodesExpanded += 1;
			explored[current.getX()][current.getY()] = true;
			if (current.getDepth()>maxDepthSearched) { maxDepthSearched = current.getDepth(); }
				if (current.isGoal(maze)) { 					
					// Print route
					while(current.getParent()!=null && !current.getParent().getSquare().equals(maze.getPlayerSquare())) {
						maze.setOneSquare(current.getParent().getSquare(), '.');
						cost += 1;
						current=current.getParent();		
					}
					return true;
				}
				// Expand the current state if it's NOT goal state
				else {
//					noOfNodesExpanded += 1;
					successors = current.getSuccessors(explored, maze);
					if (successors!=null) {
					for(int i = 0; i < successors.size(); i++) {
						//How to compare???
						if (!queue.contains(successors.get(i)) && !explored[successors.get(i).getX()][successors.get(i).getY()]) {
							queue.add(successors.get(i));
							explored[successors.get(i).getX()][successors.get(i).getY()]=true;
						}
						}
						
					}
				}
					if (queue.size() > maxSizeOfFrontier) { maxSizeOfFrontier = queue.size();}
				}
			
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			
			// TODO update the maze if a solution found

			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
		

		// TODO return false if no solution
		return false;
	}
}
