package info.fshi.oppnetdemo1.fragment;

import info.fshi.oppnetdemo1.R;
import info.fshi.oppnetdemo1.experiment.Experiment;
import info.fshi.oppnetdemo1.experiment.ExperimentActivity;
import info.fshi.oppnetdemo1.experiment.ExperimentList;
import info.fshi.oppnetdemo1.experiment.ExperimentListAdapter;
import info.fshi.oppnetdemo1.utils.Constants;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ExperimentFragment extends Fragment {

	ListView experimentLv;
	
	Context mContext;
	
	public ExperimentFragment(Context context){
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.experiment_fragment_layout, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		experimentLv = (ListView) getView().findViewById(R.id.experiment_list);
		
		ExperimentList.init();
		
		Experiment ex1 = new Experiment();
		ex1.name = "IC OppNet";
		ex1.description = "Imperial College OppNet Experiment";
		ex1.location = "180 Queen's Gate, London SW7 2AZ";
		ex1.endTime = System.currentTimeMillis() + 1000000000;
		
		Experiment ex2 = new Experiment();
		ex2.name = "Olympic Park";
		ex2.description = "Olympic Park OppNet Experiment with Intel and IC";
		ex2.location = "London E20 2ST";
		ex2.endTime = System.currentTimeMillis() + 200000000;

		ExperimentList.experimentList.add(ex1);
//		ExperimentList.experimentList.add(ex2);
		
		ExperimentList.experimentListAdapter = new ExperimentListAdapter(mContext, R.layout.experiment_listitem_layout);
		// Assign adapter to ListView
		experimentLv.setAdapter(ExperimentList.experimentListAdapter);

		// ListView Item Click Listener
		experimentLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ListView Clicked item index
				Intent intent = new Intent(mContext, ExperimentActivity.class);
				intent.putExtra(Constants.INTENT_KEY_POSITION, position);
				startActivity(intent);
//				int itemPosition = position;
//				// ListView Clicked item value
//				Experiment itemValue = (Experiment) experimentLv.getItemAtPosition(position);
			}
		}); 
	}

}
