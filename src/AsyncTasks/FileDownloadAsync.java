package AsyncTasks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class FileDownloadAsync extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		String fileUrl = params[0];
		String pathToSave = params[1];
		String appId = params[2];
		String fileId = params[3];
		URL url;
		try {
			url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			connection.connect();
			int lenghtOfFile = connection.getContentLength();
			String type = connection.getHeaderField("fileType");
			String path;
			if(type != null)
				path = pathToSave+"/"+fileId+"."+type;
			else
				 path = pathToSave+"/"+fileId+".";
			InputStream input = new BufferedInputStream(url.openStream());
	        OutputStream output = new FileOutputStream(path);
	        byte data[] = new byte[1024];
	        long total = 0;
	        int count = 0;
	        while ((count = input.read(data)) != -1) {
	            total += count;
	            output.write(data, 0, count);
	        }
	        output.flush();
	        output.close();
	        input.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
