package tw.earth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.Properties;



public class database {

	public static void main(String[] args) {
        String line = null;
		try {			
			Class.forName("com.mysql.jdbc.Driver");		
		} catch (Exception e) {
			System.out.println(e);
		}	
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		String insql = "INSERT INTO data(number,date,lon,lat,scale,depth,position) values(?,?,?,?,?,?,?)";		
		try (
				BufferedReader reader = new BufferedReader(new FileReader("C:/Users/06092681/Desktop/data/1995.csv"));//换成你的文件名
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/earthquake?useUnicode=true&characterEncoding=UTF-8",prop);
				//輸入中文資料須在後面加上?useUnicode=true&characterEncoding=UTF-8
				PreparedStatement pstmt=conn.prepareStatement(insql);
				)
			{		
					 reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉 
					 while((line=reader.readLine())!=null){ 
		                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分 
						pstmt.setString(1, item[0]);
						pstmt.setString(2, item[1]);
						pstmt.setString(3, item[2]);
						pstmt.setString(4, item[3]);
						pstmt.setString(5, item[4]);
						pstmt.setString(6, item[5]);
						pstmt.setString(7, item[6]);
						pstmt.execute();
					 }
			}
			catch (Exception e){
				System.out.println(e);
			}
			
	}

}
