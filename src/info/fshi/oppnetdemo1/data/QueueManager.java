package info.fshi.oppnetdemo1.data;

import info.fshi.oppnetdemo1.utils.Constants;

import java.util.ArrayList;
import java.util.Random;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

public class QueueManager {

	private final static String TAG = "QueueManager";

	public static String ID;

	private static volatile ArrayList<QueueItem> queue = new ArrayList<QueueItem>();
	
	public long sensorTimestamp = 0;
	public long sinkTimestamp = 0;

	public int packetsReceived = 0;
	public int packetsSent = 0;
	
	public int contacts = 0;
	public int peers = 0;
	
	// queue is a json with format {sequence of ids : content}

	private static QueueManager qManager = null;
	
	private Context mContext;
	
	private QueueManager(Context context){
		mContext = context;
		// for test purpose, init queue content
		ID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if(Constants.DEBUG){
			initQueue();
			Log.d(TAG, "init queue");
			DataManager.getInstance(mContext).saveLog("init queue");
		}
	}

	public static QueueManager getInstance(Context context){
		if(qManager == null){
			qManager = new QueueManager(context);
		}
		return qManager;
	}
	
	private void initQueue(){
		Random rand = new Random();
		for(int i=0; i<rand.nextInt(200); i++){
			StringBuffer sb = new StringBuffer();
			sb.append(20 + Math.random() - 0.5);
			QueueItem qItem = new QueueItem();
			qItem.packetId = ID + ":" + String.valueOf(System.currentTimeMillis() + new Random().nextInt(1000));
			qItem.path.add(ID);
			qItem.data = sb.toString();
			qItem.timestamp = System.currentTimeMillis();
			queue.add(qItem);
		}
		BluetoothAdapter.getDefaultAdapter().setName(String.valueOf(queue.size()));
		Log.d(TAG, "update name to " + String.valueOf(queue.size()));
		DataManager.getInstance(mContext).saveLog( "update name to " + String.valueOf(queue.size()));
		
	}
	
	public void appendToQueue(String packetId, String path, String data, String delay){

		QueueItem qItem = new QueueItem();
		
		String[] IDs = path.split(",");
		String[] delays = delay.split(",");
		boolean hasLoop = false;
		for(int i=0; i<IDs.length; i++){
			if(IDs[i] == this.ID){
				hasLoop = true;
				break;
			}
			qItem.path.add(IDs[i]);
			qItem.delay.add(Long.parseLong(delays[i]));
		}
		if(!hasLoop){
			qItem.path.add(this.ID);
			qItem.packetId = packetId;
			qItem.data = data;
			qItem.timestamp = System.currentTimeMillis();
			queue.add(qItem);
		}
	}

	// format: ID1,ID2,ID3 && data

//	public String[] getFromQueue(int length, String MAC){
//		int peerID = Devices.PARTICIPATING_DEVICES_ID.get(MAC);
//		String[] data = new String[3];
//		ArrayList<QueueItem> newQueue = new ArrayList<QueueItem>();
//		for(QueueItem qItem: queue){
//			if(length > 0){
//				StringBuffer sb = new StringBuffer();
//				boolean hasLoop = false;
//				for(int i=0; i< qItem.path.size(); i++){
//					if(peerID == qItem.path.get(i)){ // loop detection
//						hasLoop = true;
//					}
//					sb.append(qItem.path.get(i));
//					if(i != qItem.path.size() - 1){
//						sb.append(",");
//					}
//				}
//				if(hasLoop){
//					newQueue.add(qItem);
//				}
//				else{
//					data[0] = sb.toString();
//					data[1] = qItem.data;
//					data[2] = qItem.packetId;
//					length -= 1;
//				}
//			}else{
//				newQueue.add(qItem);
//			}
//		}
//		queue = newQueue;
//		updateBluetoothDeviceName();
//		return data;
//	}

	public synchronized String[] getFromQueue(){ // get the first one from the queue
		String[] data = new String[4];
		if(queue.size() > 0){
			StringBuffer sb = new StringBuffer();

			QueueItem qItem = queue.get(0);
			
			for(int i=0; i< qItem.path.size(); i++){
				sb.append(qItem.path.get(i));
				if(i != qItem.path.size() - 1){
					sb.append(",");
				}
			}
			
			data[0] = sb.toString(); // path
			
			sb = new StringBuffer();
			
			for(int i=0; i< qItem.delay.size(); i++){
				sb.append(qItem.delay.get(i));
				sb.append(",");
			}
			
			sb.append(System.currentTimeMillis() - qItem.timestamp);

			data[3] = sb.toString(); // delay
			
			data[1] = qItem.data; // data
			data[2] = qItem.packetId; // id
			queue.remove(0);
		}
		return data;
	}

	public synchronized int getQueueLength(){
		Log.d(TAG, "queue len is " + String.valueOf(queue.size()));
		DataManager.getInstance(mContext).saveLog("queue len is " + String.valueOf(queue.size()));
		
		int queueSize = queue.size();
//		for(QueueItem qItem: queue){
//			if(qItem.path.size() > 1){
//				Log.d(TAG, qItem.packetId + "@" + qItem.path.toString() + "@" + qItem.delay.toString() + ":" + qItem.data);
//			}
//		}
		if(queueSize == 0){
			initQueue();
		}
		
		return queueSize;
	}
}
