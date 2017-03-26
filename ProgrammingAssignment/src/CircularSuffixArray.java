import java.util.Arrays;

public class CircularSuffixArray {
	private final char[] str;
    private final int[] index;
    private int len;
    public CircularSuffixArray(String s) { // circular suffix array of s
    	if(s == null) throw new NullPointerException();
    	str = s.toCharArray();
    	len = s.length();
    	index = new int[len];
    	for(int i = 0; i < len ; i++){
    		index[i] = i;
    	}
    	sort(0 , len - 1, 0);
    }
    private void sort(int lo , int hi ,int d){
    	if(lo >= hi || d > len - 1) return;
    	int lt = lo, gt = hi;
    	char v = str[(index[lo] + d)%len];//
    	int i = lo + 1;
    	while(i <= gt){
    		char t = str[(index[i] + d)%len];
    		if(t > v) exch(i,gt--);    	
    		else if(t < v) exch(i++,lt++);    		
    		else i++;
    	}
    	sort(lo , lt - 1 , d);
    	sort(lt , gt , d + 1);
    	sort(gt + 1 , hi , d);
    }
    private void exch(int i ,int j){//exch index[]
    	int temp  = index[i];
    	index[i] = index[j];
    	index[j] = temp;
    }
    public int length()  {                 // length of s
    	return len;
    }
    public int index(int i) {              // returns index of ith sorted suffix
    	if(i < 0 ||i > len - 1) throw new IndexOutOfBoundsException();
    	return index[i];
    }
    public static void main(String[] args){// unit testing of the methods (optional)
    	CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
    	System.out.println(Arrays.toString(csa.index));
    }

}
