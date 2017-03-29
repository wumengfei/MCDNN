package MCDNN;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MCDNN {
	public static void main(String args[]){
		// 初始化云服务器和移动设备
		Cloud cloud = new Cloud(100, 10);
		MobileDevice md = new MobileDevice(100, 100, 20);
        // 初始化一些分类任务及其变体，测试通过后通过文件读入。
        // memory结构：memory-battery-cloudCost-transmissionCost-transmissionLatency-exeLatency-accuracy
		Task task1 = new Task();
		Model model11 = new Model(6,2,10,5,6,10,0.92f);
		Model model12 = new Model(12,2,10,3,12,10,0.98f);
		task1.addModel(model11);
		task1.addModel(model12);
		Task task2 = new Task();
		Model model21 = new Model(10,2,10,7,6,10,0.99f);
		Model model22 = new Model(12,6,10,2,12,10,0.98f);
		task2.addModel(model21);
		task2.addModel(model22);
		Task task3 = new Task();
		Model model31 = new Model(10,2,10,1,6,10,0.99f);
		Model model32 = new Model(12,6,10,2,12,10,0.98f);
		task3.addModel(model31);
		task3.addModel(model32);
		
		List<Task> rqst_pool = new ArrayList<>(); // 构造任务队列		
		List<Float> accuracys = new ArrayList<>(); // 构造准确度存储队列
		
		rqst_pool.add(task1);
		rqst_pool.add(task2);
		rqst_pool.add(task3);
		
		// 仿真阶段，起定时器，在任务池中随机挑取模型，显示执行位置以及云端&移动端的剩余资源。
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	    	public void run() {
	    		scheduler(rqst_pool.get((int)(Math.random()*3)), cloud, md, accuracys);
	    	}
	    }, 1000, 1000);
	}
	
	public void initiate(Cloud cloud, MobileDevice mobileDevice){
		// to do --- 初始化从配置文件中读取
	}
	
	public static int scheduler(Task task, Cloud cloud, MobileDevice mobileDevice, List<Float> accuracys){
		System.out.println(task);
		// packing 
		int load_position; // 1: mobile device(md); 2: cloud server(cs)
		// Approximate Module Scheduler
		Model model_md = task.getMobileBestModel(mobileDevice.Battery);
		Model model_cs = task.getCloudBestModel(cloud.budget);
		// choose the position to load models
		if(model_md.accuracy > model_cs.accuracy){
			load_position = 1;
			mobileDevice.loadModel(model_md);
			accuracys.add(model_md.accuracy);
		}
		else if(model_md.accuracy < model_cs.accuracy){
			load_position = 2;
			cloud.budget -= model_cs.cloudCost;
			if(mobileDevice.Battery > model_cs.transmissionCost){
				mobileDevice.Battery -= model_cs.transmissionCost;
				accuracys.add(model_cs.accuracy);
			}
			else{
				System.out.println("Battery Critically Low and Shutdown!!");
				System.exit(1);
			}
			showDeviceInfo(mobileDevice);
			showCloudInfo(cloud);
		}
		else{
			if(model_md.exeLatency > model_cs.transmissionLatency){
				load_position = 2;
				cloud.budget -= model_cs.cloudCost;
				if(mobileDevice.Battery > model_cs.transmissionCost){
					mobileDevice.Battery -= model_cs.transmissionCost;
					accuracys.add(model_cs.accuracy);
				}
				else{
					System.out.println("Battery Critically Low and Shutdown!!");
					System.exit(1);
				}
				showDeviceInfo(mobileDevice);
				showCloudInfo(cloud);
			}
			else{
				accuracys.add(model_md.accuracy);
				load_position = 1;
				mobileDevice.loadModel(model_md);
				accuracys.add(model_md.accuracy);
				showDeviceInfo(mobileDevice);
				showCloudInfo(cloud);
			}
		}
		showPosition(load_position);
		calMAP(accuracys);
		return load_position;
	}
	
	
	public static void showDeviceInfo(MobileDevice md){
		System.out.println("-------Mobile Device Info:--------"+"\n"
				+"Mobile Device Memory is: "+md.Memory+" MB\n"
				+"Mobile Device Battery is: "+md.Battery+" mAh");
	}
	
	public static void showCloudInfo(Cloud cloud){
		System.out.println("-------Cloud Server Info:--------"+"\n"
				+"Cloud Budget: "+cloud.budget+" US Dollar");
	}
	
	public static void showPosition(int locationId){
		// 1: mobile device(md); 2: cloud server(cs)
		if(locationId == 1){
			System.out.println("Location Chosen: Mobile Device\n");
		}
		else{
			System.out.println("Location Chosen: Cloud Server\n");
		}
	}
	
	public static float calMAP(List<Float> accuracys){
		float total = 0;
		for(int i = 0; i < accuracys.size(); i++){
			total += accuracys.get(i);
		}
		float mAp = total/accuracys.size();
		System.out.println("mAp now is: "+ mAp);
		return mAp;
	}

}
