package assignment;

public class Word {
	
	private String word;
	private int count;
	private double frequency;
	
	public Word(String word, int count) {
		this.word = word;
		this.count = count;
		this.frequency = 0;
	}
	
	public Word(String word, double frequency) {
		this.word = word;
		this.count = 0;
		this.frequency = frequency;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getCount() {
		return count;
	}
	
	public double getFrequency() {
		return frequency;
	}
}
