package assignment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadUtil {
	public static List<String[]> readCSV(String fileName) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = reader.readLine()) != null) {
				list.add(word.split(","));
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static String readString(String fileName) {
		String s = new String();
		try {
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = reader.readLine()) != null)
				s = s+" "+word;
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
