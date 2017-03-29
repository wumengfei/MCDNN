package MCDNN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MobileDevice {
	// Three indicators on mobile device 
	public float Memory;
	public float Battery;
	public float ExeLatency;
	public List <Model> model_lst = new ArrayList<>(); // 模拟内存，任务队列
	public Map<Model,Integer> model_freq = new HashMap<Model,Integer>(); // 内存中模型被请求的频次
	
	// This is the constructor of the class MobileDevice
	public MobileDevice(float Memory, float Battery, float ExeLatency){
		this.Memory = Memory;
		this.Battery = Battery;
		this.ExeLatency = ExeLatency;
		System.out.println("-------Mobile Device Initiated:--------"+"\n"
				+"Mobile Device Memory is: "+Memory+" MB\n"
				+"Mobile Device Battery is: "+Battery+" mAh\n");
	}
	
	// paging实现，从内存中片出请求频率最低的模型
	public void evictModel(Map<Model,Integer> model_freq){
		Model min_model = new Model(0,0,0,0,0,0,0);
		int min = 1000000;
		for(Model m: model_freq.keySet()){
			if(model_freq.get(m) < min){
				min_model = m;
				min = model_freq.get(m);
			}
		}
		this.Memory += min_model.memory;// 只需空出内存和电量，不应从list中删掉，为了保留频数。
		System.out.println("Model " + min_model +" has been evicted!");
		
	}
	
	// 加载模型, 超出内存后片出低频模型
	public void loadModel(Model model){
		if (!model_freq.keySet().contains(model)){
			model_freq.put(model, 1);
			while(this.Memory < model.memory){
				evictModel(model_freq);
			}
			this.Memory -= model.memory;
		}
		else{
			model_freq.put(model, model_freq.get(model)+1);
		}
		model_lst.add(model);
		this.Battery -= model.battery;
		if(this.Battery < 0){
			System.out.println("Battery Critically Low and Shutdown!!");
			System.exit(1);
		}
	}
	
	public int getModelNums(){
		return this.model_lst.size();
	}
	
	
	public void setMemory(float Memory){
		this.Memory = Memory;
	}
	
	public void setBattery(float Battery){
		this.Battery = Battery;
	}
	
	public void setExeLatency(float ExeLatency){
		this.ExeLatency = ExeLatency;
	}
	
	public float getMemory(){
		return this.Memory;
	}
	
	public float getBattery(){
		return this.Battery;
	}
	
	public float getExeLatency(){
		return this.ExeLatency;
	}
	
	
}
