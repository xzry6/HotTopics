package assignment.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class LuceneUtil {
	/**
	 * stop word list
	 */
	List<String> stopWordsList = Arrays.asList(
		      "a", "an", "and", "are", "as", "at", "be", "but", "by",
		      "for", "if", "in", "into", "is", "it",
		      "no", "not", "of", "on", "or", "such",
		      "that", "the", "their", "then", "there", "these",
		      "they", "this", "to", "was", "will", "with", "your", 
		      "you", "more", "can", "have", "from", "who", "than",
		      "on", "about", "also", "all", "our", "we", 
		      "ha", "were", "when", "so", "over", "up", 
		      "some", "don", "those", "do", "how", "per"
		    );
	
	final CharArraySet stopWords = new CharArraySet(Version.LUCENE_48, stopWordsList, false);
	
	
	
	/**
	 * this method is used to encode the original csv file;
	 * @param inputFile input the original file
	 * @param outputFile output the encoded file
	 */
	public void filter(String inputFile, String outputFile) {;
		List<String[]> list = ReadUtil.readCSV(inputFile);
		List<String[]> result = new ArrayList<String[]>();
	  	for(String[] sa:list) {
	  		String[] nsa = new String[sa.length];
	  		for(int i=0; i<sa.length; ++i) {
	  	  		String ns = filterStopWords(sa[i]);
	  	  		nsa[i] = ns;
	  	  	}
	  	  	result.add(nsa);
	  	}
	    System.out.println(result.size());
	    WriteUtil.writeCSV(result, outputFile);
	}
	
	
	
	/**
	 * filter the input sequence by stop words and several filters.
	 * @param input input sequence
	 * @return encoded sequence
	 */
	public String filterStopWords(String input) {
        input = input.replaceAll("-+", " ");
        input = input.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
        input = input.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");
	    TokenStream tokenStream = null;
	    StringBuilder sb = new StringBuilder();
		try {
			tokenStream = new ClassicTokenizer(Version.LUCENE_48, new StringReader(input));
			tokenStream = new LowerCaseFilter(Version.LUCENE_48, tokenStream);
	        tokenStream = new ClassicFilter(tokenStream);
	        tokenStream = new ASCIIFoldingFilter(tokenStream);
		    tokenStream = new StopFilter(Version.LUCENE_48, tokenStream, stopWords);
		    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		    tokenStream.reset();
		    while (tokenStream.incrementToken()) {
		        String term = charTermAttribute.toString();
		        term = filterStems(term);
		        if(term.length() == 0) continue;
		        if(sb.length() != 0) sb.append(" ");
		        sb.append(term);
		    }
			tokenStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	/**
	 * stem the input single WORD. this method is called in filterStopWords() method.
	 * @param input single WORD
	 * @return encoded word
	 */
	public String filterStems(String input) {
		
        try {
	        	TokenStream tokenStream = new ClassicTokenizer(Version.LUCENE_48, new StringReader(input));
	        tokenStream = new PorterStemFilter(tokenStream);
	        Set<String> stems = new HashSet<String>();
	        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
			tokenStream.reset();
	        while (tokenStream.incrementToken()) {
	        		String s = token.toString();
	        		if(s.matches("[0-9]+")) continue;
	            stems.add(s);
	        }

	        if (stems.size() != 1) {
	        		tokenStream.close();
	            return new String();
	        }
	        String stem = stems.iterator().next();

	        if (!stem.matches("[\\w-]+")) {
	    		tokenStream.close();
	            return new String();
	        }

			tokenStream.close();
	        return stem;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return new String();
	}

}
