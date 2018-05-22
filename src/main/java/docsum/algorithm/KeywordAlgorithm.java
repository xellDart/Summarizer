package docsum.algorithm;

import java.util.List;

/**
 * Interface for keyword extraction algorithms.
 *
 */
public interface KeywordAlgorithm {
	
	/**
	 * Runs keyword algorithm on tokenized sentence list.
	 * 
	 * @param 	sentences	List of lists of strings.
	 * @return	List of keyword strings.
	 */
	List<String> getKeywords(List<List<String>> sentences);
}
