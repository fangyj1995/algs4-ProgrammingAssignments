import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	private static int R = 256;
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode(){
    	String s = BinaryStdIn.readString();
    	CircularSuffixArray csa = new CircularSuffixArray(s);
    	int len = csa.length();
    	for(int i = 0 ;i < len ; i++){
    		if(csa.index(i) == 0){    			
    			BinaryStdOut.write(i);
    			break;
    		}
    	}
    	for(int i = 0 ;i < len ; i++){      		
    		BinaryStdOut.write(s.charAt((csa.index(i) +len- 1) % len));
    	}
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode(){//!!!!! time
    	int rank = BinaryStdIn.readInt();
    	String s = BinaryStdIn.readString();
    	char[] sorted = s.toCharArray();   			
    	Arrays.sort(sorted);    	
    	int[] next = computeNext(rank , s);    	
    	StringBuilder decodeBuilder = new StringBuilder();
    	   	
    	for(int i = 0 ;i < s.length() ; i++){    		
    		char nextChar = sorted[rank];
    		decodeBuilder.append(nextChar);
    		rank = next[rank];
    	}
  	
    	BinaryStdOut.write(decodeBuilder.toString());
    	BinaryStdOut.close();
    }

    private static int[] computeNext(int first, String s) {
    	int[] count = new int[R + 1];
    	int[] next = new int[s.length()];
    	
		for(int i = 0; i < s.length() ; i++)	
			count[s.charAt(i)+1]++;

		for(int r = 0 ;r < R; r++ )
			count[r + 1] += count[r];
						
		for(int i = 0;i < s.length(); i++)
			next[count[s.charAt(i)]++] = i;
		
		return next;
	}

	// if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args){
    	if(args[0] .equals( "-"))
    		BurrowsWheeler.encode();
    	if(args[0] .equals("+"))
    		BurrowsWheeler.decode();
    }

}
