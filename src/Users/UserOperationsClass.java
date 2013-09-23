package Users;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.openbaas.Openbaas;

public class UserOperationsClass implements UserOperations {
	Openbaas openbaas = new Openbaas();
	String url = openbaas.getUrl();
	String responseJson = null;
	@Override
	public String deleteUser(final String sessionToken, final OpenbaasUser user, final String appId) {
		Thread thread = new Thread()
		{
		    @Override
		    public void run() {
		    	boolean keepRunning = true;
		    	String responseJsonThread = null;
		        while(keepRunning) {
		        	String usersUrl = url + "/" + appId + "/users" + "/" + user.getUserId();
		    		HttpClient client = new DefaultHttpClient();
		    		boolean found = false;
		    		HttpResponse response = null;
		    		try {
		    			HttpDelete request = new HttpDelete();
		    			request.addHeader("Cookie", "sessionToken="+sessionToken);
		    			request.setURI(new URI(usersUrl));
		    			request.setHeader("Accept", "application/json");
		    			response = client.execute(request);
		    		} catch (IOException e) {
		    			found = true;
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
		        	keepRunning = false;
		        	setResponse(responseJsonThread);
				}
		    }
		};
		thread.start();
		
		return responseJson;
	}

	@Override
	public String updateUser(final String sessionToken, final OpenbaasUser user, final String appId) {
		Thread thread = new Thread()
		{
		    @Override
		    public void run() {
		    	boolean keepRunning = true;
		    	String responseJsonThread = null;
		        while(keepRunning) {
		        	String usersUrl = url + "/" + appId + "/users/" + user.getUserId();
		    		HttpClient client = new DefaultHttpClient();
		    		boolean found = false;
		    		HttpResponse response = null;
		    		try {
		    			JSONObject json = new JSONObject();
		    			if(user.getEmail() != null && user.getPassword() != null){
		    				json.put("email", user.getEmail());
		    				json.put("password", user.getPassword());
		    				json.put("alive", user.getAlive());	
		    			}else if(user.getEmail() != null){
		    				json.put("email", user.getEmail());
		    				json.put("alive", user.getAlive());	
		    			}
		    			StringEntity entity = new StringEntity(json.toString());
		    			HttpPatch request = new HttpPatch();
		    			request.addHeader("Cookie", "sessionToken="+sessionToken);
		    			request.setURI(new URI(usersUrl));
		    			request.setHeader("Accept", "application/json");
		    			request.addHeader("content-type", "application/json");
		    			request.setEntity(entity);
		    			response = client.execute(request);
		    		} catch (URISyntaxException e) {
		    			e.printStackTrace();
		    		} catch (ClientProtocolException e) {
		    			found = true;
		    		} catch (IOException e) {
		    			found = true;
		    		} catch (JSONException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    		if (!found)
		    			switch (response.getStatusLine().getStatusCode()) {
		    			case 200: // created
		    				responseJsonThread = null;
		    				break;
		    			case 400:
		    				responseJsonThread = Openbaas.BAD_REQUEST;
		    				break;
		    			case 404:
		    				responseJsonThread = Openbaas.NOT_FOUND;
		    				break;
		    			default:
		    				responseJsonThread = response.getStatusLine().toString();
		    				break;
		    			}
		    		if (found)
		    			responseJsonThread = Openbaas.FOUND;
		        	keepRunning = false;
		        	setResponse(responseJsonThread);
				}
		    }
		};
		thread.start();
		return responseJson;
	}

	public JSONObject createJsonUser(String userName, String email,
			String password, String appId) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("userName", userName);
		json.put("email", email);
		json.put("password", password);
		json.put("appId", appId);
		return json;
	}

	@Override
	public String createUser(final String sessionToken, final OpenbaasUser user, final String appId) {
		Thread thread = new Thread()
		{
		    @Override
		    public void run() {
		    	String responseJson = null;
		    	boolean keepRunning = true;
		        while(keepRunning) {
		        	String usersUrl = url + "/" + appId + "/users";
		    		HttpClient client = new DefaultHttpClient();
		    		boolean found = false;
		    		HttpResponse response = null;
		    		try {
		    			JSONObject json = createJsonUser(user.getUserName(),
		    					user.getEmail(), user.getPassword(), user.getAppId());
		    			StringEntity entity = new StringEntity(json.toString());
		    			HttpPost request = new HttpPost();
		    			request.addHeader("Cookie", "sessionToken="+sessionToken);
		    			request.setURI(new URI(usersUrl));
		    			request.setHeader("Accept", "application/json");
		    			request.addHeader("content-type", "application/json");
		    			request.setEntity(entity);
		    			response = client.execute(request);
		    		} catch (URISyntaxException e) {
		    			e.printStackTrace();
		    		} catch (ClientProtocolException e) {
		    			found = true;
		    		} catch (IOException e) {
		    			found = true;
		    		} catch (JSONException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    		if (!found)
		    			switch (response.getStatusLine().getStatusCode()) {
		    			case 201: // created
		    				responseJson = null;
		    				break;
		    			case 400:
		    				responseJson = Openbaas.BAD_REQUEST;
		    				break;
		    			case 404:
		    				responseJson = Openbaas.NOT_FOUND;
		    				break;
		    			default:
		    				responseJson = response.getStatusLine().toString();
		    				break;
		    			}
		    		if (found)
		    			responseJson = Openbaas.FOUND;
		    		setResponse(responseJson);
		    		keepRunning = false;
				}
		    }
		};
		thread.start();
		return responseJson;
	}
	private void setResponse(String value){
		this.responseJson = value;
	}
	@Override
	public String getUserIdUsingUserName(String sessionToken, OpenbaasUser user, String appId) {
		return null;
	}

}
