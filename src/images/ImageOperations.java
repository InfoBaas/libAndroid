package images;

public interface ImageOperations {
	public String uploadImage(String sessionToken, String appId, String pathToFile);
	public String downloadImage(String sessionToken, String appId, String imageId, String pathToSave);
	public String findAllImageIds(String sessionToken, String appId);
	public String getImageMetadata(String sessionToken, String appId, String imageId);
	public String deleteImage(String sessionToken, String appId, String imageId);
}
