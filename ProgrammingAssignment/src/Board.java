import java.util.LinkedList;
import java.util.List;

public class Board {
	private final int[][] blocks;
	private final int dimension;
	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		this.dimension = blocks.length;
		this.blocks = new int[dimension][dimension];
		for(int i = 0; i < dimension ; i++) {
			for(int j = 0; j < dimension; j++)
				this.blocks[i][j] = blocks[i][j];
		}
	}
    	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {                // board dimension n
		return dimension;
	}
	public int hamming()  {
		// number of blocks out of place
		int hamming = 0;
		for(int i = 0;i < dimension; i++){
			for(int j = 0;j < dimension; j++){
				if(blocks[i][j] != (i * dimension) + j+1)
					hamming++;
			}
		}
		return hamming-1;
	}
	public int manhattan(){
		// sum of Manhattan distances between blocks and goal
		int manhattan = 0;
		for(int i = 0;i < dimension; i++){
			for(int j = 0;j < dimension; j++){
				int val  = blocks[i][j];
				if(val == 0) continue;
				int i1 , j1;
				i1 = (val - 1) / dimension; 
				j1 = val - i1 * dimension - 1;
				manhattan+=(Math.abs(i1-i)+Math.abs(j1-j));
			}
		}
		return manhattan;
	}
	public boolean isGoal() {
		// is this board the goal board?
		return hamming() == 0;
	}
	public Board twin()  {
		// a board that is obtained by exchanging any pair of blocks
		Board twin = new Board(this.blocks);
		int i1=0,j1=0;
		int t=0;
		for(int i = 0;i < dimension; i++){
			for(int j = 0;j < dimension; j++){
				if(twin.blocks[i][j] != 0){
					if(t==0){
						t=twin.blocks[i][j];
						i1=i;j1=j;
					}
					else{
						twin.blocks[i1][j1] = twin.blocks[i][j];
						twin.blocks[i][j] = t;
						i = dimension;//break i
						break;
					}
				}
			}
		}
		return twin;
	}
	public boolean equals(Object y)   {
		// does this board equal y?
		if(y == null) return false;
		if(! (y instanceof Board))
			return false;
		Board that = (Board) y;
		if(this.dimension != that.dimension)
			return false;
		for(int i = 0;i < dimension; i++){
			for(int j = 0;j < dimension; j++){
				if(blocks[i][j] != that.blocks[i][j])
					return false;
			}
		}
		return true;
	}
	public Iterable<Board> neighbors() {    // all neighboring boards
		List<Board> neighbors = new LinkedList<>();
		for(int i = 0;i < dimension; i++){
			for(int j = 0;j < dimension; j++){
				if(blocks[i][j] == 0){
					if(i != 0){//top
						Board top = new Board(blocks);
						top.blocks[i][j]=top.blocks[i-1][j];
						top.blocks[i-1][j]=0;
						neighbors.add(top);
					}
					if(i != dimension-1){//bottom
						Board bottom = new Board(blocks);
						bottom.blocks[i][j]=bottom.blocks[i+1][j];
						bottom.blocks[i+1][j]=0;
						neighbors.add(bottom);
					}
					if(j != 0){//left
						Board left = new Board(blocks);
						left.blocks[i][j]=left.blocks[i][j-1];
						left.blocks[i][j-1]=0;
						neighbors.add(left);
					}
					if(j != dimension-1){//right
						Board right = new Board(blocks);
						right.blocks[i][j]=right.blocks[i][j+1];
						right.blocks[i][j+1]=0;
						neighbors.add(right);
					}
				}
			}
		}
		return neighbors;
	}
	public String toString()   {            // string representation of this board (in the output format specified below)
		StringBuilder sb = new StringBuilder();
		sb.append(dimension+"\n");
		for(int i = 0;i < dimension; i++){
			for(int j = 0;j < dimension; j++){
				sb.append(String.format("%2d ", blocks[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {// unit tests (not graded)
		
	}

}
