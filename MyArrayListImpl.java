import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/*
	Interactive console app.
	"joke" - shows my joke ArrayList implementation, 
		as it is just a wrapper for a built-in ArrayList and uses it's functions.
	"real" - allows you to manualy use my real ArrayList's functions:
		you can set it's capacity, add elements to the end, insert elements,
		change existing elements and remove existing elements.
		Also, you can look at the elements count and capacity of the list,
		as well, as look at it's internal array, which holds them.
		This console app operates only with Integer values,
			because i would have to implement many types of behaviour.
		So i settled on an Integer.
	"map" - shows the bin type of the HashMap with different elements count.
		I don't know, what depends on the untreeify trigger,
			because it reliably converts a bin into a tree at 9 elements,
			but it doesn't convert it back exactly after we cross the 6 elements threshold.
 */
public class MyArrayListImpl{
    public static void main(String[] args) throws IOException {
		/*
		 * Uncomment to test a different datatype with my implementation
			MyArrayListReal<String> al = new MyArrayListReal<>();
			al.add("123");
			al.add("qwe");
			al.add("$#");
			System.out.println(al);
			al.remove(1);
			System.out.println(al);
			al.add(0,"hhh");
			System.out.println(al);
		*/

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("quit")){
            System.out.println("____________________________________");
            System.out.printf("Available options:%n"+
                "\"joke\" - show joke ArrayList implementation%n"+
                "\"real\" - show normal implementation, using my own methods%n"+
                "\"map\" - show hashmap's bucket structure%n"+
                "\"quit\" - quit the program%n");
            System.out.println("____________________________________");
            input = "";
            while (input.isEmpty()){ //if input is empty - don't do anything and just reread the input
                input = br.readLine().toLowerCase();	
            }
            if (input.equals("joke")){
                showJokeList();
            }
            if (input.equals("real")){
                showRealList();
            }
			if (input.equals("map")){
				showMapStructure();
			}
        }
    }

	//function to show my joke implementation
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

	//function to start with the real implementation
    public static void showRealList() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
		int initialCapacity = 0;
		//using try-catch here to let the user go back to the main menu,
		//if "b/B" is the input.
		try {
			initialCapacity = setCapacityLoop(br,input); //initial list's capacity, 10 by default
			MyArrayListReal<Integer> al = new MyArrayListReal<Integer>(initialCapacity);
			while (!input.equals("0")){
				input = displayMenu(br);
				if (input.equals("1")){
					addElementLoop(al,br); 
				}
				if (input.equals("2")){
					insertElementLoop(al,br); 
				}
				
				if (input.equals("3")){
					getElementLoop(al,br);
				}

				if (input.equals("4")){
					setElementLoop(al,br);  
				}
				
				if (input.equals("5")){
					removeElementLoop(al,br);
				}
				
				if (input.equals("6")){
					System.out.println("____________________________________");
					System.out.println();
					System.out.println("SIZE(ELEMENT COUNT) => "+al.size());
					System.out.println("____________________________________");
				}

				if (input.equals("7")){
					System.out.println("____________________________________");
					System.out.println();
					System.out.println("LIST CAPACITY => "+al.capacity());
					System.out.println("____________________________________");
				}

				if (input.equals("8")){
					System.out.println("____________________________________");
					System.out.println();
					System.out.println(al);
					System.out.println("____________________________________");
				}

				if (input.equals("9")){
					System.out.println("____________________________________");
					System.out.println();
					System.out.println(al.showInternals()); //show the internal array
					System.out.println("____________________________________");
				}
			}
			//if input is "b"/"B" - the error will be caught
			//which allows us to leave this method and go back to the main menu loop
		} catch (WrongInputException e){} 
        
    }

	//method to show hashmap's bin structure
	public static void showMapStructure(){
		Map<SameHash, Integer> map = new HashMap<>(64);
        LinkedHashSet<String> classes;
        SameHash sh1 = new SameHash(1);
        SameHash sh2 = new SameHash(1);
        SameHash sh3 = new SameHash(1);
        SameHash sh4 = new SameHash(1);
        SameHash sh5 = new SameHash(1);
        SameHash sh6 = new SameHash(1);
        SameHash sh7 = new SameHash(1);
        SameHash sh8 = new SameHash(1);
        SameHash sh9 = new SameHash(1);
        map.put(sh1,1);
        map.put(sh2,2);
        map.put(sh3,3);
        map.put(sh4,4);
        map.put(sh5,5);
        map.put(sh6,6);
        map.put(sh7,7);
        map.put(sh8,8);
        classes = new LinkedHashSet<>();
        for (Map.Entry entry : map.entrySet()) {
            classes.add(entry.getClass().getSimpleName());
        }
		System.out.println("____________________________________");
		System.out.println("\nInserting 8 elements...");
        System.out.println("Elements count: " + map.entrySet().size()
                + "; Bin type: " + classes.getFirst());
        map.put(sh9,9);
        classes = new LinkedHashSet<>();
        for (Map.Entry entry : map.entrySet()) {
            classes.add(entry.getClass().getSimpleName());
        }
		System.out.println("\nInserting a 9th element to trigger treeify...");
        System.out.println("Elements count: " + map.entrySet().size()
                + "; Bin type: " + classes.getFirst());
        map.remove(sh9);
        map.remove(sh8);
        map.remove(sh7);
        map.remove(sh6);
		//should untreeify here, as we delete 6th element and cross the threshold
		//but it doesn't
		//moreover, it depends on the environment
		//because if I do that in IDEA - it untreeifies after the 5th element
		//using console it does that after the 4th for me
        map.remove(sh5);
        map.remove(sh4);
        classes = new LinkedHashSet<>();
        for (Map.Entry entry : map.entrySet()) {
            classes.add(entry.getClass().getSimpleName());
        }
		System.out.println("\nDeleting some elements to trigger untreeify...");
        System.out.println("Elements count: " + map.entrySet().size()
                + "; Bin type: " + classes.getFirst());
		System.out.println("____________________________________");
	}

    public static String displayMenu(BufferedReader br) throws IOException {
        System.out.println("____________________________________");
        System.out.printf("0 - Back to main menu%n"+
                "1 - Add an element%n"+
                "2 - Insert an element%n"+
                "3 - Get an element from the list%n"+
                "4 - Set an element to a value%n"+
                "5 - Remove an element from the list%n"+
                "6 - Show elements count%n"+
                "7 - Show list capacity%n"+
                "8 - Show all elements of the list%n"+
                "9 - Show the internal structure of the list%n");
        System.out.println("____________________________________");
        return br.readLine(); //return the input
    }

    public static int setCapacityLoop(BufferedReader br, String input) throws IOException,WrongInputException {
        int initialCapacity = -1;
		int inputValue;
        while (initialCapacity<0){
            System.out.println("____________________________________");
            System.out.println("Set initial list capacity");
            System.out.println("Capacity - how many elements the list will be able to contain before growing");
            System.out.println("Note, that it should be a positive integer");
            System.out.println("0 or empty input will produce default capacity of 10 elements");
            System.out.println("b - Back to main menu");
            System.out.println("____________________________________");
            input = br.readLine();
            if (input.toLowerCase().equals("b")){
				//throw this, to get back to the main menu
				throw new WrongInputException(input); 
            }
            if (input.isEmpty()){ //empty input - set default capacity of 10
                initialCapacity = 0;
            } else {
				try {
					//check if the input is a positive Integer
					inputValue = checkPositiveInteger(input);
					initialCapacity = inputValue;
				} catch (WrongInputException e){ //if caught - print the message and try again
					System.out.println(e);
					continue;
				}
            }
        }
        if (initialCapacity==0){
            return 10; 
        } else {
            return initialCapacity; 
        }
    }

    public static void addElementLoop(MyArrayListReal<Integer> al, BufferedReader br) throws IOException {
        String input = "";
		int val = 0;
        while (!input.equals("b")){
            System.out.println("____________________________________");
            System.out.printf("Enter a number to add at the end of the list%n"+
                    "It should be a 32-bit integer%n"+
                    "(-2,147,483,648 to 2,147,483,647)%n"+
                    "b - Go back%n");
            System.out.println("____________________________________");
            input = br.readLine();   		
            if (!input.equals("b")){
				try {
					//check if the input is an Integer
					val = checkNumericValue(input);
					al.add(val);
				} catch (WrongInputException e){
					//if caught - print the message and try again
					System.out.println(e);
				}
            }
        }
    }

    public static void insertElementLoop(MyArrayListReal<Integer> al, BufferedReader br) throws IOException {
        String input = "";
		int index = 0;
		int value = 0;
        while (!input.equals("b")){
            System.out.println("____________________________________");
            System.out.printf("Enter an index, which is needed to insert to%n"+
                    "It should be a 32-bit integer%n"+
                    "(0 to 2,147,483,647)%n"+
                    "b - Go back%n");
            System.out.println("____________________________________");
            input = br.readLine(); 
            if (!input.equals("b")){
				try {
					//check if the input is a positive Integer
					index = checkPositiveInteger(input);
					//this allows us to skip the bounds check and insert at 0, if the size is also 0
					if (index!=0||al.size()!=0){
						al.checkBounds(index); //check if the index is in the list range
					}
				} catch (WrongInputException | IndexOutOfBoundsException e){
					//if wrong input or index not in range - try again
					System.out.println(e.getMessage());
					continue;
				}
                String evenAnotherInput = "";
                while (!evenAnotherInput.equals("b")){
                    System.out.println("____________________________________");
                    System.out.printf("Enter a number to insert at the specified index of the list%n"+
                            "It should be a 32-bit integer%n"+
                            "(-2,147,483,648 to 2,147,483,647)%n"+
                            "b - Go back%n");
                    System.out.println("____________________________________");
                    evenAnotherInput = br.readLine();
                    if (!evenAnotherInput.equals("b")){
						try {
							//check if the input is an Integer
							value = checkNumericValue(evenAnotherInput);
						} catch (WrongInputException e){
							System.out.println(e);
							continue;
						}
						try {
							al.add(index,value); 
						} catch (IndexOutOfBoundsException e){}
						//the error never will be thrown, because we've already checked the bounds
						//it's just so you get the error at the index input stage, not here
                        break;
                    }
                }
            }
        }
    }

    public static void getElementLoop(MyArrayListReal<Integer> al, BufferedReader br) throws IOException {
        String input = "";
        while (!input.equals("b")){
            System.out.println("____________________________________");
            System.out.printf("Enter an index, where to get the element from%n"+
                    "It should be a positive integer%n"+
                    "(0 to 2,147,483,647)%n"+
                    "b - Go back%n");
            System.out.println("____________________________________");
            input = br.readLine(); 
            if (!input.equals("b")){
				try {
					//check if the input is a positive Integer
					int index = checkPositiveInteger(input);
					System.out.println("____________________________________");
					System.out.println("ELEMENT => "+al.get(index));
					System.out.println("____________________________________");
				} catch (WrongInputException | IndexOutOfBoundsException e){
					//if caught an error - try again
					System.out.println(e.getMessage());
				}
            }
        }
    } 

    public static void setElementLoop(MyArrayListReal<Integer> al, BufferedReader br) throws IOException {
        String input = "";
		int index = 0;
		int value = 0;
        while (!input.equals("b")){
            System.out.println("____________________________________");
            System.out.printf("Enter an index, where to set the element%n"+
                    "It should be a positive integer%n"+
                    "(0 to 2,147,483,647)%n"+
                    "b - Go back%n");
            System.out.println("____________________________________");
            input = br.readLine(); 
            if (!input.equals("b")){
				try {
					//check if the input is a positive Integer and inside the list range
					index = checkPositiveInteger(input);
					al.checkBounds(index);
				} catch (WrongInputException | IndexOutOfBoundsException e){
					System.out.println(e.getMessage());
					continue;
				}
                String evenAnotherInput = "";
                while (!evenAnotherInput.equals("b")){
                    System.out.println("____________________________________");
                    System.out.printf("Enter a number to replace at the specified index of the list%n"+
                            "It should be a 32-bit integer%n"+
                            "(-2,147,483,648 to 2,147,483,647)%n"+
                            "b - Go back%n");
                    System.out.println("____________________________________");
                    evenAnotherInput = br.readLine();
                    if (!evenAnotherInput.equals("b")){
						try {
							value = checkNumericValue(evenAnotherInput);
						} catch (WrongInputException e){
							System.out.println(e.getMessage());
							continue;
						}
						try {
							//no error here, we just throw the index error earlier
							al.set(index,value);
						} catch (IndexOutOfBoundsException e){}
                        break;
                    }
                }
            }
        }
    }

    public static void removeElementLoop(MyArrayListReal<Integer>  al, BufferedReader br) throws IOException {
        String input = "";
        while (!input.equals("b")){
            System.out.println("____________________________________");
            System.out.printf("Enter an index, where to remove the element from%n"+
                    "It should be a positive integer%n"+
                    "(0 to 2,147,483,647)%n"+
                    "b - Go back%n");
            System.out.println("____________________________________");
            input = br.readLine(); 
            if (!input.equals("b")){
				try {
					//check if the input is a positive Integer
					int index = checkPositiveInteger(input);
					al.remove(index);
				} catch (WrongInputException | IndexOutOfBoundsException e){
					//if index out of range or wrong input - try again
					System.out.println(e.getMessage());
				}
            }
        }
    }

	public static int checkPositiveInteger(String input) throws WrongInputException{
		int inputValue = checkNumericValue(input); //check, if the input is an Integer value
		if (inputValue<0){
			System.out.println();
			System.out.println("Wrong input\nIt should be a positive integer");
			throw new WrongInputException(input);
		}
		return inputValue; //return the value if ok
	}

	public static int checkNumericValue(String input) throws WrongInputException{
		try {
			return Integer.parseInt(input); //parse the input here and return as int value
		} catch (NumberFormatException e){
			System.out.println();
			System.out.println("Wrong input\nIt should be a numeric value");
			throw new WrongInputException(input);
		} 
	}
}

