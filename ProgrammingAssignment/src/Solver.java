import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Iterable<Board> solution;
	private int moves;
	private boolean isSolvable;
	private static class SearchNode{
		SearchNode  pre;
		Board board;
		int moves;
		
		public SearchNode(SearchNode pre, Board board, int moves) {
			super();
			this.pre = pre;
			this.board = board;
			this.moves = moves;
		}
	}
	private static class NodeComparator implements Comparator<SearchNode>{

		@Override
		public int compare(SearchNode n1, SearchNode n2) {
			return (n1.moves+n1.board.manhattan()) - (n2.moves+n2.board.manhattan());
		}
		
	}
	public Solver(Board initial){           
		// find a solution to the initial board (using the A* algorithm)
		if(initial == null)
			throw new NullPointerException();
		solve(initial);
	}
	
	private void solve(Board initial){
		MinPQ<SearchNode> queue=new MinPQ<>(new NodeComparator());
		MinPQ<SearchNode> twinqueue=new MinPQ<>(new NodeComparator());
		SearchNode root = new SearchNode(null,initial,0);
		SearchNode twinroot = new SearchNode(null,initial.twin(),0);
		queue.insert(root);
		twinqueue.insert(twinroot);
		while(!queue.isEmpty()||!twinqueue.isEmpty()){
			SearchNode cur = queue.delMin();
			SearchNode twincur = twinqueue.delMin();
			if(cur.board.isGoal())
			{
				this.moves = cur.moves;
				Stack<Board> s= new Stack<>();
				s.push(cur.board);
				while(cur.pre!=null){
					s.push(cur.pre.board);
					cur = cur.pre;
				}
				this.solution = s;
				this.isSolvable = true;
				return;
			}
			if(twincur.board.isGoal()){
				this.isSolvable = false;
				this.moves=-1;
				return;
			}
			
			for(Board board : cur.board.neighbors()){
				if(cur.pre!=null&&board.equals(cur.pre.board))
					continue;
				SearchNode child = new SearchNode (cur, board , cur.moves+1);
				queue.insert(child);
			}
			
			for(Board board : twincur.board.neighbors()){
				if(twincur.pre!=null&&board.equals(twincur.pre.board))
					continue;
				SearchNode child = new SearchNode (twincur, board , twincur.moves+1);
				twinqueue.insert(child);
			}
		}
	}
    public boolean isSolvable(){
		return isSolvable;
    	// is the initial board solvable?
    }
    public int moves(){
		return moves;                     
    	// min number of moves to solve initial board; -1 if unsolvable
    	
    }
    public Iterable<Board> solution(){
		return solution;      
    	// sequence of boards in a shortest solution; null if unsolvable
    	
    }
    public static void main(String[] args){ // solve a slider puzzle (given below)
    	// create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
    }

}
