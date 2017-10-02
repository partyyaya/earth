package tw.earth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * jsoup爬蟲測試
 * 
 * */
public class Jsouptest extends TimerTask{
	
	//此方法要覆寫
	//想要定時執行的工作寫在該method中
	public void run(){
		Document doc = null;
		LinkedList<quakedata> list = new LinkedList<>();//使用list存放資料
		String[] dates=null;
		try {			
			Class.forName("com.mysql.jdbc.Driver");		
		} catch (Exception e) {
			System.out.println(e);
		}	
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
				
		String insql = "INSERT INTO data(number,date,lon,lat,depth,scale,position) values(?,?,?,?,?,?,?)";
		String sql = "SELECT * FROM data where date like ?";
		
		 
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/earthquake?useUnicode=true&characterEncoding=UTF-8",prop);
			 //輸入中文資料須在後面加上?useUnicode=true&characterEncoding=UTF-8
			PreparedStatement pstmt=conn.prepareStatement(insql);
			PreparedStatement pstmt2=conn.prepareStatement(sql);
			/*-----jsoup預備程序----記得連hidden也寫進去-*/
			doc = Jsoup.connect("http://scweb.cwb.gov.tw/Page.aspx?ItemId=20&amp;loc=tw&amp;adv=1").timeout(5000).post();//設定超時時間
						
			/*--------取出年分資料-------------*/
			Elements selected = doc.select("select option[selected]");
			Element year = selected.get(0);
			String yt = year.text();
	    	System.out.println(yt);
			/*--------取出table資料-----------*/
	    	Element table = doc.select("table#ctl03_gvEarthquake").get(0);
			Elements rows = table.select("tr");
			//System.out.println( rows.size());
			for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
				//System.out.println( rows.size());
			    Element row = rows.get(i);
			    Elements cols = row.select("td");
			    quakedata data = new quakedata();//使用model存取
			    for (int j = 0; j < cols.size(); j++) {//取出所有td
			    	//System.out.println(cols.size());
			    	Element col = cols.get(j);
			    	Elements as=col.select("a");			    	
			    	for (int k = 0; k < as.size(); k++) {//取出所有a
			    		//System.out.println(as.size());    		
				    	Element a = as.get(k);
				    	String Text = a.text();
				    	/*---------轉換資料庫時間格式--------*/
				    	if(j==1) {
				    		Text=yt+"-"+Text;
				    		Text=Text.replaceFirst("月","-");
				    		Text=Text.replaceFirst("時","-");
				    		Text=Text.replaceFirst("日","-");
				    		Text=Text.replaceFirst(":","-");
					    	Text=Text.replaceFirst("分","-00");				    	
					    	dates = Text.split("-");
					    	if(Integer.parseInt(dates[1])>=10) {
					    		Text = dates[0]+"-"+dates[1]+"-"+dates[2]+" "+dates[3]+":"+dates[4]+":"+dates[5];
					    	}else {
					    		Text = dates[0]+"-0"+dates[1]+"-"+dates[2]+" "+dates[3]+":"+dates[4]+":"+dates[5];	
					    	}
				    	}
				    	/*----------------------------*/
				    	
				    	/*---------將資料存入集合--------*/
				    	switch(j) {
				    		case 0:data.setNumber(Text);break;
				    		case 1:data.setDate(Text);break;
				    		case 2:data.setLon(Float.parseFloat(Text));break;
				    		case 3:data.setLat(Float.parseFloat(Text));break;
				    		case 4:data.setScale(Float.parseFloat(Text));break;
				    		case 5:data.setDepth(Float.parseFloat(Text));break;
				    		case 6:data.setPosition(Text);break;
				    	}
				    	System.out.print(Text+",");
				    }
			    }
			    System.out.println();
			    list.add(data);			
			}
			System.out.println(list.get(0).getDate());
			for(int p=0;p<=(list.size()-1);p++) {//檢查時間->資料是否重複
				System.out.println(list.get(p).getDate());
				pstmt2.setString(1,list.get(p).getDate()+"%");
				ResultSet rs = pstmt2.executeQuery();
				if(rs.next()) {//若有重複
					continue;
				}else {
					pstmt.setString(1,list.get(p).getNumber());
	            	pstmt.setString(2,list.get(p).getDate());
					pstmt.setFloat(3,list.get(p).getLon());
					pstmt.setFloat(4,list.get(p).getLat());
					pstmt.setFloat(5,list.get(p).getDepth());
					pstmt.setFloat(6,list.get(p).getScale());
					pstmt.setString(7,list.get(p).getPosition());
					pstmt.execute();
				}
			}
			/*--------取出table資料-----------*/
//			  String linkHref = link.attr("href");
//			  String linkText = link.text();
//			  System.out.println(linkText);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String title = doc.title();
//		System.out.println(title);
	}
	
	public static void main(String[] args) {	
		//建立計時器
				Timer timer = new Timer();
				//設定計時器
				//第一個參數為"欲執行的工作",會呼叫對應的run() method
				//第二個參數為程式啟動後,"延遲"指定的毫秒數後"第一次"執行該工作
				//第三個參數為每間隔多少毫秒執行該工作
				timer.schedule(new Jsouptest(), 1000, 1000*60*60*24);//每一天定期更新一次
	}

}
