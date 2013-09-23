package storage;

import java.util.Set;

public interface StorageOperations {
	public String uploadStorageFile(String sessionToken, String appId, String pathToFile);
	public String downloadStorageFile(String sessionToken, String appId, String storageId, String pathToSave);
	public String deleteStorageFile(String sessionToken, String appId, String storageId);
	public Set<String> findAllStorageIds(String sessionToken, String appId);
}
