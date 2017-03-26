

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
	private class Node{
		Item item;
		Node next;
		Node prev;
	}
	private Node first;
	private Node last;
	private int N;
	public Deque()                           // construct an empty deque
	{
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return N==0;
	}
	public int size()                        // return the number of items on the deque
	{
		return N;
	}
	public void addFirst(Item item)          // add the item to the front
	{
		if(item==null) throw new NullPointerException();
		Node oldFirst=first;
		first=new Node();
		first.item=item;
		first.next=oldFirst;
		first.prev=null;
		if(isEmpty()) last=first;
		else oldFirst.prev=first;
		N++;
	}
	public void addLast(Item item)           // add the item to the end
	{
		if(item==null) throw new NullPointerException();
		Node oldLast=last;
		last=new Node();
		last.item=item;
		last.next=null;
		last.prev=oldLast;
		if(isEmpty()) first=last;
		else oldLast.next=last;
		N++;
	}
	public Item removeFirst()                // remove and return the item from the front
	{
		if(this.isEmpty()) throw new UnsupportedOperationException();
		Item item=first.item;		
		first=first.next;	
		N--;	
		if(isEmpty()) last=null;
		else first.prev=null;
		return item;
	}
	public Item removeLast()                 // remove and return the item from the end
	{
		if(this.isEmpty()) throw new UnsupportedOperationException();
		Item item=last.item;
		Node prevLast=last.prev;
		last=prevLast;
		N--;
		if(isEmpty()) first=null;
		else last.next=null;		
		return item;
	}
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new Iterator<Item>(){
            private Node current=first;
			@Override
			public boolean hasNext() {
				return current!=null;
			}

			@Override
			public Item next() {
				if(!hasNext()) throw new NoSuchElementException();
				Item item=current.item;
				current=current.next;
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	public static void printAll(Deque<String> dq){
		for(String s:dq){
			System.out.print(s+" ");
		}
		System.out.println();
	}
	public static void main(String[] args)   // unit testing
	{
		Deque<String> dq=new Deque<String>();
		dq.addFirst("we");
		dq.addFirst("IS");
		dq.addLast("good");
		dq.addLast("enough");
        printAll(dq);
		dq.removeLast();
		printAll(dq);
		dq.removeLast();
		printAll(dq);
		dq.removeLast();
		printAll(dq);
		dq.removeLast();
		printAll(dq);
	}
}
