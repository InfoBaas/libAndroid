package Users;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class OpenbaasUser implements Map {

	String userName;
	String email;
	String password;
	String appId;
	String userId;
	String alive;
	
	public OpenbaasUser(String appId, String userName, String email, String password){
		this.appId = appId;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	public OpenbaasUser(){
		
	}
	public String getAlive() {
		return this.alive;
	}
	public void setAlive(boolean alive) {
		if(alive)
			this.alive = "1";
		else
			this.alive = "0";
	}
	public String getUserId(){
		return this.userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object put(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void putAll(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}
}
