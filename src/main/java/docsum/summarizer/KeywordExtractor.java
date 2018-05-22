package docsum.summarizer;

import docsum.algorithm.HITSAlgorithm;
import docsum.algorithm.KeywordAlgorithm;

import java.util.List;

/**
 * Extracts keywords from text string using specified algorithm.
 * 
 * @author Evan Dempsey
 */
public class KeywordExtractor {
	
	private SentenceSegmenter segmenter;
	private SentencePreprocessor preprocessor;
	private KeywordAlgorithm hits;

	/**
	 * Constructor for KeywordExtractor class.
	 * 
	 * @param segmenter		SentenceSegmenter instance.
	 * @param preprocessor	SentencePreprocessor instance.
	 */
	public KeywordExtractor(SentenceSegmenter segmenter, 
			SentencePreprocessor preprocessor) {
		this.segmenter = segmenter;
		this.preprocessor = preprocessor;
		hits = new HITSAlgorithm();
	}

	/**
	 * Extracts a list of keywords from the source text.
	 * 
	 * @param 	text	String of text from which to extract keywords.
	 * @return	Comma-separated list of top keywords.
	 */
	public String extract(String text) {
		
		List<List<String>> sentences = segmenter.segment(text);
		List<List<String>> processed = preprocessor.process(sentences);
		List<String> keywords = hits.getKeywords(processed);
		
		return makeKeywordString(keywords, 20);
	}

	/**
	 * Joins top k extracted keywords together
	 * into a comma-separated string.
	 * 
	 * @param 	keywords	List of keywords.
	 * @param 	k			Number of keywords to take.
	 * @return	Comma-separated string of keywords.
	 */
	private String makeKeywordString(List<String> keywords, int k) {
		
		// If fewer than k keywords are available,
		// take as many as possible.
		int max = keywords.size();
		if (k < max)
			max = k;
		
		StringBuilder joined = new StringBuilder();
		
		for (int i=0; i<max; i++) {
			joined.append(keywords.get(i));
			if (i < max-1)
				joined.append(", ");
		}
		
			return joined.toString();
	}
}
