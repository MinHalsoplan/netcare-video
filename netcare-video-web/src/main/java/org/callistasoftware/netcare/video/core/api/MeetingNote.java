package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;

public interface MeetingNote extends Serializable {
	
	/**
	 * The id of this note
	 * @return
	 */
	Long getId();

	/**
	 * Get the note
	 * @return
	 */
	String getNote();
	
	/**
	 * The user who created the note
	 * @return
	 */
	User getCreatedBy();
	
	/**
	 * The date when the note was created
	 * @return
	 */
	String getCreatedAt();
}
