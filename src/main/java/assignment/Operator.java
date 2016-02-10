package assignment;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * this class offers several static operations to dataset;
 * 
 * @author Sean
 *
 */
public class Operator {
	
	/**
	 * Comparator of the Word based on the number appeared
	 */
	public static Comparator<Word> c = new Comparator<Word>() {
		@Override
		public int compare(Word o1, Word o2) {
			return o2.getCount()-o1.getCount();
		}
	};
	
	
	/**
	 * remove duplicates in the list
	 * @param list input list
	 * @return output set
	 */
	public static Set<String> removeDuplicate(List<String[]> list) {
		Set<String> set = new HashSet<String>();
		for(String[] sa:list) {
			if(sa.length > 0)
				set.add(sa[0]);
		}
		return set;
	}
	
	
	/**
	 * count number of words appeared and sort in a priority queue;
	 * @param list file list
	 * @return Word priority queue
	 */
	public static PriorityQueue<Word> countNum(List<String[]> list) {
		Map<String, Word> map = new HashMap<String, Word>();
		PriorityQueue<Word> q = new PriorityQueue<Word>(1000000, c);
		for(String[] l:list) {
			for(String s:l) {
				String[] arr = s.split(" ");
				for(String word:arr) {
					if(word == null || word.length() <= 1) continue;
					if(word.charAt(0) == '-' || word.charAt(0) == '#') continue;
					if(Character.isDigit(word.charAt(0))) continue;
					word = word.toLowerCase();
					if(map.containsKey(word)) {
						int count = map.get(word).getCount()+1;
						Word nw = new Word(word, count);
						q.remove(map.get(word));
						q.offer(nw);
						map.put(word, nw);
					}
					else {
						Word nw = new Word(word, 1);
						map.put(word, nw);
						q.offer(nw);
					}
				}
			}
		}
		return q;
	}
	
	
	/**
	 * count number of phrases appeared and sort in a priority queue;
	 * @param list file list
	 * @return phrase priority queue
	 */
	public static PriorityQueue<Word> count2Nums(List<String[]> list) {
		Map<String, Word> map = new HashMap<String, Word>();
		PriorityQueue<Word> q = new PriorityQueue<Word>(1000000, c);
		for(String[] l:list) {
			for(String s:l) {
				String[] arr = s.split(" ");
				for(int i=1; i<arr.length; ++i) {
					if(arr[i-1] == null) continue;
					if(arr[i-1].length() == 0) continue;
					String word = arr[i-1]+" "+arr[i];
					if(word == null || word.length() <= 1) continue;
					if(word.charAt(0) == '-' || word.charAt(0) == '#') continue;
					if(Character.isDigit(word.charAt(0))) continue;
					word = word.toLowerCase();
					if(map.containsKey(word)) {
						int count = map.get(word).getCount()+1;
						Word nw = new Word(word, count);
						q.remove(map.get(word));
						q.offer(nw);
						map.put(word, nw);
					}
					else {
						Word nw = new Word(word, 1);
						map.put(word, nw);
						q.offer(nw);
					}
				}
			}
		}
		return q;
	}
}
