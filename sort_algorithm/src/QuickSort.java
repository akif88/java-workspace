import java.util.Comparator;

public class QuickSort{
	
	//An efficient sorting algorithm.
	//Algorithm complexity is O(N^2) for worst case and O(N lgN) for average case.
	public static void sort(Object[] a, Comparator comparator){
		sort(a,comparator,0,a.length-1);
	}
	
	//sort the subarray
	private static void sort(Object[] a, Comparator c, int low, int high){
		if(high<=low) return;
		int j = partition(a, c, low, high);
		sort(a, c, low, j-1);
		sort(a, c, j+1, high);		
	}
	
	//partition the subarray
	private static int partition(Object[] a, Comparator c, int low, int high){
		int i = low, j = high+1;
		Object pivot = a[low];
		
		while(true){
			while(less(c,a[++i], pivot)) if(i == high) break; 
			while(less(c, pivot, a[--j])) if(j == low) break;
			if(i>=j) break;
			exchange(a, i, j);
		}
		
		exchange(a, low, j);
		return j;
	}
	
	private static boolean less(Comparator c, Object a, Object b){
		return c.compare(a, b) < 0;
	}
	
	private static void exchange(Object[] a, int i, int j){
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}	

}
