package info.fshi.oppnetdemo1.account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AccountManager {
	
	private String url = "https://accounts.organicity.eu/realms/organicity/protocol/openid-connect/auth/?client_id=oppnet-dev&redirect_uri=oppnet%3A%2F%2Ftoken&response_type=token";
	
	private static AccountManager _obj = null;
	
	private String token;
	
	private int state;
	
	public final static int STATE_NOT_AUTHORISED = 0;
	public final static int STATE_AUTHORISED = 1;
	public final static int STATE_TOKEN_EXPIRED = 2;
	
	private AccountManager(){
		state = STATE_NOT_AUTHORISED;
	}
	
	public static AccountManager getManager(){
		if(_obj == null){
			_obj = new AccountManager();
		}
		return _obj;
	}
	
	public void login(Context context){
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
	}

	public void storeToken(String token){
		this.token = token;
		state = STATE_AUTHORISED;
	}
	
	public String getToken(){
		return this.token;
	}
	
	public int getState(){
		return state;
	}
}
