package audio;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import AsyncTasks.FileDownloadAsync;
import AsyncTasks.UploadFileAsync;
import android.os.AsyncTask;

import com.example.openbaas.Openbaas;

public class AudioOperationsClass implements AudioOperations{

	Openbaas openbaas = new Openbaas();
	String url = openbaas.getUrl();
	
	@Override
	public String uploadAudio(String sessionToken, String appId,
			String pathToFile) {
		String audioUrl = url + "/" + appId + "/media/audio/";
		String [] params = {audioUrl, pathToFile, appId};
		AsyncTask<String, Void, String> fileUp = new UploadFileAsync().execute(params);
		String errorMesg = null;
		try {
			errorMesg = fileUp.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMesg;
	}

	@Override
	public String downloadAudio(String sessionToken, String appId,
			String audioId, String pathToSave) {
		String audioUrl = url + "/" + appId + "/media/images/"+audioId;
		String [] params = {audioUrl, pathToSave, appId};
		AsyncTask<String, Void, String> fileDown = new FileDownloadAsync().execute(params);
		String errorMesg = null;
		try {
			errorMesg = fileDown.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMesg;
	}

	@Override
	public Set<String> findAllAudioIds(String sessionToken, String appId) {
		String audioUrl = url + "/" + appId + "/media/audio";
		HttpClient client = new DefaultHttpClient();
		Set<String> ids = null;
		String errorMesg = null;
		HttpResponse response = null;
		try {
			HttpGet request = new HttpGet();
			request.setURI(new URI(audioUrl));
			request.addHeader("Cookie", "sessionToken="+sessionToken);
			request.setHeader("Accept", "application/json");
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // created ok
			ids = (Set<String>) response.getEntity();
			break;
		case 400:
			errorMesg = Openbaas.BAD_REQUEST;
			break;
		case 403:
			errorMesg = Openbaas.FORBIDDEN;
			break;
		default:
			errorMesg = response.getStatusLine().toString();
			break;
		}
		if(errorMesg != null){
			ids = new HashSet<String>();
			ids.add(errorMesg);
		}
		return ids;
	}

	@Override
	public String getAudioMetadata(String sessionToken, String appId,
			String audioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteAudio(String sessionToken, String appId, String audioId) {
		String userDataUrl = url + "/" + appId + "/images/"+audioId;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpDelete request = new HttpDelete();
			request.setURI(new URI(userDataUrl));
			request.addHeader("Cookie", "sessionToken="+sessionToken);
			request.setHeader("Accept", "application/json");
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // created ok
			responseJson = null;
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
			break;
		case 404:
			responseJson = Openbaas.NOT_FOUND;
			break;
		case 403:
			responseJson = Openbaas.FORBIDDEN;
			break;
		default:
			responseJson = response.getStatusLine().toString();
			break;
		}
		return responseJson;
	}

}
