package info.fshi.oppnetdemo1.experiment;

import java.util.ArrayList;

public class ExperimentList {

	public static ArrayList<Experiment> experimentList = new ArrayList<Experiment>(); 

	public static ExperimentListAdapter experimentListAdapter;
	
	public static void init(){
		experimentList = new ArrayList<Experiment>();
	}
	
	public static void update(ArrayList<Experiment> newExperimentList){
		experimentList.clear(); 
		for(Experiment experiment: newExperimentList){
			experimentList.add(experiment);
		}
	}
}
