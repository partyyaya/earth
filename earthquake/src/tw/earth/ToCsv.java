package tw.earth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class ToCsv {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try { 
             BufferedReader reader = new BufferedReader(new FileReader("C:/Users/06092681/Desktop/data/a1.csv"));//换成你的文件名
             LinkedList<quakedata> list = new LinkedList<>();//使用list存放資料
             quakedata data = new quakedata();
             reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
             String line = null; 
             while((line=reader.readLine())!=null){ 
                 String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分     
                 data.setNumber(item[0]);
                 data.setDate(item[1]);
                 data.setLon(item[2]);
                 data.setLat(item[3]);
                 data.setScale(item[4]);
                 data.setDepth(item[5]);
                 data.setPosition(item[6]);
                 list.add(data);
                 //String last = item[item.length-1];//这就是你要的数据了
                 //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                 //System.out.println(item[1]); 
             } 
             System.out.println(list.get(0).getNumber());//最新的資料會放最後面
         } catch (Exception e) { 
             e.printStackTrace(); 
         }
		 
	}

}
