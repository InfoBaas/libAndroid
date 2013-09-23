package userData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;

import Users.OpenbaasUser;

import com.example.openbaas.Openbaas;

public class UserDataOperationsClass implements UserDataOperations{
	Openbaas openbaas = new Openbaas();
	String url = openbaas.getUrl();
	
	@Override
	public String createOrReplaceData(String sessionToken, OpenbaasUser user, JSONObject data, String path) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data/"+path;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPut request = new HttpPut();
			request.setURI(new URI(userDataUrl));
			StringEntity entity = new StringEntity(data.toString());
			request.setEntity(entity);
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
		case 200: // deleted ok
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
	@Override
	public String createOrReplaceDataWithGeolocation(String sessionToken, OpenbaasUser user,
			JSONObject data, float latitude, float longitude, String path) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data/"+path;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPut request = new HttpPut();
			request.setURI(new URI(userDataUrl));
			StringEntity entity = new StringEntity(data.toString());
			request.setEntity(entity);
			request.setHeader("Accept", "application/json");
			request.addHeader("Cookie", "sessionToken="+sessionToken);
			request.setHeader("location", latitude+":"+longitude);
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 201: // created ok
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
	@Override
	public String getAllElementsInDocument(String sessionToken, OpenbaasUser user) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data";
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpGet request = new HttpGet();
			request.setURI(new URI(userDataUrl));
			request.setHeader("Accept", "application/json");
			request.addHeader("Cookie", "sessionToken="+sessionToken);
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // created ok
			responseJson = response.getEntity().toString();;
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
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
	@Override
	public String getElementInDocument(String sessionToken, OpenbaasUser user, String path) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data/"+path;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpGet request = new HttpGet();
			request.setURI(new URI(userDataUrl));
			request.setHeader("Accept", "application/json");
			request.addHeader("Cookie", "sessionToken="+sessionToken);
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // created ok
			responseJson = response.getEntity().toString();
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
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
	@Override
	public String createDocumentRoot(String sessionToken, OpenbaasUser user, String json) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data";
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPut request = new HttpPut();
			request.setURI(new URI(userDataUrl));
			StringEntity entity = new StringEntity(json.toString());
			request.setEntity(entity);
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
		case 201: // created ok
			responseJson = response.getEntity().toString();
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
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
	@Override
	public String createDocumentRootWithGeolocation(String sessionToken, OpenbaasUser user,
			String json, float latitude, float longitude) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data";
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPut request = new HttpPut();
			request.setURI(new URI(userDataUrl));
			StringEntity entity = new StringEntity(json.toString());
			request.setEntity(entity);
			request.addHeader("Cookie", "sessionToken="+sessionToken);
			request.setHeader("location", latitude+":"+longitude);
			request.setHeader("Accept", "application/json");
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 201: // created ok
			responseJson = response.getEntity().toString();
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
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
	@Override
	public String deleteDataInElement(String sessionToken, OpenbaasUser user, String path) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data/"+path;
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
			responseJson = response.getEntity().toString();
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
	@Override
	public String createNonPublishableDocument(String sessionToken, OpenbaasUser user, String json,
			String path) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data/"+path;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPost request = new HttpPost();
			request.setURI(new URI(userDataUrl));
			StringEntity entity = new StringEntity(json.toString());
			request.setEntity(entity);
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
			responseJson = response.getEntity().toString();
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

	@Override
	public String getAllElementsInDocumentInsideRadius(String sessionToken, OpenbaasUser user,
			float latitude, float longitude, double radius) {
		String userDataUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/data";
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpGet request = new HttpGet();
			String finalURL = addLocationToUrl(userDataUrl, latitude, longitude, radius); //adds query params
			request.setURI(new URI(finalURL));
			request.setHeader("Accept", "application/json");
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 201: // created ok
			responseJson = null;
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
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
	
	protected String addLocationToUrl(String url, float latitude, float longitude, double radius){
	    if(!url.endsWith("?"))
	        url += "?";

	    List<NameValuePair> params = new LinkedList<NameValuePair>();

	    if (latitude != 0.0 && longitude != 0.0 && radius != 0.0){
	        params.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
	        params.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
	        params.add(new BasicNameValuePair("radius", String.valueOf(radius)));
	    }

	    String paramString = URLEncodedUtils.format(params, "utf-8");

	    url += paramString;
	    return url;
	}
}
