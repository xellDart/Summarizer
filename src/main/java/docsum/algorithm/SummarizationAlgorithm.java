package docsum.algorithm;

import java.util.List;

/**
 * Interface for single document summarization algorithms.
 *
 */
public interface SummarizationAlgorithm {

	/**
	 * Summarizes pre-tokenized document.
	 * 
	 * @param 	sentences	List of tokenized sentences.
	 * @param 	percentage	Percentage of original sentences to use in summary.
	 * @return	List of indices of sentences included in summary.
	 */
	List<Integer> getSelection(List<List<String>> sentences, int percentage);
}
