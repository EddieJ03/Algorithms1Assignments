/* *****************************************************************************
 *  Name: Edward Jin
 *  Date: 6/21/2021
 *  Description: Deque Implementation Using Doubly Linked-List
 **************************************************************************** */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int counter;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        counter = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return counter == 0;
    }

    // return the number of items on the deque
    public int size() {
        return counter;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null)
            throw new IllegalArgumentException();

        counter++;

        if (counter == 1) {
            first = new Node(item);
            first.setItem(item);
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node(item);
            first.setNext(oldFirst);
            first.setPrev(null);
            oldFirst.setPrev(first);
        }

    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null)
            throw new IllegalArgumentException();

        counter++;

        if (counter == 1) {
            last = new Node(item);
            last.setItem(item);
            first = last;
        }
        else {
            Node oldLast = last;
            last = new Node(item);
            last.setNext(null);
            last.setPrev(oldLast);
            oldLast.setNext(last);
        }

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = first.getItem();
        first.setItem(null);
        first = first.getNext();
        counter--;
        if (counter == 0) last = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = last.getItem();
        last.setItem(null);
        last = last.getPrev();
        last.setNext(null);
        counter--;

        if (counter == 0) first = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item item = current.getItem();
            current = current.getNext();
            return item;
        }

    }

    public static void main(String[] args) {

        Deque<Integer> deq2 = new Deque<Integer>();

        System.out.println("size: " + deq2.size());

        deq2.addFirst(1);
        deq2.addFirst(2);
        deq2.addFirst(3);
        deq2.addFirst(4);
        deq2.addFirst(5);

        System.out.println("size: " + deq2.size());


        Iterator<Integer> itr = deq2.iterator();

        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next() + "\n");

        System.out.println(deq2.removeLast());


        System.out.println(deq2.removeFirst());
        System.out.println(deq2.removeFirst());

        System.out.println("size: " + deq2.size());

        System.out.println(deq2.removeLast());
        System.out.println(deq2.removeLast());


        deq2.addFirst(1);
        deq2.addLast(2);

        System.out.println(deq2.removeLast());
        System.out.println(deq2.removeLast());


        deq2.addFirst(3);
        deq2.addLast(4);


        System.out.println("size: " + deq2.size());

        itr = deq2.iterator();

        System.out.println(itr.next());
        System.out.println(itr.next());

        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        Iterator<Integer> itr2 = deque.iterator();  
        System.out.println(itr2.next());
        deque.addLast(2);
        itr2 = deque.iterator();  
        System.out.println(itr2.next()); 
        System.out.println(itr2.next()); 
        deque.addFirst(3);
        itr2 = deque.iterator();  
        System.out.println(itr2.next()); 
        System.out.println(itr2.next()); 
        System.out.println(itr2.next()); 
        deque.removeLast();   
        itr2 = deque.iterator();  
        System.out.println(itr2.next()); 
        System.out.println(itr2.next()); 

    }

}
