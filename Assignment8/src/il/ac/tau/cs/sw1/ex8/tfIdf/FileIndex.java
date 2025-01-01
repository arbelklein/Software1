package il.ac.tau.cs.sw1.ex8.tfIdf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	
	private HashMap<String, HashMapHistogram<String>> appearanceMap;
	private HashMap<String, List<Map.Entry<String, Double>>> tfidfMap;

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		
		this.appearanceMap = new HashMap<String, HashMapHistogram<String>>();
		this.tfidfMap = new HashMap<String, List<Map.Entry<String, Double>>>();
		
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				String filename = file.getName();
				HashMapHistogram<String> appearances = new HashMapHistogram<String>();
				
				try {
					List<String> words = FileUtils.readAllTokens(file);
					for(String word : words)
					{
						appearances.addItem(word);
					}
					this.appearanceMap.put(filename, appearances);
				}
				
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				String filename = file.getName();
				HashMapHistogram<String> appearances = this.appearanceMap.get(filename);
				List<Map.Entry<String, Double>> tfidf = new ArrayList<Map.Entry<String, Double>>();
				
				try {
					Iterator<Map.Entry<String, Integer>> appearIt = appearances.iterator();
					
					while (appearIt.hasNext())
					{
						Entry<String, Integer> entry = appearIt.next();
						tfidf.add(Map.entry(entry.getKey(), getTFIDF(entry.getKey(), filename)));
					}
					
					tfidf.sort(new Comparator<Map.Entry<String, Double>>() {
			    		@Override
			    		public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
			    			if(a.getValue() > b.getValue()) //a has tf-idf bigger than b
			    				return 1;
			    			else if(a.getValue() < b.getValue()) //b has tf-idf bigger than a
			    				return -1;
			    			else //a and b have the same tf-idf
			    			{
			    				return b.getKey().compareTo(a.getKey());
			    			}
			    		}
			    	});
					
					this.tfidfMap.put(filename, tfidf);
				}
				
				catch (FileIndexException e) {
					e.printStackTrace();
				}
				
			}
		}

		isInitialized = true;
	}
	
	
	
	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{ 
		if(!this.appearanceMap.containsKey(fileName)) //file is not in the index
			throw new FileIndexException("File " + fileName + " Not Found");
		
		HashMapHistogram<String> hist = this.appearanceMap.get(fileName);
		return hist.getCountForItem(word.toLowerCase());
		
	}
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{ 
		if(!this.appearanceMap.containsKey(fileName)) //file is not in the index
			throw new FileIndexException("File " + fileName + " Not Found");
		
		HashMapHistogram<String> hist = this.appearanceMap.get(fileName);
		Iterator<Map.Entry<String, Integer>> it = hist.iterator();
		int sum = 0;
		while(it.hasNext())
		{
			sum++;
			it.next();
		}
		return sum;
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return this.appearanceMap.size();
	}

	
	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		if(!this.appearanceMap.containsKey(fileName)) //file is not in the index
			throw new FileIndexException("File " + fileName + " Not Found");
		
		//counting number of words in fileName
		int wordsInFile = 0;
		HashMapHistogram<String> hist = this.appearanceMap.get(fileName);
		Iterator<Map.Entry<String, Integer>> it = hist.iterator();
		while(it.hasNext())
		{
			wordsInFile += it.next().getValue();
		}
		
		return calcTF(getCountInFile(word, fileName), wordsInFile);
	}
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		//counting number of docs containing word
		int filesWithWord = 0;
		Iterator<Entry<String, HashMapHistogram<String>>> mapIt = this.appearanceMap.entrySet().iterator();
		while(mapIt.hasNext())
		{
			Entry<String, HashMapHistogram<String>> entry = mapIt.next();
			HashMapHistogram<String> hist = this.appearanceMap.get(entry.getKey());
			if(hist.getCountForItem(word.toLowerCase()) != 0) //word is in file
				filesWithWord++;
		}
		
		return calcIDF(getNumOfFilesInIndex(), filesWithWord);
	}
	
	
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k) 
													throws FileIndexException{ //Q5
		if(!this.tfidfMap.containsKey(fileName)) //file is not in the index
			throw new FileIndexException("File " + fileName + " Not Found");
		
		List<Map.Entry<String, Double>> kList = new ArrayList<Map.Entry<String, Double>>();
		Iterator<Map.Entry<String, Double>> It = this.tfidfMap.get(fileName).iterator();
		
		int i = 0;
		//going to the k'th smallest item
		while(i < (this.tfidfMap.get(fileName).size() - k))
		{
			It.next();
			i++;
		}
		
		while(It.hasNext())
			kList.add(0, It.next());
			i++;
		
		return kList;
	}
	
	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		if(!this.tfidfMap.containsKey(fileName1)) //file1 is not in the index
			throw new FileIndexException("File " + fileName1 + " Not Found");
		if(!this.tfidfMap.containsKey(fileName2)) //file2 is not in the index
			throw new FileIndexException("File " + fileName2 + " Not Found");
		
		double sumAB = 0, sumAA = 0, sumBB = 0;
		List<Map.Entry<String, Double>> listA = this.tfidfMap.get(fileName1);
		List<Map.Entry<String, Double>> listB = this.tfidfMap.get(fileName2);
		HashMapHistogram<String> histA = this.appearanceMap.get(fileName1);
		HashMapHistogram<String> histB = this.appearanceMap.get(fileName2);
		Iterator<Entry<String, Integer>> histItA = histA.iterator();
		
		while(histItA.hasNext())
		{
			String word = histItA.next().getKey();
			
			if(histB.getCountForItem(word) != 0) //the word is in both files
			{
				Entry<String, Double> itemA = findTfidfMapItem(listA, word);
				Entry<String, Double> itemB = findTfidfMapItem(listB, word);
				sumAB += itemA.getValue() * itemB.getValue();
			}
		}
		
		//going through A
		for(Entry<String, Double> item : listA)
		{
			sumAA += item.getValue() * item.getValue();
		}
		
		//going through B
		for(Entry<String, Double> item : listB)
		{
			sumBB += item.getValue() * item.getValue();
		}
		
		return sumAB / Math.sqrt(sumAA * sumBB);
	}
	
	/*
	 * @pre: word is in tfidfMap List
	 */
	private Map.Entry<String, Double> findTfidfMapItem(List<Map.Entry<String, Double>> lst, String word)
	{
		for(Entry<String, Double> item : lst)
		{
			if(item.getKey().equals(word))
				return item;
		}
		
		return null;
	}
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) 
			throws FileIndexException{ //Q6
		if(!this.tfidfMap.containsKey(fileName)) //file1 is not in the index
			throw new FileIndexException("File " + fileName + " Not Found");
		
		List<Map.Entry<String, Double>> sortList = new ArrayList<Map.Entry<String, Double>>();
		Iterator<Entry<String, List<Entry<String, Double>>>> mapIt = this.tfidfMap.entrySet().iterator();
		while(mapIt.hasNext())
		{
			Entry<String, List<Entry<String, Double>>> entry = mapIt.next();
			sortList.add(Map.entry(entry.getKey(), getCosineSimilarity(fileName, entry.getKey())));
		}
		
		sortList.sort(new Comparator<Map.Entry<String, Double>>() {
    		@Override
    		public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
    			if(a.getValue() > b.getValue()) //a has Cosine-Similarity bigger than b
    				return 1;
    			else if(a.getValue() < b.getValue()) //b has Cosine-Similarity bigger than a
    				return -1;
    			else //a and b have the same Cosine-Similarity
    			{
    				return a.getKey().compareTo(b.getKey());
    			}
    		}
    	});
		
		List<Map.Entry<String, Double>> kList = new ArrayList<Map.Entry<String, Double>>();
		Iterator<Map.Entry<String, Double>> It = sortList.iterator();
		
		int i = 0;
		//going to the k'th smallest item
		while(i < (sortList.size() - k))
		{
			It.next();
			i++;
		}
		
		while(It.hasNext())
			kList.add(0, It.next());
		
		return kList;
	}

	
	
	//add private methods here, if needed

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}
	
}
