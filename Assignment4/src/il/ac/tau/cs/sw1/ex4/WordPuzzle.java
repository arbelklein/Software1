package il.ac.tau.cs.sw1.ex4;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		char[] puzzle = new char[word.length()];
		
		for(int i=0; i<puzzle.length; i++)
		{
			if(template[i] == true)
				puzzle[i] = HIDDEN_CHAR;
			else
				puzzle[i] = word.charAt(i);
		}
				
		return puzzle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		if(word.length() != template.length)
			return false;
		
		/*the idea is to go over all the letters that suppose to be hidden, then find all the occurrences
		 * of this letter, and to see if every occurrence is hidden in template.
		 */
		
		char[] hiddenLetters = new char[word.length()];
		char[] showedLetters = new char[word.length()];
		
		int hiddenIndex =0, showedIndex=0;
		
		for(int i=0; i<word.length(); i++)
		{
			char toCheckLetter = word.charAt(i);
			if(template[i] == true) //the letter is hidden
			{ 
				if((Arrays.toString(hiddenLetters)).indexOf(toCheckLetter) == -1 && (Arrays.toString(showedLetters)).indexOf(toCheckLetter) == -1) //we have'nt checked this letter yet
				{
					if(i == word.length() - 1) //reached the end of the word.
					{
						hiddenLetters[hiddenIndex] = word.charAt(i);
						hiddenIndex++;
					}
					else
					{
						int occurenceIndex = word.indexOf(toCheckLetter, i+1);
						while(occurenceIndex != -1) //searching all the occurrences of the letter.
						{
							if(template[occurenceIndex] == false) //this occurrence of the letter is'nt hidden in the template.
								return false;
							occurenceIndex = word.indexOf(toCheckLetter, occurenceIndex+1);
						}
						hiddenLetters[hiddenIndex] = word.charAt(i);
						hiddenIndex++;
					}
				}
				else if((Arrays.toString(showedLetters)).indexOf(toCheckLetter) != -1) //we already checked this letter, and it once showed and once hidden.
					return false;
			}
			else
			{
				if((Arrays.toString(hiddenLetters)).indexOf(toCheckLetter) != -1) //we already checked this letter, and it once showed and once hidden.
					return false;
				showedLetters[showedIndex] = word.charAt(i);
				showedIndex++;
			}
		}
		
		if(hiddenIndex == 0 || showedIndex == 0)
			return false;
		
		
		
		return true;
	}
	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		
		//array to hold all of the legal option for templates.
		boolean[][] checkAllLegal = new boolean[nAboveK(word.length(), k) + 1][word.length()];
		/*
		 * array to store info about the checkAllLegal array. 
		 * if cell i contains true, then in checkAllLegal[i] there is an valid template.
		 */
		boolean[] isLegal = new boolean[checkAllLegal.length]; 
		
		String min = minBinaryNumber(word.length(), k);
		String max = maxBinaryNumber(word.length(), k);
		
		int count = 0, j=0;
		
		int minDec = binToDec(Integer.parseInt(min));
		int maxDec = binToDec(Integer.parseInt(max));
		
		for(int i=minDec; i<=maxDec && j<checkAllLegal.length; i++)
		{
			String format = "%" + word.length() + "s";
			String binary = String.format(format, Integer.toBinaryString(i)).replaceAll(" ", "0");;
			if(!checkVaildBinary(binary, k)) //checking if the binary is in valid formation regarding the number of 1's in the string
				continue;
			
			boolean[] checkTemplate = binToTemplate(binary);
			
			if(checkLegalTemplate(word, checkTemplate)) //the template is valid.
			{
				count++;
				checkAllLegal[j] = Arrays.copyOf(checkTemplate, checkTemplate.length);
				isLegal[j] = true;
			}
			else
			{
				isLegal[j] = false;
			}
			
			j++;
		}
		
		boolean[][] AllLegalTemplates = new boolean[count][word.length()];
		int allLegalIndex = 0;
		for(int i=0; i<isLegal.length; i++)
		{
			if(isLegal[i]) //the template in checkAllLegal[i] is legal. 
			{
				AllLegalTemplates[allLegalIndex] = Arrays.copyOf(checkAllLegal[i], checkAllLegal[i].length);
				allLegalIndex++;
			}
		}
		
		
		return AllLegalTemplates;
	}
	
	//return the minimum binary number giving: n - number of bits, k - number of 1's.
	public static String minBinaryNumber(int n, int k)
	{
		String binary = new String("");
		
		for(int i=0; i<(n-k); i++)
			binary = binary.concat("0");
		
		for(int i=(n-k); i<n; i++)
			binary = binary.concat("1");
				
		return binary;
	}
	
	//return the maximum binary number giving: n - number of bits.
	public static String maxBinaryNumber(int n, int k)
	{
		String binary = new String("");
		
		for(int i=0; i<k; i++)
			binary = binary.concat("1");
		
		for(int i=k; i<n; i++)
			binary = binary.concat("0");
				
		return binary;
	}
	
	//return the decimal number of a giving binary number.
	public static int binToDec(int bin)
	{
		int decimal = 0;  
	    int n = 0;  
	    while(true){  
	      if(bin == 0){  
	        break;  
	      } else {  
	          int temp = bin%10;  
	          decimal += temp*Math.pow(2, n);  
	          bin = bin/10;  
	          n++;  
	       }  
	    }  
	    return decimal; 
	}
	
	//returns the n above k math.
	public static int nAboveK(int n, int k)
	{
		return (factorial(n) / (factorial(k)*factorial(n-k)));
	}
	
	//returns x!.
	public static int factorial(int x)
	{
		int factorial = 1;
		
		for(int i=x; i>0; i--)
		{
			factorial = factorial * i;
		}
		
		return factorial;
	}
	
	//return template from string of binary number.
	public static boolean[] binToTemplate(String bin)
	{
		boolean[] template = new boolean[bin.length()];
		
		for(int i=0; i<template.length; i++)
		{
			if(bin.charAt(i) == '0')
				template[i] = false;
			else
				template[i] = true;
		}
		
		return template;
	}
	
	//returns true if in the bin string the number of 1's = k.
	public static boolean checkVaildBinary(String bin, int k)
	{
		int count = 0;
		for(int i=0; i<bin.length(); i++)
		{
			if(bin.charAt(i) == '1')
				count++;
		}
		
		return (count == k);
	}
	
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int numOfOccurrs = 0, index = -1;
		
		while(word.indexOf(guess, index + 1) != -1)
		{
			if(puzzle[word.indexOf(guess, index + 1)] == HIDDEN_CHAR)
			{
				puzzle[word.indexOf(guess)] = guess;
				numOfOccurrs++;
			}
			index++;
		}
		
		
		
		return numOfOccurrs;
	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		char[] hint = new char[2];
		char[] rightNotGuessed = new char[26];
		char[] wrongNotGuessed = new char[26];
		
		int rightIndex=0, wrongIndex=0;
		
		for(int i=0; i<already_guessed.length; i++)
		{
			if(already_guessed[i] == false) //the user has'nt guessed this letter.
			{
				char guess = (char)(i + 97);
				if(word.indexOf(guess) == -1) //the letter is'nt in the word.
				{
					wrongNotGuessed[wrongIndex] = guess;
					wrongIndex++;
				}
				
				else
					if(puzzle[word.indexOf(guess)] == HIDDEN_CHAR) //the letter is hidden.
					{
						rightNotGuessed[rightIndex] = guess;
						rightIndex++;
					}
			}
		}
		
		Random rand = new Random(2806);
		int randRight = rand.nextInt(rightIndex);
		int randWrong = rand.nextInt(wrongIndex);
		
		if(rightNotGuessed[randRight] <= wrongNotGuessed[randWrong])
		{
			hint[0] = rightNotGuessed[randRight];
			hint[1] = wrongNotGuessed[randWrong];
		}
		else
		{
			hint[0] = wrongNotGuessed[randWrong];
			hint[1] = rightNotGuessed[randRight];
		}
				
		return hint;
	}

	

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		
		printSettingsMessage();
		
		boolean tryAgain = true;
		
		do
		{
			printSelectTemplate();
			int input = inputScanner.nextInt();
			
			if(input == 1)
			{
				printSelectNumberOfHiddenChars();
				int numHidden = inputScanner.nextInt();
				
				boolean[][] allTemplates = getAllLegalTemplates(word, numHidden);
				if(allTemplates.length == 0) //unable to create valid template for numHidden and word.
				{
					printWrongTemplateParameters();
					tryAgain = false;
				}
				else
				{
					Random rand = new Random(2806);
					int randTemplate = rand.nextInt(allTemplates.length);
					tryAgain = false;
					return createPuzzleFromTemplate(word, allTemplates[randTemplate]);
				}
			}
			else if(input == 2)
			{
				printEnterPuzzleTemplate();
				
				String[] fragments = inputScanner.next().split(",");
				boolean[] template = new boolean[word.length()];
				
				for(int i=0; i<template.length; i++)
				{
					if(fragments[i].equals("X"))
					{
						template[i] = false;
					}
					else if(fragments[i].equals("_"))
					{
						template[i] = true;
					}
					else
					{
						System.out.println("Invalid input");
					}
				}
				
				if(checkLegalTemplate(word, template))
				{
					tryAgain = false;
					return createPuzzleFromTemplate(word, template);
				}
				else
				{
					printWrongTemplateParameters();
					tryAgain = true;
				}
			}
			else
			{
				System.out.println("Invalid input");
				tryAgain = true;
			}
		}
		while(tryAgain);
		
		return null;
	}
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		
		printGameStageMessage();
		
		int numGusses = countHiddenChars(word, puzzle) + 3;
		boolean[] guessedChars = new boolean[26]; //max length is all the alphabet.
		
		
		while(numGusses > 0)
		{
			printPuzzle(puzzle);
			printEnterYourGuessMessage();
			
			char guess = inputScanner.next().charAt(0);
			
			
			if(guess == 'H') //need hint.
			{
				printHint(getHint(word, puzzle, guessedChars));
			}
			else
			{
				guessedChars[guess - 97] = true; //(guess - 97) = range[0 - 25], because a=97, z=122.
				
				if(applyGuess(guess, word, puzzle) > 0) //there was a change in the puzzle.
				{
					if(countHiddenChars(word, puzzle) == 0) //there are no hidden letters.
					{
						printWinMessage();
						numGusses = -1; //ending the loop and the function.
					}
					else
					{
						numGusses--;
						printCorrectGuess(numGusses);
					}
				}
				
				else //there wasn't a change in the puzzle.
				{
					numGusses--;
					printWrongGuess(numGusses);
				}
			}
		}
		
		if(numGusses == 0) //failed to solve the puzzle.
		{
			printGameOver();
		}
		

	}
	
	public static int countHiddenChars(String word, char[] puzzle)
	{
		char[] hiddenChar = new char[puzzle.length];
		int count = 0;
		
		for(int i=0; i<puzzle.length; i++)
		{
			if(puzzle[i] == '_') //the letter is hidden
			{
				char letter = word.charAt(i);
				if((Arrays.toString(hiddenChar)).indexOf(letter) == -1) //the letter is'nt already in the hiddenChar array
				{
					hiddenChar[count] = letter;
					count++;
				}
			}
		}
		return count;
	}

/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
