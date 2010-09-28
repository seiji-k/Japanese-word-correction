package swing.sample;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
import java.text.*;
import org.chasen.mecab.MeCab;
import org.chasen.mecab.Tagger;
import org.chasen.mecab.Node;


class CheckFrequency{

	HashMap<String, Double> ngram_freq_map = new HashMap<String, Double>();


	String filename = "/home/seiji-k/output/xx03out.txt";
	int r = 1;
	int n = 2;
	double threshold = -10;

	public void setThreshold(double t){
		threshold = t;
	}
	public double getThreshold(){
		return threshold;
	}


	public void setFilename(String f){
		filename = f;
	}

	public void loadFile() throws IOException{
		System.out.println("in loadFile function");
		FileReader fr = new FileReader(filename);
		System.out.println("in loadFile function2");
		BufferedReader br = new BufferedReader(fr);
		System.out.println("in loadFile function3");
		String tmp_str;
		System.out.println("loading begin");
		while((tmp_str = br.readLine()) != null){
			String s[] = tmp_str.split("	");
			String words = s[1];
			double freq = Double.valueOf(s[0]);
			ngram_freq_map.put(words, freq);
			System.out.println(words + freq); 
		}
		System.out.println("loading end");
	}


	public String trim(String s){
		String regex = "\\p{Punct}";
		s = s.replaceAll(regex, "");
		regex = "[\u2010-\u301F]|[\uFF01-\uFF0F]|[\uFF1A-\uFF1F]|" +
		"[\uFF3B-\uFF40]|[\uFF5B-\uFFE5]";
		s = s.replaceAll(regex, "");
		regex = "\\p{Cntrl}";
		s = s.replaceAll(regex, "");
		s.trim();
		s = s.toLowerCase();

		return s;
	}	

	public double getNgramFrequency(List<String>wakati, int i){
		String first = trim((String)wakati.get(i-1));
		String second =  trim((String)wakati.get(i));
		String third =  trim((String)wakati.get(i+1));
		/*
		if ("".equals(first)){
			first = trim((String)wakati.get(i+1));
			second =  trim((String)wakati.get(i+2));
			third =  trim((String)wakati.get(i+3));
		}
		if ("".equals(second)){
			second =  trim((String)wakati.get(i+2));
			third =  trim((String)wakati.get(i+3));
		}
		if ("".equals(third)){
			third =  trim((String)wakati.get(i+3));
		}
		 */		
		String ngram = first + " " + second + " " + third;
		//System.out.println(ngram);

		if(ngram_freq_map.containsKey(ngram)){
			double freq = ngram_freq_map.get(ngram);
			return freq;
		}
		return -99;	
	}

	public double getFrequencyByWord(List<String>wakati, String first, String second, String third){
		String ngram = first + " " + second + " " + third;
		if(ngram_freq_map.containsKey(ngram)){
			double freq = ngram_freq_map.get(ngram);
			return freq;
		}
		return -99;	
	}

	public double culcTwoBigramFreq(double first, double second){
		double log_frequency  =	Math.log(first) + Math.log(second);
		return log_frequency;
	}

	public String padding(String s, int l){

		for ( int i = 0; s.length() < l; ++i){
			s += "　";	
		}
		return s;
	}

	public ArrayList<String> kakujoshiFreq(ArrayList<String> wakati){

		ArrayList<String> words_and_freq = new ArrayList<String>();
		String[] kakujoshi = {"が","の","を","に","へ","と","から","より","で"}; 
		for(int i = 0; i < wakati.size(); ++i){ 
			int counter = 0;
			String current_word = (String)wakati.get(i);
			//System.out.println(current_word);

			if(i == 0){
				words_and_freq.add(current_word + "," + "0");
				continue;
			}
			for(int k = 0; k < kakujoshi.length; ++k){
				if(kakujoshi[k].equals(current_word)){
					//double pre_curr_freq = getNgramFrequency(wakati, i-1);
					//double curr_next_freq = getNgramFrequency(wakati, i);
					//double log_freq = culcTwoNgramFreq(pre_curr_freq, curr_next_freq);
					double frequency = getNgramFrequency(wakati, i);
					if(frequency < threshold){
						++counter;
						words_and_freq.add(current_word + "," + "1");
					}
				}
			}
			if (counter == 0){
				words_and_freq.add(current_word + "," + "0");

			}
		}
		return words_and_freq;
	}

}
class Mecab {


	static {
		try {
			//System.out.println(System.getProperty("java.library.path"));
			System.loadLibrary("MeCab");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
			System.exit(1);
		}
	}
	String filename = "kokoro";

	public void setFilename(String f){
		filename = f;
	}

	public ArrayList<String> StringTokenizer(String s) throws IOException{
		//	System.out.println(MeCab.VERSION);
		Tagger tagger = new Tagger("-Owakati");	
		//System.out.println(s);
		//System.out.println("hi");

		ArrayList<String> wakati = new ArrayList<String>(); 
		String sentence = tagger.parse(s);

		//System.out.println(sentence + "-------------------------------------------------------------");
		String[] sentence_w = sentence.split(" ");
		for (int i = 0; i < sentence_w.length; ++i){
			wakati.add(sentence_w[i]);

		}
		return wakati;
	}
}  

public class kakujoshicheck {
	CheckFrequency cf = new CheckFrequency();
	Mecab mec = new Mecab();
	ArrayList tokensList = new ArrayList();
	public kakujoshicheck(){}
	public void setThreshold(double t){
		cf.setThreshold	(t);
	}
	public double getThreshold(){
		return cf.getThreshold();
	}
	public ArrayList<String> check(String s) throws IOException{
		//mec.setFilename(s);
		//	System.out.println(s);
		System.out.println("check start.");
		cf.setThreshold(-1);
		System.out.println("loading start.");
		cf.loadFile();
		System.out.println("loading done.");
		return cf.kakujoshiFreq(mec.StringTokenizer(s));
	}
	public ArrayList<String> Reload(String s)throws IOException{
		System.out.println(getThreshold());
		return cf.kakujoshiFreq(mec.StringTokenizer(s));

	}
}