package assignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import assignment.util.ReadUtil;
/**
 * this class is used to calculate the tf-idf value of each appeared in documents;
 * 
 * tf-idf is an algorithm used for determine the importance of a word in the documents;
 * assume there are some documents in a file:
 * 
 * - tf is frequency of a word in a document;
 * 		              number of this word in this document
 * 	        ---------------this-is-a-divide-operator-----------------
 *              number of the most frequency word in this document
 *              
 * - idf is related to probability of a word appeared in documentS;
 *                 number of documents which contain the word
 * 	        ---------------this-is-a-divide-operator-----------------
 *                            all the documents
 *                           
 * - tf-idf = tf*(-log(idf));
 * 
 * @author Sean
 *
 */
public class TFIDF {
	
	private Map<String, Integer> appeared;
	private List<String[]> indexes;
	private int[][] frequency;
	private double[][] result;
	
	
	/**
	 * Comparator to sort the word by frequency;
	 */
	private Comparator<Word> c = new Comparator<Word>() {
		@Override
		public int compare(Word o1, Word o2) {
			if(o1.getFrequency() == o2.getFrequency())
				return 0;
			if(o1.getFrequency() > o2.getFrequency())
				return -1;
			else return 1;
		}
	};
	
	
	/**
	 * initialize a TFIDF class;
	 * @param indexName a file contains all the words with default 
	 *                  indexes as information in encoded file
	 * @param encodedName encoded file
	 * @param fileName original file
	 */
	public TFIDF(String indexName, String encodedName, String fileName) {
		appeared = new HashMap<String, Integer>();
		indexes = ReadUtil.readCSV(indexName);
		frequency = transformList(ReadUtil.readCSV(encodedName));
		generateMap(ReadUtil.readCSV(fileName));
	}
	
	
	/**
	 * select and return top num topics in the document
	 * @param num num of topics being showed
	 * @return a list of top num topics for each document
	 */
	public List<String[]> select(int num) {
		List<String[]> list = new ArrayList<String[]>();
		for(int i=0; i<result.length; ++i) {
			PriorityQueue<Word> q = new PriorityQueue<Word>(10000, c);
			for(int j=0; j<result[i].length; ++j) {
				if(result[i][j] == 0) continue;
				System.out.println(result[i][j]);
				Word word = new Word(indexes.get(j)[0], result[i][j]);
				q.offer(word);
			}
			String[] sa = new String[num];
			for(int k=0; k<num; ++k) 
				if(!q.isEmpty())
					sa[k] = q.poll().getWord();
			for(int m=0; m<num; ++m) {
				for(int n=0; n<num; ++n) {
					if(n == m) continue;
					if(!q.isEmpty()) {
						if(sa[m].indexOf(sa[n]) != -1)
							sa[n] = q.poll().getWord();
						else if(sa[n].indexOf(sa[m]) != -1)
							sa[m] = q.poll().getWord();
					}
				}
			}
			list.add(sa);
		}
		return list;
	}
	
	
	/**
	 * get all results;
	 * @return this instance
	 */
	public TFIDF getAll() {
		result = new double[frequency.length][];
		for(int i=0; i<frequency.length; ++i)
			result[i] = get(i);
		return this;
	}
	
	
	/**
	 * get an array result of a instance; 
	 * the array length is the same as index length, the order as well;
	 * @param i ith document in file
	 * @return a double array of tf-idf value;
	 */
	private double[] get(int i) {
		double[] result = new double[indexes.size()];
		int max = findMax(i);
		for(int j=0; j<result.length; ++j) {
			result[j] = TF(i, j, max)*IDF(j);
		}
		return result;
	}
	
	
	/**
	 * calculate a word's idf value
	 * @param j jth word in index
	 * @return idf value of this word
	 */
	private double IDF(int j) {
		double tmp = (double) indexes.size()/(appeared.get(indexes.get(j)[0])+1);
		return Math.log(tmp);
	}
	
	
	/**
	 * calculate tf value of ith document and jth word;
	 * @param i ith document
	 * @param j jth word in index
	 * @param maxValue the most frequency word in this document
	 * @return tf value
	 */
	private double TF(int i, int j, int maxValue) {
		return (double) frequency[i][j]/maxValue;
	}
	
	
	/**
	 * find the most frequency number in one document
	 * @param i ith document
	 * @return maxValue the most frequency word in this document
	 */
	private int findMax(int i) {
		int max = Integer.MIN_VALUE;
		for(int j=0; j<frequency[i].length; ++j)
			max = Math.max(max, frequency[i][j]);
		return max;
	}

	
	/**
	 * initialize the index map;
	 * @param file input file
	 */
	private void generateMap(List<String[]> file) {
		appeared = new HashMap<String, Integer>();
		for(String[] in:indexes) {
			String ii = in[0];
			appeared.put(ii, 0);
			for(String[] sa:file) {
				for(String s:sa) {
					if(s.indexOf(ii) != -1) {
						appeared.put(ii, appeared.get(ii)+1);
						break;
					}
				}
			}
			System.out.println(ii+": "+appeared.get(ii));
		}
	}
	
	
	/**
	 * turn a list of string to int matrix
	 * @param list list of String
	 * @return int matrix
	 */
	private int[][] transformList(List<String[]> list) {
		int[][] arr = new int[list.size()][];
		for(int i=0; i<list.size(); ++i) {
			String[] sa = list.get(i);
			arr[i] = new int[sa.length];
			for(int j=0; j<sa.length; ++j) {
				arr[i][j] = Integer.parseInt(sa[j]);
			}
		}
		return arr;
	}
}
