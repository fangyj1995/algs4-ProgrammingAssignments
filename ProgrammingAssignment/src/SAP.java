import edu.princeton.cs.algs4.*;

/**
 * Created by A on 2017/1/31.
 */
public final class SAP {
    private final Digraph g;
    private final static class SapRes{
        private int length = -1;
        private int v = -1;

        public SapRes(int v , int length){
            this.length = length;
            this.v = v;
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        this.g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if(v < 0||v > g.V() - 1 ||w < 0||w > g.V() - 1)
            throw new IndexOutOfBoundsException();
        return shortest(new BreadthFirstDirectedPaths (g , v) , new BreadthFirstDirectedPaths (g , w)).length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if(v < 0||v > g.V() - 1 ||w < 0||w > g.V() - 1)
            throw new IndexOutOfBoundsException();
        return shortest(new BreadthFirstDirectedPaths (g , v) , new BreadthFirstDirectedPaths (g , w)).v;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null)
            throw new NullPointerException();
        for(int i:v){
            for(int j:w){
                if(i < 0||i > g.V() - 1 ||j < 0||j > g.V() - 1)
                    throw new IndexOutOfBoundsException();
            }
        }
        return shortest(new BreadthFirstDirectedPaths (g , v) , new BreadthFirstDirectedPaths (g , w)).length;
    }
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null)
            throw new NullPointerException();
        for(int i:v){
            for(int j:w){
                if(i < 0||i > g.V() - 1 ||j < 0||j > g.V() - 1)
                    throw new IndexOutOfBoundsException();
            }
        }
        return shortest(new BreadthFirstDirectedPaths (g , v) , new BreadthFirstDirectedPaths (g , w)).v;
    }

    private SapRes shortest(BreadthFirstDirectedPaths bfsV , BreadthFirstDirectedPaths bfsW){
        int minsum = Integer.MAX_VALUE;
        int ancestor = -1;
        for(int i = 0 ;i< g.V() ; i++){
            if(bfsV.hasPathTo(i)&& bfsW.hasPathTo(i) ) {
                int distsum = bfsV.distTo(i)+bfsW.distTo(i);
                if(distsum < minsum) {
                    minsum = distsum;
                    ancestor = i;
                }
            }
        }
        if(ancestor == -1)
            minsum = -1;
        SapRes res = new SapRes(ancestor , minsum);
        return res;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }


}
