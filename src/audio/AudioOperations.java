package audio;

import java.util.Set;

public interface AudioOperations {

	public String uploadAudio(String sessionToken, String appId, String pathToFile);
	public String downloadAudio(String sessionToken, String appId, String audioId, String pathToSave);
	public Set<String> findAllAudioIds(String sessionToken, String appId);
	public String getAudioMetadata(String sessionToken, String appId, String audioId);
	public String deleteAudio(String sessionToken, String appId, String audioId);
}
