package video;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface VideoOperations {

	public String downloadVideo(String sessionToken, String appId, String videoId, String pathToSave);
	public String uploadVideo(String sessionToken, String appId, String fileDirectory, String geoLocation);
	public String uploadVideo(String sessionToken, String appId, String fileDirectory);
	public String deleteVideo(String sessionToken, String appId, String videoId);
	public Map<String,String> getVideoMetadata(String sessionToken, String appId, String videoId);
	public List<String> getAllVideoIds(String sessionToken, String appId);
	
}
