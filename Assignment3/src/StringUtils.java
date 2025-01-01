import java.util.Arrays;

public class StringUtils {

	public static String findSortedSequence(String str) {
		
		String[] splittedStr = str.split(" ");
		String[] maxSortedSequence = new String[splittedStr.length];
		
		
		int i = 0, max = 0;
		while(i < splittedStr.length)
		{
			int count = 0, j = i;
			String[] sortedSequence = new String[splittedStr.length];
			while(j < (splittedStr.length - 1))
			{
				//the string are in lexicographic order, so we need to keep going through the substrings.
				if(splittedStr[j].compareTo(splittedStr[j+1]) <= 0)
				{
					sortedSequence[count] = splittedStr[j];
					count++;
					j++;
				}
				
				
				else
				{
					sortedSequence[count] = splittedStr[j];
					count++;
					j = splittedStr.length + 1;
				}
			}
			
			if(j == (splittedStr.length - 1))//we reached the end of the string, but it is in lexicographic order
			{
				sortedSequence[count] = splittedStr[j];
				count++;
			}
			
			if(count >= max)
			{
				max = count;
				maxSortedSequence = Arrays.copyOf(sortedSequence, count);
			}
			
			i++;
			//count = 0;
		}
		
		
		return String.join(" ", maxSortedSequence);

	}

	
	public static boolean isEditDistanceOne(String a, String b){

		if(a.compareTo(b) == 0)//the two string are equals
		{
			return true;
		}
		
		if(Math.abs(a.length() - b.length()) > 1)//there is more than 1 char difference between the two strings
		{
			return false;
		}
		
		
		//now we know there at most one charcter difference between the strings
		if(a.length() == 0 || b.length() == 0)//one of the strings is empty
		{
			return true;
		}
		
		if(a.length() == 1 && b.length() == 1)//there are both 1 character strings, so it would take up to 1 change to make them the same
		{
			return true;
		}
		
		
		
		int differences;
		
		if(a.length() > b.length())//a is the longer string
		{
			differences = countDifferences(a, b);
		}
		
		else //b is the longer string
		{
			differences = countDifferences(b, a);
		}
		
		
		
		return (differences <= 1);
	}
	
	public static int countDifferences(String longer, String shorter)
	{
		int count = 0;
		for(int i=0; i < shorter.length(); i++)
		{
			if(shorter.charAt(i) != longer.charAt(i))
			{
				if(i==0)
				{
					if(shorter.length() == longer.length())
						count++;
					else if(shorter.charAt(i) != longer.charAt(i+1))
						count++;
				}
				else
					count++;
			}
		}
		return count;
	}
	
}
