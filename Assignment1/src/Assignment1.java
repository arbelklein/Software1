

public class Assignment1 {
	public static void main(String[] args){
		char chr, newChar;
		int offset;

		chr = args[0].charAt(0);
		offset = Integer.parseInt(args[1]);
		newChar = (char) (chr + offset);

		System.out.println("New char is " + newChar + ".");
	}
}
