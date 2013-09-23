package sessions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import Users.OpenbaasUser;

import com.example.openbaas.Openbaas;

public class SessionOperationsClass implements SessionOperations{
	
	Openbaas openbaas = new Openbaas();
	String url = openbaas.getUrl();
	
	@Override
	public String createSession(OpenbaasUser user) {
		String sessionsUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/sessions";
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			JSONObject json = new JSONObject();
			json.put("userName", user.getUserName());
			json.put("password", user.getPassword());
			HttpPost request = new HttpPost();
			StringEntity entity = new StringEntity(json.toString());
			request.setURI(new URI(sessionsUrl));
			request.setEntity(entity);
			request.setHeader("Accept", "application/json");
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // deleted ok
			responseJson = response.getEntity().toString();
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
			break;
		case 404:
			responseJson = Openbaas.NOT_FOUND;
			break;
		case 302:
			responseJson = Openbaas.FOUND;
			break;
		case 403:
			responseJson = Openbaas.FORBIDDEN;
			break;
		case 402:
			responseJson = Openbaas.UNAUTHORIZED;
			break;
		default:
			responseJson = response.getStatusLine().toString();
			break;
		}
		return responseJson;
	}
	@Override
	public String deleteSession(OpenbaasUser user, String sessionToken) {
		String sessionsUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/sessions/"+sessionToken;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpDelete request = new HttpDelete();
			request.setURI(new URI(sessionsUrl));
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
			responseJson = response.getEntity().toString();
			break;
		case 404:
			responseJson = Openbaas.NOT_FOUND;
			break;
		case 302:
			responseJson = Openbaas.FOUND;
			break;
		default:
			responseJson = response.getStatusLine().toString();
			break;
		}
		return responseJson;
	}

	@Override
	public String patchSession(OpenbaasUser user, String sessionToken) {
		String sessionsUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/sessions/"+sessionToken;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPatch request = new HttpPatch();
			request.setURI(new URI(sessionsUrl));
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
	public String createSessionWithGeolocation(OpenbaasUser user,
			float latitude, float longitude, String userAgent) {
		String sessionsUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/sessions";
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			JSONObject json = new JSONObject();
			json.put("userName", user.getUserName());
			json.put("password", user.getPassword());
			json.put("location", latitude+":"+longitude);
			HttpPost request = new HttpPost();
			StringEntity entity = new StringEntity(json.toString());
			request.setURI(new URI(sessionsUrl));
			request.setEntity(entity);
			request.setHeader("Accept", "application/json");
			request.setHeader("user-agent", userAgent);
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // deleted ok
			responseJson = response.getEntity().toString();
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
			break;
		case 404:
			responseJson = Openbaas.NOT_FOUND;
			break;
		case 302:
			responseJson = Openbaas.FOUND;
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
	public String patchSessionWithGeolocation(OpenbaasUser user, String sessionToken,
			float latitude, float longitude, String userAgent) {
		String sessionsUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/sessions/"+sessionToken;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			JSONObject json = new JSONObject();
			json.put("location", latitude+":"+longitude);
			HttpPatch request = new HttpPatch();
			request.setURI(new URI(sessionsUrl));
			StringEntity entity = new StringEntity(json.toString());
			request.setEntity(entity);
			request.setHeader("Accept", "application/json");
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (response.getStatusLine().getStatusCode()) {
		case 200: // deleted ok
			responseJson = response.getEntity().toString();
			break;
		case 400:
			responseJson = Openbaas.BAD_REQUEST;
			break;
		case 404:
			responseJson = Openbaas.NOT_FOUND;
			break;
		case 302:
			responseJson = Openbaas.FOUND;
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
	public String getSessionFields(OpenbaasUser user, String sessionToken) {
		String sessionsUrl = url + "/" + user.getAppId() + "/users" + "/" + user.getUserId()+"/sessions/"+sessionToken;
		HttpClient client = new DefaultHttpClient();
		String responseJson = null;
		HttpResponse response = null;
		try {
			HttpPatch request = new HttpPatch();
			request.setURI(new URI(sessionsUrl));
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
			responseJson = response.getEntity().toString();
			break;
		case 302:
			responseJson = Openbaas.FOUND;
			break;
		case 403:
			responseJson = Openbaas.FORBIDDEN;
			break;
		default:
			responseJson = response.getStatusLine().toString();
		}
		return responseJson;
	}
}
