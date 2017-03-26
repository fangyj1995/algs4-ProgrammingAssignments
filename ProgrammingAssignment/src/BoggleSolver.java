import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by fangyj on 2017/2/7.
 */
class TrieSet{
    private Node root;
    static class Node{
        Node[] next = new Node[26];
        boolean isWord;
    }
    public TrieSet(){
        root = new Node();
    }
    public Node getRoot(){
        return root;
    }
    public void add(String word) {
        Node cur = root;
        for(int i = 0; i < word.length();i++ ){
            int index = word.charAt(i)-'A';
            if(cur.next[index] == null)
                cur.next[index] = new Node();
            cur = cur.next[index];
        }
        cur.isWord = true;
    }
    public boolean contains(String word){
        Node cur = root;
        for(int i = 0; i < word.length();i++ ){
            cur = cur.next[word.charAt(i)-'A'];
            if(cur == null) return false;
        }
        return cur.isWord;
    }
    public boolean hasPrefixOf(String word){
        Node cur = root;
        for(int i = 0; i < word.length();i++ ){
            cur = cur.next[word.charAt(i)-'A'];
            if(cur == null) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TrieSet trie = new TrieSet();
        trie.add("NIHAO");
        trie.add("NI");
        trie.add("BUYAO");
        trie.add("B");
        System.out.print(trie.contains("NIHAO"));
        System.out.print(trie.contains("NI"));
        System.out.print(trie.hasPrefixOf("NIHA"));
        System.out.print(trie.hasPrefixOf("BU"));
        System.out.print(trie.hasPrefixOf("B"));
        System.out.print(trie.hasPrefixOf("NIHAOT"));
        System.out.print(trie.contains("BU"));
    }
}

public class BoggleSolver {

    private final TrieSet dict = new TrieSet();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        for(String word : dictionary)
            dict.add(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        Set<String> words = new HashSet<>();
        for(int i = 0;i < board.rows() ; i++){
            for(int j = 0; j < board.cols() ; j++){
                boolean[][] visited = new boolean[board.rows()][board.cols()];
                StringBuilder sb = new StringBuilder();
                dfs(board,i,j,words,visited,sb,dict.getRoot());
            }
        }
        return words;
    }
    private void dfs(BoggleBoard board,int i,int j,Set<String> words,boolean[][] visited,StringBuilder sb,TrieSet.Node cur){
        if(i<0 || i > board.rows() - 1 || j < 0 ||j > board.cols() - 1 || visited[i][j]) return;
        char c = board.getLetter(i,j);
        cur = cur.next[c-'A'];
        if(cur == null) return;
        if(c == 'Q') {
            sb.append("QU");
            cur = cur.next['U'-'A'];
            if(cur == null) return;
        }
        else sb.append(c);
        System.out.println(c);
        System.out.println(sb);
        if(cur.isWord&&sb.length()>2) words.add(sb.toString());

        visited[i][j] = true;
        dfs(board, i - 1, j, words,visited,sb,cur);//top
        dfs(board, i + 1, j, words,visited,sb,cur);//bottom
        dfs(board, i, j - 1 , words,visited,sb,cur);//left
        dfs(board, i, j + 1, words,visited,sb,cur);//right
        dfs(board, i - 1, j - 1, words,visited,sb,cur);//left top!!
        dfs(board, i - 1, j + 1, words,visited,sb,cur);//right top
        dfs(board, i + 1, j - 1, words,visited,sb,cur);//left bottom
        dfs(board, i + 1, j + 1, words,visited,sb,cur);//right bottom
        visited[i][j] = false;

        if(c == 'Q')
            sb.delete(sb.length() - 2,sb.length());
        else
            sb.deleteCharAt(sb.length() - 1);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if(!dict.contains(word)) return  0;
        int len = word.length();
        if(len <= 2) return 0;
        if(len <= 4) return 1;
        if(len == 5) return 2;
        if(len == 6) return 3;
        if(len == 7) return 5;
        else return 11;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word + " "+solver.scoreOf(word));
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
