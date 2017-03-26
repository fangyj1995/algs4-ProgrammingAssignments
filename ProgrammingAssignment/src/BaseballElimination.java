import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	private int n;
	private int[] win;
	private int[] lose;
	private int[] remain;
	private int[][] game;
	private Map<String,Iterable<String>> certificates;
	private Map<String,Integer> teamIndex;
	private String[] teamName;
	private int numOfGames =1;

	public BaseballElimination(String filename){// create a baseball division from given filename in format specified below
		In in = new In(filename);
		n = Integer.parseInt(in.readLine());
		teamIndex = new HashMap<>();
		certificates = new HashMap<>();
		win = new int[n];lose = new int[n];
		remain = new int[n]; game = new int[n][n];
		teamName = new String[n];
		numOfGames = (n - 1)*(n - 2) / 2;
		for(int i = 0;in.hasNextLine();i++){
			String[] line = in.readLine().trim().split(" +");
			teamIndex.put(line[0],i);
			teamName[i] = line[0];
			win[i] = Integer.parseInt(line[1]);
			lose[i] = Integer.parseInt(line[2]);
			remain[i] = Integer.parseInt(line[3]);
			for(int j = 0; j < n; j++){
				game[i][j] = Integer.parseInt(line[4 + j]);
			}
		}
		for(int i = 0 ;i < n ;i++){
			checkElimination(i);
		}
	}
	private void checkElimination(int teamIndex){
		if(checkTrival(teamIndex)) return;
		checkNonTrival(teamIndex);	
	}
	private boolean checkTrival(int teamIndex){
		int totalCanWin = win[teamIndex] + remain[teamIndex];
		List<String> certificate = new LinkedList<>();
		for(int i = 0 ;i < n ;i++){
			if(totalCanWin < win[i]) {
				certificate.add(teamName[i]);
			}
		}
		if(!certificate.isEmpty()){
			this.certificates.put(teamName[teamIndex], certificate);
			return true;
		}
		return false;
	}
	private boolean checkNonTrival(int teamIndex){
		int totalVertex = 2 + n-1+ numOfGames;
		FlowNetwork flow = new FlowNetwork(totalVertex);
		int s = 0 ;int t = totalVertex - 1;
		int gameVertex = 1;
		for(int j = 0; j < n; j++){
			if(j == teamIndex ) continue;
			for(int k = j + 1; k < n; k++){//game for team (j,k)
				if(k == teamIndex ) continue;
				flow.addEdge(new FlowEdge(s,gameVertex,game[j][k]));	
				flow.addEdge(new FlowEdge(gameVertex,(j > teamIndex? j - 1: j )+ numOfGames + 1,Double.POSITIVE_INFINITY));
				flow.addEdge(new FlowEdge(gameVertex,(k > teamIndex? k - 1: k )+ numOfGames + 1,Double.POSITIVE_INFINITY));
				gameVertex++;
			}
		}
		int totalCanWin = win[teamIndex] + remain[teamIndex];
		for(int j = 0; j < n; j++){
			if(j == teamIndex ) continue;
			flow.addEdge(new FlowEdge((j > teamIndex? j - 1: j )+ numOfGames + 1,t,totalCanWin - win[j]));
		}
		FordFulkerson ford = new FordFulkerson(flow,s,t);
		for(FlowEdge edge :flow.adj(0)){
			if(edge.flow() < edge.capacity()){//not full eliminate
				//create certificate
				List<String> certificate = new LinkedList<>();
				for(int i = 0 ;i < n ; i++){
					if(i == teamIndex ) continue;
					if(ford.inCut((i > teamIndex? i - 1: i )+ numOfGames + 1)){
						certificate.add(teamName[i]);
					}
				}
				certificates.put(teamName[teamIndex], certificate);
				return true;
			}
		}	
		return false;//not eliminated
	}
	public int numberOfTeams(){
		return n;// number of teams
	}
	
	public Iterable<String> teams(){
		return this.teamIndex.keySet();// all teams
	}
	
	public int wins(String team){
		if(!this.teamIndex.containsKey(team)) throw new IllegalArgumentException();
		int index = this.teamIndex.get(team);
		return this.win[index];
	}
	
	public int losses(String team){
		if(!this.teamIndex.containsKey(team)) throw new IllegalArgumentException();
		int index = this.teamIndex.get(team);
		return this.lose[index];
	}
	
	public int remaining(String team){
		if(!this.teamIndex.containsKey(team)) throw new IllegalArgumentException();
		int index = this.teamIndex.get(team);
		return this.remain[index];
	}
	
	public int against(String team1, String team2){
		if(!this.teamIndex.containsKey(team1)||!this.teamIndex.containsKey(team2)) throw new IllegalArgumentException();
		int index1 = this.teamIndex.get(team1);
		int index2 = this.teamIndex.get(team2);
		return game[index1][index2];// number of remaining games between team1 and team2
	}
	
	public boolean isEliminated(String team){// is given team eliminated?
		if(!this.teamIndex.containsKey(team)) throw new IllegalArgumentException();
		return this.certificates.containsKey(team);
	}	
	
	public Iterable<String> certificateOfElimination(String team){
		if(!this.teamIndex.containsKey(team)) throw new IllegalArgumentException();
		if(! this.certificates.containsKey(team))return null;
		else return this.certificates.get(team);
	}	
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}


}
