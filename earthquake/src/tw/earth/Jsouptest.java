package tw.earth;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * jsoup爬蟲測試
 * 
 * */
public class Jsouptest {
		
	public static void main(String[] args) {
		//解析html
		
		Document doc = null;
		try {
			doc = Jsoup.connect("http://scweb.cwb.gov.tw/Page.aspx?ItemId=20&loc=tw&adv=1").timeout(5000).get();//設定超時時間
			Element table = doc.select("table").get(1);
			
			/*--------取出年分資料-------------*/
			Elements selected = doc.select("select option[selected]");
			Element year = selected.get(0);
			String yt = year.text();
	    	System.out.println(yt);
			
			/*--------取出table資料-----------*/			
			Elements rows = table.select("tr");
			//System.out.println( rows.size());
			for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
				//System.out.println( rows.size());
			    Element row = rows.get(i);
			    Elements cols = row.select("td");
			    for (int j = 0; j < cols.size(); j++) {//取出所有td
			    	//System.out.println(cols.size());
			    	Element col = cols.get(j);
			    	Elements as=col.select("a");			    	
			    	for (int k = 0; k < as.size(); k++) {//取出所有a
			    		//System.out.println(as.size());    		
				    	Element a = as.get(k);
				    	String Text = a.text();
				    	if(j==1) {
				    		Text=Text.replaceFirst("月","/");
				    		Text=Text.replaceFirst("時",":");
					    	Text=Text.replaceFirst("分",":00");				    	
					    	if(Integer.parseInt(Text.substring(5,7))>=13) {
					    		if((Integer.parseInt(Text.substring(5,7))-12)>=10){
					    		Text=Text.substring(0,4)+" 下午 "+(Integer.parseInt(Text.substring(5,7))-12)+Text.substring(7,13);
					    		}else {
					    		Text=Text.substring(0,4)+" 下午 0"+(Integer.parseInt(Text.substring(5,7))-12)+Text.substring(7,13);	
					    		}
					    	}else {
					    		if((Integer.parseInt(Text.substring(5,7)))>=10){
						    		Text=Text.substring(0,4)+" 上午 "+(Integer.parseInt(Text.substring(5,7)))+Text.substring(7,13);
						    		}else {
						    		Text=Text.substring(0,4)+" 上午 0"+(Integer.parseInt(Text.substring(5,7)))+Text.substring(7,13);	
						    		}
					    	}				    	
					    	Text=yt+"/"+Text;
				    	}
				    	System.out.print(Text+",");
				    }
			    }
			    System.out.println();
			}
			/*--------取出table資料-----------*/
//			  String linkHref = link.attr("href");
//			  String linkText = link.text();
//			  System.out.println(linkText);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String title = doc.title();
//		System.out.println(title);
	}

}
