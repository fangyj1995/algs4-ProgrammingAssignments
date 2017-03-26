import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	private static int R = 256;
	private static char[] ascii = new char[256];
	// apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode(){
    	for(int i = 0 ; i < R ;i++){
			ascii[i] = (char)i;
		}
    	while(!BinaryStdIn.isEmpty()){  	
    		char c = BinaryStdIn.readChar();    		
    		int pos = positionOfChar(c);
    		BinaryStdOut.write(pos, 8);
    	}
    	BinaryStdOut.close();
    }
    private static int positionOfChar(char c){
    	int pos = 0;
    	for(int i = 0; i < R ; i++){    		
    		if(c == ascii[i]){
    			pos = i;
    			break;
    		}
    	}
    	
    	//move to front;
    	for(int i = pos ;i > 0 ; i--){
    		ascii[i] = ascii[i - 1];
    	}
    	ascii[0] = c;   	
    	return pos;
    }
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
    	for(int i = 0 ; i < R ;i++){
			ascii[i] = (char)i;
		}
    	while(!BinaryStdIn.isEmpty()){ 
    		int pos = BinaryStdIn.readInt(8);
    		char c = ascii[pos];
    		BinaryStdOut.write(c);
    		
    		//move to front
        	for(int i = pos ;i > 0 ; i--){
        		ascii[i] = ascii[i - 1];
        	}
        	ascii[0] = c;
    	}
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args){
    	if(args[0] .equals( "-"))
    		MoveToFront.encode();
    	if(args[0] .equals("+"))
    		MoveToFront.decode();
    }

}
