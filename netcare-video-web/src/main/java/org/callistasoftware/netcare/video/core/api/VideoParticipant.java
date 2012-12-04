package org.callistasoftware.netcare.video.core.api;

import org.callistasoftware.netcare.video.core.api.impl.VideoParticipantImpl;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as=VideoParticipantImpl.class)
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
