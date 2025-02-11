import java.util.ArrayList;
import java.util.Collection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class MyArrayListImpl{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("quit")){
            System.out.printf("Available options:%n"+
                "\"joke\" - show joke ArrayList implementation%n"+
                "\"real\" - show normal implementation, using my own methods%n"+
                "\"map\" - show hashmap's bucket structure%n"+
                "\"quit\" - quit the program%n");
            input = "";
            while (input.isEmpty()){
                input = br.readLine().toLowerCase();	
            }
            System.out.println("____________________________________");
            if (input.equals("joke")){
                showJokeList();
            }
        }
    }

    public static void showJokeList(){
        ArrayList<Integer> al = new ArrayList<>();
        al.add(1);
        MyArrayListJoke<Integer> list = new MyArrayListJoke<>(al);
        System.out.println("List, created from an existing list");
        System.out.println(list);
        list.add(123);
        System.out.println("List after adding a value to the end");
        System.out.println(list);
        System.out.println("Getting the first element from the list");
        System.out.println(list.get(0));
        list.add(0,666);
        System.out.println("List after inserting a value");
        System.out.println(list);
        list.set(1,-1);
        System.out.println("List, after changing a value at an index");
        System.out.println(list);
        list.remove(list.size()-1);
        System.out.println("List, after removing a value at an index");
        System.out.println(list);
        System.out.println("____________________________________");
    }
}

class MyArrayListReal<T>{
}


class MyArrayListJoke<T>{
    private ArrayList<T> list;

    public MyArrayListJoke(){
        this.list = new ArrayList<>();
    }

    public MyArrayListJoke(int initialCapacity){
        this.list = new ArrayList<>(initialCapacity);
    }

    public MyArrayListJoke(Collection<? extends T> source){
        this.list = new ArrayList<>(source);
    }

    public T get(int index){
        return list.get(index);
    }

    public T set(int index, T value){
        return list.set(index, value);
    }

    public boolean add(T element){
        return list.add(element);
    }

    public void add(int index, T element){
        list.add(index,element);
    }

    public T remove(int index){
        return list.remove(index);
    }

    public int size(){
        return list.size();
    }

    public String toString(){
        return list.toString();
    }
}
