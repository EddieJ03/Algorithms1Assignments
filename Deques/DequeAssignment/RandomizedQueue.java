import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] array;

    //DON'T NEED THIS CODE FROM HERE
    private static Random random;
    private static long seed;  

    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }
    //TO ALL THE WAY HERE

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void changeArray(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }

        array = newArray;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == array.length) {
            changeArray(size * 2);
        }

        array[size++] = item;

    }

    // remove and return a RANDOM item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();

        //replace uniform with StdRandom.uniform() 
        int randomNum = uniform(size);

        Item item = array[randomNum];

        array[randomNum] = array[--size];
        array[size] = null;

        if (size > 0 && size == array.length / 4) {
            changeArray(array.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        //replace uniform with StdRandom.uniform() 
        int randomNum = uniform(size);

        return array[randomNum];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private int counter = 0;
        private Item[] arr;

        ListIterator() {
            
            arr = (Item[]) new Object[size];
            
            for (int i = 0; i < size; i++) {
                arr[i] = array[i];
            }
            
            shuffle(arr, 0, size);
            
        }

        public boolean hasNext() {
            return counter < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return arr[counter++];
        }

    }


    //DONT NEED THIS CODE FROM HERE TO
    public static void shuffle(Object[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static int uniform(int a, int b) {
      if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
          throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
      }
      return a + uniform(b - a);
    }

    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        return random.nextInt(n);
    }
    //ALL THE WAY HERE


    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        rq.enqueue(0);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);

        System.out.println("items: " + rq.size());

        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());

        //System.out.println(rq.toString());
        System.out.println("items: " + rq.size());

        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
        rq.enqueue(10);

        System.out.println("items: " + rq.size());

        for (Integer num1 : rq) {
          for (Integer num2 : rq) {
            System.out.println(num1 + "-" + num2);
          }
        }

        System.out.println("\n");

        for (Integer num : rq) {
           System.out.println(num);
        }

    }

}