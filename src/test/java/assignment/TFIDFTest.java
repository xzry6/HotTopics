package assignment;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TFIDFTest {
	
	private TFIDF tfidf;
	
	@Before
	public void setUp() throws Exception {
		tfidf = new TFIDF("indexes.csv", "encoded.csv", "parsed.csv");
	}

	@Test
	public void test() {
		List<String[]> list = tfidf.getAll().select(3);
		//WriteUtil.writeCSV(list, "testResult.csv");
		assertNotNull(list);
		for(int i=0; i<list.size(); ++i) {
			String[] instance = list.get(i);
			assertNotNull(instance);
		}
	}

}
