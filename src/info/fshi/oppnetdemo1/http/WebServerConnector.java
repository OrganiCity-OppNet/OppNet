package info.fshi.oppnetdemo1.http;

import info.fshi.oppnetdemo1.data.DataManager;
import info.fshi.oppnetdemo1.data.QueueManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;

public class WebServerConnector extends BroadcastReceiver{

	private final String ENTITY_URI = "urn:oc:entity:london:oppnet:";

	private final static String TAG = "web server connector";
	private static String serverUrl = "http://146.169.24.58:10226/v1/contextEntities/";

	private static Context mContext = null;

	private static long updatedTime;

	public WebServerConnector(Context context){
		mContext = context;
		updatedTime = System.currentTimeMillis();
		new RegisterDeviceTask().execute();
	}

	public WebServerConnector(){}

	public void registerDevice(){

	}

	public void sendSensorData(String data){
		new SendSensorDataTask().execute(data);
	}

	private static boolean serverAvailable = true;
	
	private class SendSensorDataTask extends AsyncTask<String, Void, Void> {
		protected Void doInBackground(String... data) {

			String entityId = ENTITY_URI + "data:" + data[2];

			Log.d(TAG, "send data to server");
			DataManager.getInstance(mContext).saveLog("send data to server");
			
			
			JSONObject content = new JSONObject();
			try {
				content.put("type", "data");
				JSONArray attrs = new JSONArray();
				JSONObject attr = new JSONObject();
				attr.put("name", "temp");
				attr.put("type", "string");
				attr.put("value", data[1]);
				attrs.put(attr);
				attr = new JSONObject();
				attr.put("name", "path");
				attr.put("type", "string");
				attr.put("value", data[0]);
				attrs.put(attr);
				attr = new JSONObject();
				attr.put("name", "delay");
				attr.put("type", "string");
				attr.put("value", data[3]);
				attrs.put(attr);
				content.put("attributes", attrs);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			URL url;
			HttpURLConnection connection = null;
			try{
				Log.d(TAG, serverUrl + entityId);
				DataManager.getInstance(mContext).saveLog(serverUrl + entityId);
				
				url = new URL(serverUrl + entityId);
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.connect();

				OutputStream out = new BufferedOutputStream(connection.getOutputStream());
				out.write(content.toString().getBytes());
				out.close();

				InputStream in = new BufferedInputStream(connection.getInputStream());
				BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8")); 
				StringBuilder responseStrBuilder = new StringBuilder();

				String inputStr;
				while ((inputStr = streamReader.readLine()) != null)
					responseStrBuilder.append(inputStr);
				Log.d(TAG, responseStrBuilder.toString());

			} catch (IOException e) {
				// writing exception to log
				e.printStackTrace();
				serverAvailable = false;
			} finally {
				if(connection != null){
					connection.disconnect();
				}
			}
			return null;
		}
	}

	private class RegisterDeviceTask extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			// check if exists

			String entityId = ENTITY_URI + "resource:" + Secure.getString(mContext.getContentResolver(),
					Secure.ANDROID_ID); // unique identifier of the device

			URL url;
			HttpURLConnection connection = null;
			// register to orion

			JSONObject content = new JSONObject();
			try {
				content.put("type", "phone");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try{
				Log.d(TAG, serverUrl + entityId);
//				DataManager.getInstance(mContext).saveLog(serverUrl + entityId);
				
				url = new URL(serverUrl + entityId);
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.connect();

				OutputStream out = new BufferedOutputStream(connection.getOutputStream());
				out.write(content.toString().getBytes());
				out.close();

				InputStream in = new BufferedInputStream(connection.getInputStream());
				BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8")); 
				StringBuilder responseStrBuilder = new StringBuilder();

				String inputStr;
				while ((inputStr = streamReader.readLine()) != null)
					responseStrBuilder.append(inputStr);
//				Log.d(TAG, responseStrBuilder.toString());

			} catch (IOException e) {
				// writing exception to log
				e.printStackTrace();
			} finally {
				if(connection != null){
					connection.disconnect();
				}
			}
			return null;
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected()) {
			// Do whatever
			Log.d(TAG, "wifi connected");
//			DataManager.getInstance(mContext).saveLog("wifi connected");
			
			
			serverAvailable = true;
			
			// send data to sink
			long currentTime = System.currentTimeMillis();

			if(mContext != null){
				if(currentTime - updatedTime > 1000*60*1){
					while(QueueManager.getInstance(mContext).getQueueLength() > 0 & serverAvailable){
						String[] data = QueueManager.getInstance(mContext).getFromQueue();
						new SendSensorDataTask().execute(data);
					}
					BluetoothAdapter.getDefaultAdapter().setName(String.valueOf(QueueManager.getInstance(mContext).getQueueLength()));
					Log.d(TAG, "update name to " + String.valueOf(QueueManager.getInstance(mContext).getQueueLength()));
					DataManager.getInstance(mContext).saveLog("update name to " + String.valueOf(QueueManager.getInstance(mContext).getQueueLength()));
					
//					MainActivity.txMyQueueLen.setText(String.valueOf(QueueManager.getInstance(mContext).getQueueLength()));
					updatedTime = currentTime;
				}
			}
		}
	}
}