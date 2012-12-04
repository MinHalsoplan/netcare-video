package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.springframework.security.core.userdetails.UserDetails;

@JsonTypeInfo(use=Id.NAME, include=As.PROPERTY, property="valueType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=CareGiver.class),
	@JsonSubTypes.Type(value=Patient.class)
})
public interface User extends Serializable, UserDetails {

	/**
	 * Get the id of the user
	 * @return
	 */
	Long getId();
	
	/**
	 * Get the user's username
	 * @return
	 */
	String getName();
	
	/**
	 * Get the user's email address
	 */
	String getEmail();
	
	/**
	 * Whether the user is a care giver or not
	 * @return
	 */
	boolean isCareGiver();
}
