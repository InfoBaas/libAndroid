package sessions;

import Users.OpenbaasUser;

public interface SessionOperations {

	/**
	 * Creates a session for the given user, if sucessful returns a sessionToken that identifies that session. Each session is active for
	 * 24 hours, after that period and if no action was performed then the session is automatically deleted. 
	 * @param user
	 * @return
	 */
	public String createSession(OpenbaasUser user);
	/**
	 * Creates a session for the given user, if sucessful returns a sessionToken that identifies that session. Each session is active for
	 * 24 hours, after that period and if no action was performed then the session is automatically deleted.
	 * 
	 * Sends the device geolocation so that session is marked with the location.
	 * @param user
	 * @param latitude
	 * @param longitude
	 * @param userAgent
	 * @return
	 */
	public String createSessionWithGeolocation(OpenbaasUser user, float latitude, float longitude, String userAgent);
	/**
	 * Updates the given session and updates the location, if we perform a patch 10 hours after the session was created then that session will have 24 more hours 
	 * before inactivity deletion.
	 * @param user
	 * @param sessionToken
	 * @param latitude
	 * @param longitude
	 * @param userAgent
	 * @return
	 */
	public String patchSessionWithGeolocation(OpenbaasUser user, String sessionToken, float latitude, float longitude, String userAgent);	
	/**
	 * Deletes the given user session, a user can have more than one session active (one for PC, another for android, ect). This method
	 * only affects the specified session, the remaining are unaffected.
	 * @param user
	 * @param sessionToken
	 * @return
	 */
	public String deleteSession(OpenbaasUser user, String sessionToken);
	/**
	 * Updates the given session, if we perform a patch 10 hours after the session was created then that session will have 24 more hours 
	 * before inactivity deletion.
	 * @param user
	 * @param sessionToken
	 * @return
	 */
	public String patchSession(OpenbaasUser user, String sessionToken);
	/**
	 * Retrieves the given session fields.
	 * @param user
	 * @param sessionToken
	 * @return
	 */
	public String getSessionFields(OpenbaasUser user, String sessionToken);
}
