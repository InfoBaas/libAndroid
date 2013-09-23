package video;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;


import AsyncTasks.FileDownloadAsync;
import AsyncTasks.UploadFileAsync;
import android.os.AsyncTask;

import com.example.openbaas.Openbaas;

public class VideoOperationsClass implements VideoOperations{

    Openbaas openbaas = new Openbaas();
    String url = openbaas.getUrl();
    
    @Override
    public String downloadVideo(String sessionToken, String appId, String videoId, String pathToSave) {
        String videoUrl = url + "/" + appId + "/media/video/"+videoId;
        String [] params = {videoUrl, pathToSave, appId};
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
    public String uploadVideo(String sessionToken, String appId, String pathToFile, String geoLocation) {
        String videoUrl = url + "/" + appId + "/media/video/";
        String [] params = {videoUrl, pathToFile, appId};
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
    public String deleteVideo(String sessionToken, String appId, String videoId) {
        String usersUrl = url + "/" + appId + "/media/video/" + "/" + videoId;
        HttpClient client = new DefaultHttpClient();
        String responseJson = null;
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
    public Map<String, String> getVideoMetadata(String sessionToken, String appId, String videoId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getAllVideoIds(String sessionToken, String appId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String uploadVideo(String sessionToken, String appId, String filePath) {
        String imageUrl = url + "/" + appId + "/storage";
        String [] params = {imageUrl, filePath, appId};
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

}
