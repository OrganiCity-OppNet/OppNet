package info.fshi.oppnetdemo1.utils;

public abstract class Constants {

	public static long SCAN_INTERVAL = 15000;
	public static long SCAN_DURATION = 10000;

	public static boolean DEBUG = true;

	public static String TAG_ACT_TEST = "test";

	public static long BT_CLIENT_TIMEOUT = 5000;

	public static long SENSOR_CONTACT_INTERVAL = 60000;
	public static long SINK_CONTACT_INTERVAL = 240000;

	public static int MAX_RELAY_NUM = 2;

	public static String MQTT_BROKER_ADDR = "tcp://achilles.doc.ic.ac.uk:1883";
	
	public static final String WEB_SERVER_ADDR = "http://hydeparkdemo.herokuapp.com/";
	public static final String WEB_SERVER_TX_ADDR = "http://hydeparkdemo.herokuapp.com/tx";
	public static final String WEB_SERVER_LOC_ADDR = "http://hydeparkdemo.herokuapp.com/loc";
	public static final String WEB_SERVER_DEVICELIST_ADDR = "http://hydeparkdemo.herokuapp.com/devicelist";
	public static final String WEB_SERVER_TYPELIST_ADDR = "http://hydeparkdemo.herokuapp.com/typelist";

}
