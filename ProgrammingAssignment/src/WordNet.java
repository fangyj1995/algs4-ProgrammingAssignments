import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * Created by A on 2017/1/31.
 */
public class WordNet {
    private final Digraph g;
    private int n = 0;
    private final Map<String, Set<Integer>> synMap = new HashMap<>();
    private final Set<String> nouns;
    private final SAP sap;
    private final ArrayList<String> synList = new ArrayList<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if( synsets == null || hypernyms == null)
            throw new NullPointerException();
        In in = new In(synsets);
        while(in.hasNextLine()){
            String [] line = in.readLine().split(",",3);
            int id = Integer.parseInt(line[0]);

            synList.add(line[1]);
            String [] synset = line[1].split(" ");
            //String gloss = line[2];
            for(String syn :synset){
                if(!synMap.containsKey(syn))
                    synMap.put(syn, new TreeSet<Integer>());
                synMap.get(syn).add(id);
            }
            //System.out.println(id +" , " + Arrays.toString(synset) + " , " +gloss);
            n++;
        }
        nouns = synMap.keySet();
        in.close();
        in = new In(hypernyms);
        g = new Digraph(n);
        while(in.hasNextLine()){
            String [] edge = in.readLine().split(",");
            int v = Integer.parseInt(edge[0]);
            for(int i=1 ;i < edge.length;i++){
                int w = Integer.parseInt(edge[i]);
                //System.out.println(v+" "+w);
                g.addEdge(v,w);
            }

        }
        in.close();
        int roots = 0;
        for(int i =0 ;i < g.V() ; i++){
            if(g.outdegree(i) == 0)
                roots ++;
            if(roots > 1)
                throw new IllegalArgumentException();
        }
        if( roots != 1)
            throw new IllegalArgumentException();
        DirectedCycle cycleFinder = new DirectedCycle(g);
        if(cycleFinder.hasCycle())
            throw new IllegalArgumentException();
        sap = new SAP(g);
        //IllegalArgumentException
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null)
            throw new NullPointerException();
        return nouns.contains(word);
    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA == null || nounB == null)
            throw new NullPointerException();
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        Set<Integer> a = synMap.get(nounA);
        Set<Integer> b = synMap.get(nounB);
        int length = sap.length(a,b);
        return length;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null)
            throw new NullPointerException();
        if(!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        Set<Integer> a = synMap.get(nounA);
        Set<Integer> b = synMap.get(nounB);
        int ancestor = sap.ancestor(a,b);
        return synList.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args){
       // WordNet wordnet = new WordNet(args[0] , args[1]);
    }

}
