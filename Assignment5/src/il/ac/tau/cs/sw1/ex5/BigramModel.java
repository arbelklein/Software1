package il.ac.tau.cs.sw1.ex5;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		FileReader fReader = new FileReader(new File(fileName));
		BufferedReader buffReader = new BufferedReader(fReader);
		
		String[] tempVoc = new String[MAX_VOCABULARY_SIZE];
		int vocIndex = 0, someNumIndex = -1;
		String checkWord, checkLine;
		String[] splittedLine;
		
		while(vocIndex<tempVoc.length && (checkLine = buffReader.readLine()) != null)
		{
			//checkLine = buffReader.readLine();
			splittedLine = checkLine.split("\\s+"); //splits by any white space.
			for(int j=0; j<splittedLine.length; j++)
			{
				checkWord = splittedLine[j].toLowerCase();
				int wordIndex = 0;
				while(wordIndex < checkWord.length() && Character.isDigit(checkWord.charAt(wordIndex)) == true)
				{
					wordIndex++;
				}
				if(wordIndex < checkWord.length() && Character.isDigit(checkWord.charAt(wordIndex)) == false) //reached char that isn't a number.
				{
					if(Character.isAlphabetic(checkWord.charAt(wordIndex)) == true) //Valid word.
					{
						if(checkInVoc(tempVoc, checkWord, vocIndex) == -1)//check if the word is already in the vocabulary.
						{
							tempVoc[vocIndex] = checkWord;
							vocIndex++;
						}
					}
					else
					{
						wordIndex++;
						while(wordIndex < checkWord.length() && Character.isAlphabetic(checkWord.charAt(wordIndex)) == false)
						{
							wordIndex++;
						}
						if(wordIndex < checkWord.length() && Character.isAlphabetic(checkWord.charAt(wordIndex)) == true) //Valid word.
						{
							if(checkInVoc(tempVoc, checkWord, vocIndex) == -1)//check if the word is already in the vocabulary.
							{
								tempVoc[vocIndex] = checkWord;
								vocIndex++;
							}
						}
						
						//otherwise, the word doesn't contains an English letter and isn't a valid number - IGNORE the word.
					}
				}
				else if(wordIndex == checkWord.length()) //reached the end of the word, and it's all digits.
				{
					if(someNumIndex == -1) //there isn't a number in the vocabulary yet.
					{
						tempVoc[vocIndex] = SOME_NUM;
						someNumIndex = vocIndex;
						vocIndex++;
					}
				}
			}
		}
		
		String[] vocabulary = new String[vocIndex];
		vocabulary = Arrays.copyOf(tempVoc, vocIndex);
		
		buffReader.close();
		return vocabulary;
	}
	
	//returns the index in the vocabulary if word is found in voc, else returns -1.
	public int checkInVoc(String[] voc, String word, int vocIndex)
	{
		if(isNumber(word))
			word = SOME_NUM;
		
		for(int i=0; i<vocIndex; i++)
		{
			if(voc[i].equals(word) == true)//word is in voc
			{
				return i;
			}
		}
		return -1;
	}
	
	public boolean isNumber(String word)
	{
		for(int i=0; i<word.length(); i++)
		{
			if(!Character.isDigit(word.charAt(i))) //the char isn't digit
				return false;
		}
		
		return true;
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		FileReader fReader = new FileReader(new File(fileName));
		BufferedReader buffReader = new BufferedReader(fReader);
		
		int[][] countArr = new int[vocabulary.length][vocabulary.length];
		String checkLine;
		String[] splittedLine;
		
		while((checkLine = buffReader.readLine()) != null)
		{
			splittedLine = checkLine.split("\\s+"); //splits by any white space.
			for(int i=0; i<(splittedLine.length - 1); i++)
			{
				int x = checkInVoc(vocabulary, splittedLine[i].toLowerCase(), vocabulary.length);
				int y = checkInVoc(vocabulary, splittedLine[i+1].toLowerCase(), vocabulary.length);
				
				if(x != -1 && y != -1) //both words are valid.
				{
					countArr[x][y]++;
				}
			}
		}
		
		buffReader.close();
		return countArr;

	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3		
		String vocFileName = fileName + VOC_FILE_SUFFIX;
		String countsFileName = fileName + COUNTS_FILE_SUFFIX;
		
		FileWriter vocWriter = new FileWriter(new File(vocFileName));
		FileWriter countsWriter = new FileWriter(new File(countsFileName));
		
		BufferedWriter vocBuffWriter = new BufferedWriter(vocWriter);
		BufferedWriter countsBuffWriter = new BufferedWriter(countsWriter);
		
		//writing the voc file:
		vocBuffWriter.write(mVocabulary.length + " words\n");
		for(int i=0; i<mVocabulary.length; i++)
			vocBuffWriter.write(i + "," + mVocabulary[i] + "\n");
		
		
		//writing the counts file:
		for(int i=0; i<mBigramCounts.length; i++)
		{
			for(int j=0; j<mBigramCounts[i].length; j++)
			{
				if(mBigramCounts[i][j] != 0)
					countsBuffWriter.write(i + "," + j + ":" + mBigramCounts[i][j] + "\n");
			}
		}
		
		vocBuffWriter.close();
		countsBuffWriter.close();
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		String vocFileName = fileName + VOC_FILE_SUFFIX;
		String countsFileName = fileName + COUNTS_FILE_SUFFIX;
		
		FileReader vocReader = new FileReader(new File(vocFileName));
		FileReader countsReader = new FileReader(new File(countsFileName));
		
		BufferedReader vocBuffReader = new BufferedReader(vocReader);
		BufferedReader countsBuffReader = new BufferedReader(countsReader);
		
		//finding the vocabulary size.
		String line = vocBuffReader.readLine();
		String[] splittedLine = line.split("\\s+");
		int vocSize = Integer.valueOf(splittedLine[0]);
		
		String[] tempVoc = new String[vocSize];
		int[][] tempCounts = new int[vocSize][vocSize];
		
		
		//Initialise vocabulary.
		while((line = vocBuffReader.readLine()) != null)
		{
			splittedLine = line.split(",");
			int vocIndex = Integer.valueOf(splittedLine[0]);
			tempVoc[vocIndex] = splittedLine[1];
		}
		
		//Initialise countsArray.
		while((line = countsBuffReader.readLine()) != null)
		{
			splittedLine = line.split("[,:]");
			int i = Integer.valueOf(splittedLine[0]);
			int j = Integer.valueOf(splittedLine[1]);
			tempCounts[i][j] = Integer.valueOf(splittedLine[2]);
		}
		
		mVocabulary = tempVoc;
		mBigramCounts = tempCounts;
		
		
		vocBuffReader.close();
		countsBuffReader.close();
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		if(isNumber(word))
			word = SOME_NUM;
		
		for(int i=0; i<mVocabulary.length; i++)
		{
			if(mVocabulary[i].equals(word) == true)//word is in mVocabulary
			{
				return i;
			}
		}
		return ELEMENT_NOT_FOUND;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int word1Index = getWordIndex(word1);
		int word2Index = getWordIndex(word2);
		
		if(word1Index == ELEMENT_NOT_FOUND || word2Index == ELEMENT_NOT_FOUND)
			return 0;
		
		return mBigramCounts[word1Index][word2Index];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int max = 0;
		int wordIndex = getWordIndex(word);
		
		String maxWord = "";
		
		for(int i=0; i < mBigramCounts[wordIndex].length; i++)
		{
			if(mBigramCounts[wordIndex][i] > max)
			{
				max = mBigramCounts[wordIndex][i];
				maxWord = mVocabulary[i];
			}
		}
		
		if(max == 0) //there isn't any word that came after the desired word.
			return null;
		
		return maxWord;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String[] splittedSentence = sentence.split(" ");
		
		if(splittedSentence.length == 1) //only one word in the sentence.
		{
			if(getWordIndex(splittedSentence[0]) == ELEMENT_NOT_FOUND) //word isn't in vocabulary.
				return false;
			else
				return true;
		}
		
		for(int i=0; i<(splittedSentence.length - 1); i++)
		{
			int word1Index = getWordIndex(splittedSentence[i]);
			int word2Index = getWordIndex(splittedSentence[i+1]);
			
			if(word1Index == ELEMENT_NOT_FOUND || word2Index == ELEMENT_NOT_FOUND) //one or two words aren't in the vocabulary.
				return false;
			
			if(mBigramCounts[word1Index][word2Index] == 0) //there wasn't a combination that word2 came after word1.
				return false;
		}	
		
		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		
		if(checkZeroedArr(arr1) || checkZeroedArr(arr2)) //one or both arrays contains only 0's.
			return ELEMENT_NOT_FOUND;
		
		int sumArr1Arr2 = 0, sumArr1 = 0, sumArr2 = 0;
		
		for(int i=0; i<arr1.length; i++)
		{
			sumArr1Arr2 += (arr1[i] * arr2[i]);
			sumArr1 += (arr1[i] * arr1[i]);
			sumArr2 += (arr2[i] * arr2[i]);
		}
		
		
		return (sumArr1Arr2 / (Math.sqrt(sumArr1) * Math.sqrt(sumArr2)));
	}
	
	//return true if the arr is filled with only 0's, false otherwise.
	public static boolean checkZeroedArr(int[] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			if(arr[i] != 0)
				return false;
		}
		
		return true;
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		
		int[] wordVector = createWordVector(word);
		int[][] vocVectors = createVocVectorMat();
		
		double max = 0.;
		String maxWord = "";
		
		for(int i=0; i<mVocabulary.length; i++)
		{
			if(!(mVocabulary[i].equals(word)))
			{
				int[] checkVector = vocVectors[i];
				
				double cosineSim = calcCosineSim(wordVector, checkVector);
				
				if(cosineSim > max)
				{
					max = cosineSim;
					maxWord = mVocabulary[i];
				}
			}
		}
		
		return maxWord;
	}
	
	//return the word vector for word.
	public int[] createWordVector(String word)
	{
		int[] vector = new int[mVocabulary.length];
		
		int wordIndex = getWordIndex(word);
		vector = Arrays.copyOf(mBigramCounts[wordIndex], mVocabulary.length);
		
		return vector;
	}
	
	public int[][] createVocVectorMat()
	{
		int[][] vector = new int[mVocabulary.length][];
		
		for(int i=0; i<mVocabulary.length; i++)
		{
			vector[i] = createWordVector(mVocabulary[i]);
		}
		
		return vector;
	}

	
}
