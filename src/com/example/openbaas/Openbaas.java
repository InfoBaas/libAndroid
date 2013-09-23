package com.example.openbaas;


import images.ImageOperations;
import images.ImageOperationsClass;

import java.util.Set;

import org.json.simple.JSONObject;

import sessions.SessionOperations;
import sessions.SessionOperationsClass;
import storage.StorageOperations;
import storage.StorageOperationsClass;
import userData.UserDataOperations;
import userData.UserDataOperationsClass;
import video.VideoOperations;
import video.VideoOperationsClass;
import Users.OpenbaasUser;
import Users.UserOperations;
import Users.UserOperationsClass;
import android.content.Context;
/*
 * Developer Notes:
 * In order to use Openbaas Library you must do the following:
 * - Start Openbaas Tomcat server 
 * - if the server is in another machine do the following:
 * 	- Get the ip of the machine the server is on
 * 	- Correct the url variable in this class with the correct IP
 * 	- Run Virtual Router Manager
 * 	- Shared connection:local area connection 
 * 	- Connect the smartphone to the wireless network
 * 	- Run the app
 * 	- Perform operations and check that the server is receiving them
 */
public class Openbaas {
	public String url = "http://10.0.3.37:8080/Openbaas/apps";
	
	//Error messages
	public static final String tempSessionToken = "~session";
	public static final String BAD_REQUEST = "400-Invalid request";
	public static final String FOUND = "302-User already exists";
	public static final String NOT_FOUND = "404-Resource does not exist";
	public static final String FORBIDDEN = "403-Forbidden";
	public static final String UNAUTHORIZED = "401-Unauthorized";
	private static String appId = null;
//	public static final String 
	
	public Openbaas(){
		
	}
	public Openbaas(Context context) {
		
	}
	public static Set<String> findAllStorageIds(){
		StorageOperations storageOp = new StorageOperationsClass();
		return storageOp.findAllStorageIds(tempSessionToken, appId);
	}
	public static String createSession(String userName, String password){
		SessionOperations sessionOps = new SessionOperationsClass();
		OpenbaasUser user = new OpenbaasUser();
		user.setUserName(userName);
		user.setPassword(password);
		user.setAppId(appId);
		sessionOps.createSession(user);
		return appId;
	}
	public void setAppId(String appId){
		Openbaas.appId = appId;
	}
	public String getAppId(){
		return appId;
	}
	public String createUser(OpenbaasUser user) {
		UserOperations userOps = new UserOperationsClass();
		String op = userOps.createUser(tempSessionToken, user, appId);
		return op;
	}
	public String updateUser(OpenbaasUser user){
		UserOperations userOps = new UserOperationsClass();
		String op = userOps.updateUser(tempSessionToken, user, appId);
		return op;
	}
	public String deleteUser(OpenbaasUser user){
		UserOperations userOps = new UserOperationsClass();
		String op = userOps.deleteUser(tempSessionToken, user, appId);
		return op;
	}
	public String createUserDataWithUserId(JSONObject data, OpenbaasUser user, String path){
		UserDataOperations userData = new UserDataOperationsClass();
		JSONObject json = new JSONObject();
		json.put("data", data);
		user.setAppId(appId);
		return userData.createOrReplaceData(tempSessionToken, user, json, path);
	}
	public String createUserDataWithUserName(JSONObject data,
			String userId){
		UserDataOperations userData = new UserDataOperationsClass();
		UserOperations userOp = new UserOperationsClass();
		OpenbaasUser user = new OpenbaasUser();
		user.setAppId(appId);
		user.setUserId(userId);
		JSONObject json = new JSONObject();
		json.put("data", data);
		return userData.createDocumentRoot(tempSessionToken, user, json.toString());
	}
	public String getUrl(){
		return this.url;
	}
	public String uploadStorageFile(String pathToFile){
		StorageOperations storageOp = new StorageOperationsClass();
		return storageOp.uploadStorageFile(tempSessionToken, appId, pathToFile);
	}
	public String downloadStorageFile(String storageId, String pathToSave){
		StorageOperations storageOp = new StorageOperationsClass();
		return storageOp.downloadStorageFile(tempSessionToken, appId, storageId, pathToSave);
	}
	public String deleteStorageFile(String storageId){
		StorageOperations storageOp = new StorageOperationsClass();
		return storageOp.deleteStorageFile(tempSessionToken, appId, storageId);
	}
	public String downloadImage(String imageId, String pathToSave){
		ImageOperations imageOp = new ImageOperationsClass();
		return imageOp.downloadImage(tempSessionToken, appId, imageId, pathToSave);
	}
	public String uploadImage(String pathToFile){
		ImageOperations imageOp = new ImageOperationsClass();
		return imageOp.uploadImage(tempSessionToken, appId, pathToFile);
	}
	public String downloadVideo(String videoId, String pathToSave){
		VideoOperations videoOp = new VideoOperationsClass();
		return videoOp.downloadVideo(tempSessionToken, appId, videoId, pathToSave);
	}
	public String uploadVideo(String pathToFile){
		VideoOperations videoOp = new VideoOperationsClass();
		return videoOp.uploadVideo(tempSessionToken, appId, pathToFile);
	}
	public String uploadVideoWithGeoLocation(String pathToFile){
		VideoOperations videoOp = new VideoOperationsClass();
		return videoOp.uploadVideo(tempSessionToken, appId, pathToFile);
	}
	
}
