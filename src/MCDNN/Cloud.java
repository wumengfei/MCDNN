package MCDNN;

public class Cloud {
	// Two indicators on cloud
	float budget;
	float transLatency;
	
	public Cloud(float budget, float transLatency){
		this.budget = budget;
		this.transLatency = transLatency;
		System.out.println("-------Cloud Server Initiated:--------"+"\n"
		+"Cloud Budget: "+budget+" US Dollar\n"
		+"Cloud Transmission Latency: "+transLatency+" ms");		
	}
	
	public void setBudget(float budget){
		this.budget = budget;
	}
	
	public void setTransLatency(float transLatency){
		this.transLatency = transLatency;
	}
	
	public float getbudget(){
		return this.budget;
	}
	
	public float getTransLantency(){
		return this.transLatency;
	}
}
