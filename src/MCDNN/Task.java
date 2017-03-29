package MCDNN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;  
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Task {
	private List <Model> model_lst = new ArrayList<>();  //	Model list
	private Map<Integer,Float> model_acc_map = new HashMap<Integer,Float>();
	private int model_counts = 0;
	
	//	add a model of this task
	public void addModel(Model model){
		model_lst.add(model);
		model_counts += 1;
		model_acc_map.put(model_counts, model.accuracy);
	}
	
	//  choose the best model under energy budget on mobile device
	// Robust!!!!!!
	public Model getMobileBestModel(float eBudget){
		int index = 0;
		float max = this.model_lst.get(index).accuracy;
		for (int i = 0; i < this.model_lst.size(); i++){
			float thisAcc = this.model_lst.get(i).accuracy;
			float thisEne = this.model_lst.get(i).battery;
			if (thisEne > eBudget) continue;
			if (thisAcc > max){
				index = i;
				max = thisAcc;
			}
		}
		return this.model_lst.get(index);
	}
	
	//  choose the best model under energy budget on mobile device
	// Robust!!!!!!
	public Model getCloudBestModel(float cBudget){
		int index = 0;
		float max = this.model_lst.get(index).accuracy;
		for (int i = 0; i < this.model_lst.size(); i++){
			float thisAcc = this.model_lst.get(i).accuracy;
			float thisCost = this.model_lst.get(i).cloudCost;
			if (thisCost > cBudget) continue;
			if (thisAcc > max){
				index = i;
				max = thisAcc;
			}
		}
		return this.model_lst.get(index);
	}
	
	//	get the model numbers 
	public int getModelNum(){
		return model_counts;
	}
}
