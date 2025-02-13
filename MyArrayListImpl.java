import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class MyArrayListImpl{
    public static void main(String[] args) throws IOException {
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
            while (input.isEmpty()){
                input = br.readLine().toLowerCase();	
            }
            if (input.equals("joke")){
                showJokeList();
            }
            if (input.equals("real")){
                showRealList();
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

    public static void showRealList() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
		int initialCapacity = 0;
		try {
			initialCapacity = setCapacityLoop(br,input);
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
					System.out.println(al.showInternals());
					System.out.println("____________________________________");
				}
			}
		} catch (WrongInputException e){}
        
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
        return br.readLine();
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
				throw new WrongInputException(input);
            }
            if (input.isEmpty()){
                initialCapacity = 0;
            } else {
				try {
					inputValue = checkPositiveInteger(input);
					initialCapacity = inputValue;
				} catch (WrongInputException e){
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
					val = checkNumericValue(input);
					al.add(val);
				} catch (WrongInputException e){
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
					index = checkPositiveInteger(input);
					if (index!=0&&al.size()!=0){
						al.checkBounds(index);
					}
				} catch (WrongInputException | IndexOutOfBoundsException e){
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
							value = checkNumericValue(evenAnotherInput);
						} catch (WrongInputException e){
							System.out.println(e);
							continue;
						}
						try {
							al.add(index,value);
						} catch (IndexOutOfBoundsException e){}
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
					int index = checkPositiveInteger(input);
					System.out.println("____________________________________");
					System.out.println("ELEMENT => "+al.get(index));
					System.out.println("____________________________________");
				} catch (WrongInputException | IndexOutOfBoundsException e){
					System.out.println(e.getMessage());
					continue;
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
					int index = checkPositiveInteger(input);
					al.remove(index);
				} catch (WrongInputException | IndexOutOfBoundsException e){
					System.out.println(e.getMessage());
					continue;
				}
            }
        }
    }

	public static int checkPositiveInteger(String input) throws WrongInputException{
		int inputValue = checkNumericValue(input);
		if (inputValue<0){
			System.out.println();
			System.out.println("Wrong input\nIt should be a positive integer");
			throw new WrongInputException(input);
		}
		return inputValue;
	}

	public static int checkNumericValue(String input) throws WrongInputException{
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e){
			System.out.println();
			System.out.println("Wrong input\nIt should be a numeric value");
			throw new WrongInputException(input);
		} 
	}
}

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
        capacity*=2;
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
		if (index!=0&&size!=0){
			checkBounds(index);
			System.arraycopy(elements,index,elements,index+1,size-index);
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
        System.arraycopy(elements,index+1,elements,index,size-index-1);
        size--;
        elements[size] = null;
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
