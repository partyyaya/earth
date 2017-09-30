package tw.earth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jsoupInsert {
	public static void main(String[] args) {
		for(int ii=1995;ii<=2017;ii++) {
			for(int jj=1;jj<=12;jj++) {
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
				String jt=null;
				if(jj>=10) {jt=jj+"";}else {jt="0"+jj;}
				doc = Jsoup.connect("http://scweb.cwb.gov.tw/Page.aspx?ItemId=20&amp;loc=tw&amp;adv=1")
						.data("__VIEWSTATE", "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")
						.data("__VIEWSTATEGENERATOR", "3989C74E")
						.data("__VIEWSTATEENCRYPTED", "")
						.data("__EVENTVALIDATION", "MNmuTHFy6fQgQrxLvk2HZIPN3VM5DKRH8pc0h41q3YKrukoeae7tZsb0L4OEt7SWXpYjxRXhhEjswY6wXHjCVtHZj13Ri7O/wK16vgVn1NTk0v3eEwdRjXVpNIOBvKQC+y7KpUA9+2ioAoCg9rM0KIDEKjKBA6Rlytkt+aWzpBm2jGNqxFind/mViDLCKc4TyBG9i87dLrW/uSv91Q2bmPxA+L5Fg7wHIvajs7RNbHeNO8D6AdYGVGdbedcGOqfAOatsXBoVDMRU7Ybd7bgpyLWQZ6z9Y+3aMRurcZ8rZwh07WE1S0AEjiRM/g8/KUOtVBw4vvSPWEKaLFpOAg6RIZzYb6SZv20hSwEXixKVFCzE6I3uHQPOJWX6lTYMCe1OcuJRYXibN7Hwgr5ud3EE9CsJMovqFP3mkjk+LzT0IlAcSz2MK2CxNQk2OBjVMCAVNI8fpVczqTiSx7hFGv13LzPh89o/6p4PJSnQNkDYwmEjwa/beNghnlKhg8UdBb6N6WfuW9d4LmECOuo6RNim1nWS5se39XkD50KqKZjvKvEt0EgyPiQ1B942HGlJw+Yeoc2IO3P7a1EDgbI6cTyhyF/mFpOc2PUnAUvg+YC6NW50ULMlzRN/POComhTwCkqC2zXES+Y+483NVlzArTj8Xo2qfo8INIG9blsokriHguO6neHEuAQ9H5LCS885FJFgowQsANUZbLpSyy7eRP69kpjo1AAmm48L9W5JU7eqK1X7D5LzhgYdxS3NU3LbuFm1cpEeLDcFBa3urh6oTTv3PAOEUTNSDwaStqvumuLaoHEGnFqOZ1aidTee+EIrrMbP7UHDZ//S9mBTyQGpId8lP5MrIsN4ygIvDobZCN0uhKvXAaps0wWykCO/SZNAkbyZhkUgXw==")
						.data("ctl03$ddlYear",ii+"")
						.data("ctl03$ddlMonth",jt)
						.data("ctl03$btnSearch","送出").timeout(5000).post();//設定超時時間
							
				/*--------取出年分資料-------------*/
				Elements selected = doc.select("select option[selected]");
				Element year = selected.get(0);
				String yt = year.text();
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
					pstmt2.setString(1,list.get(p).getDate());
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
//				  String linkHref = link.attr("href");
//				  String linkText = link.text();
//				  System.out.println(linkText);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			String title = doc.title();
//			System.out.println(title);
		}
	}
	}
}
