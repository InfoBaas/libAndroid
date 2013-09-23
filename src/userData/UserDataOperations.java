package userData;

import org.json.simple.JSONObject;

import Users.OpenbaasUser;


public interface UserDataOperations {

	public String createOrReplaceData(String sessionToken, OpenbaasUser user, JSONObject data, String path);
	public String createOrReplaceDataWithGeolocation(String sessionToken, OpenbaasUser user, JSONObject data, float latitude, float longitude, String path);
	public String getAllElementsInDocument(String sessionToken, OpenbaasUser user);
	public String getAllElementsInDocumentInsideRadius(String sessionToken, OpenbaasUser user, float latitude, float longitude, double radius);
	public String getElementInDocument(String sessionToken, OpenbaasUser user, String path);
	public String createDocumentRoot(String sessionToken, OpenbaasUser user, String json);
	public String createDocumentRootWithGeolocation(String sessionToken, OpenbaasUser user, String json, float latitude, float longitude);
	public String deleteDataInElement(String sessionToken, OpenbaasUser user, String path);
	public String createNonPublishableDocument(String sessionToken, OpenbaasUser user, String json, String path);
}
