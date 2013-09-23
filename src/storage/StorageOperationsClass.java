package storage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import AsyncTasks.FileDownloadAsync;
import AsyncTasks.UploadFileAsync;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.openbaas.Openbaas;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;

public class StorageOperationsClass implements StorageOperations {

	Openbaas openbaas = new Openbaas();
	String url = openbaas.getUrl();
	String responseJson = null;
	
	@Override
	public String uploadStorageFile(String sessionToken, String appId,
			String pathToFile) {
		String storageUrl = url + "/" + appId + "/storage";
		String[] params = { storageUrl, pathToFile, appId, sessionToken };
		AsyncTask<String, Void, String> fileUp = new UploadFileAsync()
				.execute(params);
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
	public String downloadStorageFile(String sessionToken, String appId,
			String storageId, String pathToSave) {
		String storageUrl = url + "/" + appId + "/storage/" + storageId;
		String[] params = { storageUrl, pathToSave, appId, storageId };
		AsyncTask<String, Void, String> fileDown = new FileDownloadAsync()
				.execute(params);
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
	public String deleteStorageFile(final String sessionToken,
			final String appId, final String storageId) {
		Thread thread = new Thread()
		{
		    @Override
		    public void run() {
		    	String responseJsonThread = null;
		    	boolean keepRunning = true;
		        while(keepRunning) {
		        	String usersUrl = url + "/" + appId + "/storage" + "/"
							+ storageId;
					HttpClient client = new DefaultHttpClient();
					HttpResponse response = null;
					try {
						HttpDelete request = new HttpDelete();
						request.addHeader("Cookie", "sessionToken="
								+ sessionToken);
						request.setURI(new URI(usersUrl));
						request.setHeader("Accept", "application/json");
						response = client.execute(request);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (response.getStatusLine().getStatusCode()) {
					case 200: // deleted ok
						responseJsonThread = response.getEntity().toString();
						break;
					case 400:
						responseJsonThread = Openbaas.BAD_REQUEST;
						break;
					case 404:
						responseJsonThread = Openbaas.NOT_FOUND;
						break;
					case 403:
						responseJsonThread = Openbaas.FORBIDDEN;
						break;
					default:
						responseJsonThread = response.getStatusLine().toString();
						break;
					}
					setResponse(responseJsonThread);
			        keepRunning = false;
				}
		    }
		};
		thread.start();
		return responseJson;
	}

	private void setResponse(String value) {
		this.responseJson = value;
	}

	@Override
	public Set<String> findAllStorageIds(final String sessionToken,
			final String appId) {
		Set<String> ids = new HashSet<String>();
		Thread thread = new Thread() {
			@Override
			public void run() {
				boolean keepRunning = true;
				while (keepRunning) {
					String storageUrl = url + "/" + appId + "/storage";
					HttpClient client = new DefaultHttpClient();
					Set<String> ids = null;
					String errorMesg = null;
					HttpResponse response = null;
					try {
						HttpGet request = new HttpGet();
						request.setURI(new URI(storageUrl));
						request.addHeader("Cookie", "sessionToken="
								+ sessionToken);
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
						HttpEntity ent = response.getEntity();
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
					if (errorMesg != null) {
						ids = new HashSet<String>();
						ids.add(errorMesg);
					}
					keepRunning = false;
				}
			}
		};
		thread.start();
		return ids;
	}
}
