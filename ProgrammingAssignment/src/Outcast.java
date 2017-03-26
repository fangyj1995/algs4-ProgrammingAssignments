import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by A on 2017/1/31.
 */
public class Outcast {
    private WordNet wordnet ;

    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }         // constructor takes a WordNet object
    public String outcast(String[] nouns){
        int [] distance = new int[nouns.length];
        for(int i = 0 ;i < nouns.length ; i++){
            for(int j = i + 1 ; j < nouns.length ;j++){
                 int dist = wordnet.distance(nouns[i] ,nouns[j]);
                 distance[i] += dist;
                 distance[j] += dist;
            }
        }
        int max = 0;int id = 0;
        for(int i = 0;i < distance.length ; i++){
            if(distance[i] > max){
                max = distance[i];
                id = i;
            }
        }
        return nouns[id];
    }   // given an array of WordNet nouns, return an outcast

    public static void main(String[] args){
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below

}
