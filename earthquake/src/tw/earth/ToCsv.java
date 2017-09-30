package tw.earth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.Properties;

public class ToCsv {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {			
				Class.forName("com.mysql.jdbc.Driver");		
			} catch (Exception e) {
				System.out.println(e);
			}	
			Properties prop = new Properties();
			prop.setProperty("user", "root");
			prop.setProperty("password", "root");
			
			
			String insql = "INSERT INTO data(number,date,lon,lat,scale,depth,position) values(?,?,?,?,?,?,?)";	
		
		 try { 
             BufferedReader reader = new BufferedReader(new FileReader("C:/Users/06092681/Desktop/data/2017.csv"));//换成你的文件名
             LinkedList<quakedata> list = new LinkedList<>();//使用list存放資料
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/earthquake?useUnicode=true&characterEncoding=UTF-8",prop);
			 //輸入中文資料須在後面加上?useUnicode=true&characterEncoding=UTF-8
			 PreparedStatement pstmt=conn.prepareStatement(insql);
			 
             reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
             String line = null; 
             while((line=reader.readLine())!=null){ 
                 String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                 quakedata data = new quakedata();
                 data.setNumber(item[0]);
                 //System.out.println(item[0]);
//                 data.setDate(item[1]);
//                 data.setLon(item[2]);
//                 data.setLat(item[3]);
//                 data.setScale(item[4]);
//                 data.setDepth(item[5]);
//                 data.setPosition(item[6]);
//                 list.add(data);
                 //String last = item[item.length-1];//这就是你要的数据了
                 //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                 //System.out.println(item[1]); 
             }
//             for(int i=(list.size()-1);i>=0;i--) {
//            	pstmt.setString(1,list.get(i).getNumber());
//            	pstmt.setString(2,list.get(i).getDate());
//				pstmt.setString(3,list.get(i).getLon());
//				pstmt.setString(4,list.get(i).getLat());
//				pstmt.setString(5,list.get(i).getScale());
//				pstmt.setString(6,list.get(i).getDepth());
//				pstmt.setString(7,list.get(i).getPosition());
//				pstmt.execute();
//            	//System.out.println(i);
//             }
             //System.out.println((list.size()-1));
             //System.out.println(list.get(list.size()-1).getNumber());//list.get(list.size()-1)為第一筆
         } catch (Exception e) { 
             e.printStackTrace(); 
         }
		 
	}

}
