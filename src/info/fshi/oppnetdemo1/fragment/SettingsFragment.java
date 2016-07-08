package info.fshi.oppnetdemo1.fragment;

import info.fshi.oppnetdemo1.R;
import info.fshi.oppnetdemo1.utils.Constants;
import info.fshi.oppnetdemo1.utils.SharedPreferencesUtil;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {

	Context mContext;

	private final int REQUEST_BT_ENABLE = 1;
	
	private final String TAG = "SettingsFragment";

	public SettingsFragment(Context context){
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.settings_fragment_layout, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		ToggleButton bluetoothToggle = (ToggleButton) getView().findViewById(R.id.settings_bluetooth_togglebutton);
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
		if (mBluetoothAdapter.isEnabled()) {
			bluetoothToggle.setChecked(true);
		}else{
			bluetoothToggle.setChecked(false);
		}
		bluetoothToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// enable bluetooth
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, REQUEST_BT_ENABLE);
				} else {
					// disable bluetooth
					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
					if (mBluetoothAdapter.isEnabled()) {
						mBluetoothAdapter.disable(); 
					}
					getActivity().moveTaskToBack(true); 
					getActivity().finish();
				}
			}
		});

		ToggleButton wifiToggle = (ToggleButton) getView().findViewById(R.id.settings_wifi_togglebutton);
		WifiManager mng = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		if(mng.isWifiEnabled()){
			wifiToggle.setChecked(true);
		}else{
			wifiToggle.setChecked(false);
		}
		wifiToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				WifiManager mng = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
				mng.setWifiEnabled(isChecked);
			}
		});

		ToggleButton batteryToggle = (ToggleButton) getView().findViewById(R.id.settings_battery_togglebutton);
		batteryToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if(isChecked){
					Constants.SCAN_INTERVAL = Constants.SCAN_INTERVAL_SAVING;
					Constants.SCAN_DURATION = Constants.SCAN_DURATION_SAVING;
				}else{
					Constants.SCAN_INTERVAL = Constants.SCAN_INTERVAL_NORMAL;
					Constants.SCAN_DURATION = Constants.SCAN_DURATION_NORMAL;
				}
			}
		});

		ToggleButton energyToggle = (ToggleButton) getView().findViewById(R.id.settings_energy_togglebutton);
		energyToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				SharedPreferencesUtil.savePreferences(mContext, Constants.SP_KEY_ENERGY, isChecked);
			}
		});

	}
}
