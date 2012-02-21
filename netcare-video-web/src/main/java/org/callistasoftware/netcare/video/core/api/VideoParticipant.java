package org.callistasoftware.netcare.video.core.api;

public interface VideoParticipant {

	/**
	 * Get the user
	 * @return
	 */
	User getUser();
	
	/**
	 * Get the stream
	 * @return
	 */
	String getStream();
	
	/**
	 * Whether the participant is connected or not
	 * @return
	 */
	boolean isConnected();
}
