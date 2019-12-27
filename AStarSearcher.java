import java.util.ArrayList;
import java.util.PriorityQueue;
import java.lang.Math;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// TODO initialize the root state and add
		// to frontier list
		
		// Search for goal state
		Square goal = maze.getGoalSquare();

		// Search for root state
		State start = new State (maze.getPlayerSquare(),null,0,0);
		
		frontier.add(new StateFValuePair(start,Math.sqrt(Math.pow(goal.X-start.getX(),2)+Math.pow(goal.Y-start.getY(),2))));
		cost = 1;
//		maxDepthSearched = 1;
		maxSizeOfFrontier = 1;
		noOfNodesExpanded = 0;
	

		while (!frontier.isEmpty()) {
			// TODO return true if a solution has been found
			StateFValuePair currentPair = frontier.poll();
			noOfNodesExpanded += 1;
			State current = currentPair.getState();
			explored[current.getX()][current.getY()]=true;
			ArrayList<State> successors = new ArrayList<State>();
			PriorityQueue<StateFValuePair> frontier2;
//			noOfNodesExpanded += 1;
			if (current.getDepth()>maxDepthSearched) { maxDepthSearched = current.getDepth(); }
			if (current.isGoal(maze)) {
				cost=current.getDepth();
					// Print route
					while(current.getParent()!=null && !current.getParent().getSquare().equals(maze.getPlayerSquare())) {
						maze.setOneSquare(current.getParent().getSquare(), '.');
//						cost += 1;
						current=current.getParent();
					}
					return true;
			}
				
			//Expand the current state if it's NOT goal state
			else {
				successors = current.getSuccessors(explored, maze);
				if (successors!=null) {
				for(int i = 0; i < successors.size(); i++) {
//					StateFValuePair successorPair = new StateFValuePair (successors.get(i),
//							successors.get(i).getGValue()+sqrt(Math.pow(goal.X-successors.get(i).getX(),2)+Math.pow(goal.Y-successors.get(i).getY(),2)))
					// Look for replicate???
					
					boolean contains = false;
					frontier2 = new PriorityQueue<StateFValuePair>(frontier);
					
					for (int j = 0; j < frontier.size(); j++) {
						StateFValuePair pairInFrontier = frontier2.poll();
						if(pairInFrontier.getState().getSquare().X ==  successors.get(i).getSquare().X && pairInFrontier.getState().getSquare().Y ==  successors.get(i).getSquare().Y) {
							// Update frontier if g(n) < g(m)
							contains = true;
							if (pairInFrontier.getState().getGValue() > successors.get(i).getGValue()) {
								frontier.remove(pairInFrontier);
								break;
							}
						}
					}
					if (!contains) {
						frontier.add(new StateFValuePair(successors.get(i),successors.get(i).getGValue()+Math.sqrt(Math.pow(goal.X-successors.get(i).getX(),2)+Math.pow(goal.Y-successors.get(i).getY(),2))));
					}
				}
			}
			}
				if (frontier.size() > maxSizeOfFrontier) { maxSizeOfFrontier = frontier.size();}

			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}

		// TODO return false if no solution
		return false;
	}

}
