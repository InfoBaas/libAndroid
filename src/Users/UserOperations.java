package Users;

public interface UserOperations {
	public String deleteUser(String sessionToken, OpenbaasUser user, String appId);
	public String updateUser(String sessionToken, OpenbaasUser user, String appId);
	public String createUser(String sessionToken, OpenbaasUser user, String appId);
	public String getUserIdUsingUserName(String sessionToken, OpenbaasUser user, String appId);
}