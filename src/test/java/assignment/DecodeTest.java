package assignment;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import assignment.util.WriteUtil;

public class DecodeTest {
	
	private static Decode d;
	
	@Before
	public void setUp() throws Exception {
		d = new Decode("testResult.csv", "applause.csv");
	}

	@Test(timeout = 1000)
	public void testDecodeAll() {
		List<String[]> list = d.decodeAll();
		assertNotNull(list);
		WriteUtil.writeCSV(list, "xiaozhou_solution.csv");
		for(int i=0; i<list.size(); ++i) {
			String[] instance = list.get(i);
			assertNotNull(instance);
		}
	}

	@Test
	public void testDecode() {
		String s = d.decode("nutrut", 0);
		assertEquals(s, "nutrution");
	}

}
