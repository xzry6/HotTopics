package assignment;

import java.util.ArrayList;
import java.util.List;

import assignment.util.LuceneUtil;
import assignment.util.ReadUtil;


/**
 * when found the encoded topics, use this class to decode the topics to original word;
 * - words in this approach is first encoded to similar words:
 * 		e.g. "study" and "studies" are both encoded to "studi";
 * - when we got "studi", a same lucene stemming method is called to find the same word
 * 	 in this instance;
 * 
 * 
 * @author Sean
 *
 */
public class Decode {
	private List<String[]> encoded;
	private List<String[]> file;
	
	
	/**
	 * input the encoded topic file and the original file;
	 * @param encodedFile encoded topic file
	 * @param originalFile original file
	 */
	public Decode(String encodedFile, String originalFile) {
		encoded = ReadUtil.readCSV(encodedFile);
		file = ReadUtil.readCSV(originalFile);
	}
	
	
	/**
	 * decode all words in encoded file and return a list of String array;
	 * @return a list of decoded String array
	 */
	public List<String[]> decodeAll() {
		List<String[]> result = new ArrayList<String[]>();
		for(int i=0; i<encoded.size(); ++i) {
			String[] sa = encoded.get(i);
			String[] ns = new String[sa.length];
			for(int j=0; j<sa.length; ++j) {
				String s = sa[j];
				StringBuilder sb = new StringBuilder();
				String[] sl = s.split(" ");
				for(String ss:sl) {
					String decoded = decode(ss, i);
					if(sb.length() != 0) sb.append(" ");
					sb.append(decoded);
				}
				ns[j] = sb.toString();
			}
			result.add(ns);
		}
		return result;
	}
	
	
	/**
	 * decode one word in file
	 * @param encode encoded word
	 * @param i row of encoded word
	 * @return decode word
	 */
	public String decode(String encode, int i) {
		String[] sa = file.get(i);
		LuceneUtil lu = new LuceneUtil();
		for(String s:sa) {
			String[] sl = s.replaceAll("-", " ").split(" ");
			for(String ss:sl) {
				if(lu.filterStems(ss.toLowerCase()).equals(encode))
					return ss.toLowerCase();
			}
		}
		return new String();
	}
}
