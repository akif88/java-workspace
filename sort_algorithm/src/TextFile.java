import java.util.Comparator;

public class TextFile{
	
	private String word;
	private long weight;
	
	public TextFile(){}
	
	public TextFile(String text)
	{			
		try
		{	//split by the weight and words.
			String[] info = text.split("\t", 2);
			setWord(info[1]);
			setWeight(Long.parseLong(info[0]));			
		
		}catch(IllegalArgumentException e){
			//System.out.println(e);
		}
		catch(NullPointerException e){
			//System.out.println(e);
		}
				
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		if((this.word = word).isEmpty() || (this.word = word) == null )
			throw new NullPointerException("Null String is not valid!!!");
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		if((this.weight = weight) < 0)
			throw new IllegalArgumentException("Negative weight is not valid!!!");
	}

	
	//Compare incoming results by words.
	public static class wordsOrder implements Comparator<TextFile>{

		@Override
		public int compare(TextFile w1, TextFile w2) {
			return w1.word.compareToIgnoreCase(w2.word);
		}		
		
	}
	////Compare incoming results by weight.
	public static class weightOrder implements Comparator<TextFile>{

		@Override
		public int compare(TextFile w1, TextFile w2) {
			if(w1.weight < w2.weight)
				return -1;
			else if(w1.weight > w2.weight)
				return 1;
			else							
				return 0; 
		}
		
	}
	
}
