package images;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import AsyncTasks.FileDownloadAsync;
import AsyncTasks.UploadFileAsync;
import android.os.AsyncTask;

import com.example.openbaas.Openbaas;

public class ImageOperationsClass implements ImageOperations {

	Openbaas openbaas = new Openbaas();
	String url = openbaas.getUrl();
	
	@Override
	public String uploadImage(String sessionToken, String appId, String pathToFile) {
		String imageUrl = url + "/" + appId + "/media/images/";
		String [] params = {imageUrl, pathToFile, appId, sessionToken};
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
	public String downloadImage(String sessionToken, String appId, String imageId, String pathToSave) {
		String storageUrl = url + "/" + appId + "/media/images/"+imageId;
		String [] params = {storageUrl, pathToSave, appId};
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
	public String findAllImageIds(String sessionToken, String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageMetadata(String sessionToken, String appId,
			String imageId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String deleteImage(String sessionToken, String appId, String imageId) {
		String userDataUrl = url + "/" + appId + "/images/"+imageId;
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
