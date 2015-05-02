package com.pik.agmarkiiitm;

import android.os.Bundle;

/**
 * Interface for speech recognition callbacks.
 * 
 * Essentially a cut-down version of {@link android.speech.RecognitionListener},
 * to avoid dependencies on Froyo and methods we don't need or can't provide.
 * 
 * 
 */
public interface RecognitionListener {
	/**
	 * Called on the recognition thread when partial results are available.
	 * 
	 * Note: This is not like android.speech.RecognitionListener in that it does
	 * not get called on the main thread.
	 * 
	 * @param b
	 *            Bundle containing the partial result string under the "hyp"
	 *            key.
	 */
	abstract void onPartialResults(Bundle c, int x);

	/**
	 * Called when final results are available.
	 * 
	 * Note: This is not like android.speech.RecognitionListener in that it does
	 * not get called on the main thread.
	 * 
	 * @param b
	 *            Bundle containing the final result string under the "hyp" key.
	 */
	abstract void onResults(Bundle c, int x);

	/**
	 * Called if a recognition error occurred.
	 * 
	 * Note: This will only ever be passed -1 for the moment, which corresponds
	 * to a recognition failure (null result).
	 * 
	 * @param err
	 *            Code representing the error that occurred.
	 */
	abstract void onError(int err,  int x);	
}
