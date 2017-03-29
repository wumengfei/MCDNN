package MCDNN;

public class Model {
	// String mName;
	float memory;
	float battery;	// battery cost each time
	float cloudCost;
	float transmissionCost; // battery cost for image transmission
	float transmissionLatency; // latency of image transmission
	float exeLatency;
	float accuracy;
	
	// constructor of the class MobileDevice
	public Model(float memory, float battery, float cloudCost, float transmissionCost, 
			float transmissionLatency, float exeLatency, float accuracy){
		this.memory = memory;
		this.battery = battery;
		this.cloudCost = cloudCost;
		this.transmissionCost = transmissionCost;
		this.transmissionLatency = transmissionLatency;
		this.exeLatency = exeLatency;
		this.accuracy = accuracy;
	}
	
	public void setMemory(float memory){
		this.memory = memory;
	}
	
	public void setBattery(float battery){
		this.battery = battery;
	}
	
	public void setCloudCost(float cloudCost){
		this.cloudCost = cloudCost;
	}
	
	public void setTransitionCost(float transitionCost){
		this.transmissionCost = transitionCost;
	}
	
	public float getMemory(){
		return this.memory;
	}
	
	public float getBattery(){
		return this.battery;
	}
	
	public float getCloudCost(){
		return this.cloudCost;
	}
	
	public float getTransitionCost(){
		return this.transmissionCost;
	}
}
