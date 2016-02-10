package assignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BagOfWordsTest {

	private static BagOfWords bow; 
	
	@Before
	public void setUp() throws Exception {
		bow = new BagOfWords("wordCount.csv", "parsed.csv", "encoded.csv");
	}

	@Test
	public void testBagWords() {
		bow.bagWords();
	}

}
