import java.util.Comparator;

public class BinarySearch{
	
	//An efficient algorithm that searches the ordered list for the desired data.
	//Algorithm complexity is O(lgN).
	public static int find(Object[] data, Comparator comparator, Object key){
		
		int lo = 0, hi = data.length-1; 
        while (lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            int cmp = comparator.compare(key, data[mid]);
            if      (cmp < 0) hi = mid - 1; 
            else if (cmp > 0) lo = mid + 1; 
            else return mid; 
        } 	
		
		return lo;		
	}
	
	
	

}
