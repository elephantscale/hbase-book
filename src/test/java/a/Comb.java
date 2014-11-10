package a;

import java.util.ArrayList;
import java.util.List;

import com.kenai.jffi.Array;

public class Comb {
	
	public static void main(String[] args) {
		// string --> string array
		String input = "1112234";
	}
	
	public static int [][] getComb(int [] a, int start, int width)
	{
		List<int[]> arrays = new ArrayList<>();
		// terminal condition
		if (width == 1)
		{
			for (int i=0; i < a.length; i++)
			{
				arrays.add (new int [] {a[i]});
			}
				
			return arrays.toArray(); // todo pass the int array
		}
		
		int [] myArray = Array.extract (width);
		
		int [][] otherCombinations = getComp(a,  a.length - width, width);
		
		int [][] finalComp = myArray + otherCombinations;
		
		return finalComp;
		
	}

}
