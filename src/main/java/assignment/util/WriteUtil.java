package assignment.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import assignment.Word;

public class WriteUtil {
	public static void writeMap(Map<String, Integer> map, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fw);
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String word = it.next();
				out.write(word+" "+map.get(word)+"\n");
			}
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeDoubleArr(double[][] result, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fw);
			for(int i=0; i<result.length; ++i) {
				for(int j=0; j<result[i].length; ++j) {
					if(j != 0) out.write(", ");
					out.write(Double.toString(result[i][j]));
				}
				out.write('\n');
			}
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void writeList(List<int[]> list, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			for(int[] arr:list) {
				for(int i=0; i<arr.length; ++i) {
					if(i != 0) fw.write(",");
					int num = arr[i];
					fw.write(String.valueOf(num));
				}
				fw.write('\n');
			}
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeStringList(List<String> list, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			for(String s:list)
				fw.write(s+'\n');
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeQueue(Queue<Word> q, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fw);
			while(!q.isEmpty()) {
				Word word = q.poll();
				out.write(word.getWord()+", "+word.getCount()+"\n");
			}
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeQueue(Queue<Word> q, String fileName, int numLimit) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fw);
			while(!q.isEmpty()) {
				Word word = q.poll();
				if(word.getCount() >= numLimit)
					out.write(word.getWord()+", "+word.getCount()+"\n");
			}
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void writeCSV(List<String[]> list, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fw);
			for(String[] sa:list) {
				for(int i=0; i<sa.length; ++i) {
					if(i == 0 && sa[i] != null)
						out.write(sa[i]);
					else
						out.write(","+sa[i]);
				}
				out.write("\n");
			}
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
