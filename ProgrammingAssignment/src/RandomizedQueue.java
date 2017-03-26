import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item>{
	private int N;
	private Item[] a;
	   @SuppressWarnings("unchecked")
	public RandomizedQueue()   {
		   // construct an empty randomized queue
	    a=(Item[]) new Object[2];
	}
	public boolean isEmpty()          {
		   // is the queue empty?
		return N==0;
	}
	public int size()             {           // return the number of items on the queue
		   return N;//N是元素个数，length是数组大小
	}
	@SuppressWarnings("unchecked")
	private void resize(int newSize){
		Item[] temp=(Item[]) new Object[newSize];
		for(int i =0;i<N;i++){
			temp[i]=a[i];
		}
		a=temp;
	}
	public void enqueue(Item item)   {
		   // add the item
	  if(item==null) throw new NullPointerException();
	  if(N==a.length)
		  resize(a.length*2);
	  a[N++]=item;
	}
	public Item dequeue()            {        // remove and return a random item
	  if(isEmpty()) throw new NoSuchElementException();	
	  int index= (int)(Math.random()*N);
	  Item item=a[index];
	  for(int i=index+1;i<N;i++){
		  a[i-1]=a[i];
		  a[i]=null;
	  }
	  N--;
	  if(N==a.length/4&&N>0)
		  resize(a.length/2);
	  return item;
	}
	public Item sample()         {
		   // return (but do not remove) a random item
	  if(isEmpty()) throw new NoSuchElementException();	
		int index= (int)(Math.random()*N);
		Item item=a[index];
	  return item;	
	}
	public Iterator<Item> iterator()   {      // return an independent iterator over items in random order		
		return new ListIterator();
		
	}
	private class ListIterator implements Iterator<Item>{
		int current=0;
		Item[] temp;
		@SuppressWarnings("unchecked")
		public ListIterator(){
			temp=(Item[])new Object[N];
			for(int i=0;i<N;i++)
				temp[i]=a[i];
			StdRandom.shuffle(temp);	
		}
		@Override
		public boolean hasNext() {
			return current<N;
		}
		@Override
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException();		
			return temp[current++];
		}			
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	   }	
	public static void printAll(RandomizedQueue<String> rq){
		for(String s:rq){
			System.out.print(s+" ");
		}
		System.out.println(".");
	}
	public static void main(String[] args) {  // unit testing
		RandomizedQueue<String> rq=new RandomizedQueue<String>();
		rq.enqueue("we");
		rq.enqueue("IS");
		rq.enqueue("good");
		rq.enqueue("enough");
        printAll(rq);
        System.out.print(rq.sample()+" .");
        System.out.print(rq.sample()+" .");
        System.out.println();
		rq.dequeue();
		printAll(rq);
	    rq.dequeue();
	    printAll(rq);
		rq.dequeue();
		printAll(rq);
		rq.dequeue();
		printAll(rq);
	}
}