//My real ArrayList implementation.
//Should work fine with other datatypes
class MyArrayListReal<T>{
    private Object[] elements;
    
    private int size = 0;

    private int capacity;

    public MyArrayListReal(){
        capacity = 10;
        elements = new Object[capacity]; 
    }

    public MyArrayListReal(int initialCapacity){
        capacity = initialCapacity;
        elements = new Object[capacity];
    }

    private Object[] grow(){
        capacity*=2; //twice the capacity with each grow. Not optimal, but simple
        return elements = Arrays.copyOf(elements,capacity);
    }

    public void add(T element){
        if (size==capacity){
            elements = grow();
        }
        elements[size] = element;
        size++;
    }

    public void add(int index, T element) throws IndexOutOfBoundsException {
        if (size==capacity){
            elements = grow();
        }
		if (index!=0||size!=0){ //allows us to skip the bounds check if inserting at 0 with 0 size
			checkBounds(index);
			System.arraycopy(elements,index,elements,index+1,size-index); //shift the elements to the right to free the index
		}
        elements[index] = element;
        size++;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        checkBounds(index); 
        @SuppressWarnings("unchecked")
        T val = (T)elements[index];
        return val;
    }

    public T set(int index, T value) throws IndexOutOfBoundsException {
        checkBounds(index); 
        @SuppressWarnings("unchecked")
        T oldValue = (T)elements[index];
        elements[index] = value;
        return oldValue;
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        checkBounds(index);
        System.arraycopy(elements,index+1,elements,index,size-index-1); //shift elements to the left
        size--;
        elements[size] = null; //delete the last element, as it is a copy of the last element after shifting
    }

    public int size(){
        return size;
    }

    public int capacity(){
        return capacity;
    }

    public void checkBounds(int index) throws IndexOutOfBoundsException {
        if (index<0||index>=size){
			System.out.println("Wrong input");
            throw new IndexOutOfBoundsException(index);
        }
    }

	//just putting pipes instead of square brackets and commas
    public String toString(){
        int index = 0;
        
        if (size==0){
            return "| |";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("|"); 
        while (index<size){
            @SuppressWarnings("unchecked") 
            T element = (T)elements[index];
            sb.append(element);
            sb.append("|");
            index++;
        }
        return sb.toString();
    }

    public String showInternals(){
        return Arrays.toString(elements);
    }
}


//joke ArrayList implementation.
//just wraps the ArrayList and uses it's methods
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

//custom Exception class for the wrong user input
class WrongInputException extends Exception{
    String message;

    public WrongInputException(String input){
        message = "Input: "+input;
    }

    public String getMessage(){
        return message;
    }
    public String toString(){
        return message;
    }
}

//class for the HashMap show.
//Forces to be in the [1] bin.
class SameHash{
    int val = 1;

    public SameHash(int val){
        this.val = val;
    }

    @Override
    public int hashCode(){
        return 1;
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

}
