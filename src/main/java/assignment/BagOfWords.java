package assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import assignment.util.ReadUtil;
import assignment.util.WriteUtil;


/**
 * this class is used to apply bag-of-words algorithm to input file;
 * 
 * - given two sequences:
 * 		String a = "this is a beer";
 * 		String b = "I like beer";
 * - first list all the words: 
 * 		String[] c = {"this", "is", "a", "beer", "I", "like"};
 * - encode the original string with number of words according to corresponding position in c:
 * 		encode a to [1,1,1,1,0,0];
 * 		encode b to [0,0,0,1,1,1];
 * 
 * @author Sean
 *
 */
public class BagOfWords {

	private String WordsInputName;
	private String outputName;
	private List<String[]> document;
	private Set<String> set;
	private List<int[]> result;
	
	/**
	 * construct a BagOfWords class with following inputs;
	 * @param WordsInputName a csv file with all the words
	 * @param documentInputName the file need to be encoded
	 * @param outputName output file name
	 */
	public BagOfWords(String WordsInputName, String documentInputName, String outputName) {
		this.outputName = outputName;
		this.WordsInputName = WordsInputName;
		document = ReadUtil.readCSV(documentInputName);
		set = new HashSet<String>();
		result = new ArrayList<int[]>();
	}
	
	/**
	 * begin encoding;
	 */
	public void bagWords() {
		readFile();
		encode();
		writeFile();
	}
	
	
	private void writeFile() {
		WriteUtil.writeList(result, outputName);
	}
	
	
	private void readFile() {
		List<String[]> list = ReadUtil.readCSV(WordsInputName);
		set = Operator.removeDuplicate(list);
	}
	
	
	/**
	 * core encode method;
	 */
	private void encode() {
		List<String> indexes = new ArrayList<String>(set);
//		WriteUtil.writeStringList(indexes, "indexes.csv");
		for(String[] sa:document) {
			int[] tmp = new int[indexes.size()];
			for(int i=0; i<sa.length; ++i) {
				int coefficient = 1;
				if(i == 0) coefficient = 5;
				String[] words = sa[i].split(" ");
				for(int j=0; j<words.length; ++j ) {
					String word = words[j];
					if(word == null || word.length() == 0) continue;
					if(j>0 && set.contains(words[j-1]+" "+word))
						tmp[indexes.indexOf(words[j-1]+" "+word)] += coefficient;
					if(set.contains(word)) 
						tmp[indexes.indexOf(word)] += coefficient;
				}
			}
			result.add(tmp);
		}
	}
}
