import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//The file name and the number of results.
		String fileName =  args[0]; 
		int k = Integer.parseInt(args[1]);		
		Scanner in = new Scanner(System.in); //console input for query
		//Loop from console to zero("0") input
		while(true){			
			System.out.print("Please Enter Your Query: ");
			String query = in.nextLine();	//read console query input
			if(query.equals("0")){ // if console input is zero("0") exit the program
				System.out.println("Program is Closed");
				System.exit(0);
			}
				
			//read text file
			ReadFile file = new ReadFile(fileName);
			//get row size first row from text 
			int array_size = Integer.parseInt(file.readLine().trim());

			//create file array until array_size 
			TextFile[] text = new TextFile[array_size];
			
			int size = 0;//  read each row data from file
			while (file.hasNextLine())
				text[size++] = new TextFile(file.readLine().trim()); 
			
			//	sort by word with quicksort algorithm		
			QuickSort sort = new QuickSort();		
			sort.sort(text, new TextFile.wordsOrder());
			
			//read the query from console input 			
			TextFile tQ= new TextFile();
			try{	//if the query is null or does not exist, then enter nullpointer exception				
				tQ.setWord(query);	           }
			catch (NullPointerException e)     {
				System.out.println(e);
				continue;                      }
			
			
			//find the entered query with BinarySearch Algorithm
			int index = BinarySearch.find(text, new TextFile.wordsOrder(), tQ);
			
			//If you can not find it because of the character problem(ş,ğ,ü etc)
			if(array_size == index) continue;
			
			//If the query word exists, binary Search finds the first data in the Array. 
			//Find all Linear search to find all other relevant queries.
			int startindex = index;  
			//Linear Search
			while(text[index].getWord().toLowerCase().startsWith(tQ.getWord().toLowerCase())==true)
				index++;
			
			//array resize by find all data for weight sort 
			text = dataMinimize(text, startindex, index);
			                	  
			
			int hi = text.length-1, lo = text.length-k; //to sort in descending order. 
			
			//If k is greater than the number of queries found, list the number of queries found.
			if(text.length < k) lo=0;
			
			// Sort by weight with QuickSort Algorithm
			sort.sort(text, new TextFile.weightOrder());
			for(int i = hi; i>=lo; i--) // write result to the console
				System.out.println(text[i].getWeight() + " " + text[i].getWord());
		
		}//end while(true)
		
	}//end main
	
	
	//resize array according to found data
	private static TextFile[] dataMinimize(TextFile[] text, int lo, int hi){
			
		TextFile[] textData = new TextFile[hi-lo];
			
		int a = 0;
			
		for(int i = lo; i < hi; i++)
			textData[a++] = text[i];
			
		return textData;	
	}
	
}
