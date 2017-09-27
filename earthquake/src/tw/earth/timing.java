package tw.earth;

import java.util.Timer;
import java.util.TimerTask;

public class timing extends TimerTask{
	//此方法要覆寫
	//想要定時執行的工作寫在該method中
	public void run(){
		
	}

	public static void main(String[] args){
		//建立計時器
		Timer timer = new Timer();
		//設定計時器
		//第一個參數為"欲執行的工作",會呼叫對應的run() method
		//第二個參數為程式啟動後,"延遲"指定的毫秒數後"第一次"執行該工作
		//第三個參數為每間隔多少毫秒執行該工作
		timer.schedule(new timing(), 1000, 2000);
	}
}
