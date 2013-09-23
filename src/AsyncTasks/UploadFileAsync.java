package AsyncTasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

public class UploadFileAsync extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		String url = params[0];
		String pathToFile = params[1];
		String appId = params[2];
		String sessionToken = params[3];
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost();
		HttpResponse response = null;
		httpPost.addHeader("Cookie", "sessionToken="+sessionToken);
		try {
			httpPost.setURI(new URI(url));
			httpPost.setHeader("Accept", "application/json");
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			File file = new File(pathToFile);
			if(!file.exists())
				return null;
			FileBody fileBody = new FileBody(file);
			entity.addPart("file", fileBody);
			httpPost.setEntity(entity);
			
			response = httpClient.execute(httpPost);
			HttpEntity result = response.getEntity();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return response.getStatusLine().toString();		
	}
	
}
