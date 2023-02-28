
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
public class Task1 {

    //通过filePath文件路径获取Docment对象
	public Document getDoc(String filePath) throws IOException{
        Document doc = Jsoup.parse(new File(filePath), "utf-8","http://www.educoder.com");
		return doc;
	}

	/**
	 * 获取清理后的信息
	 * @param doc
	 * @return
	 */
	public List<String> cleanHTML(Document doc){
        ArrayList<String> list = new ArrayList<>();

        String clean_basic = Jsoup.clean(doc.toString(),Whitelist.basic());
        String clean_simple = Jsoup.clean(doc.toString(),Whitelist.simpleText());

        list.add(clean_basic);
        list.add(clean_simple);

		return list;

	}

}


public class Task2 {

	public List<Hotel> getHotle(String hotelResult){

        //把json转化为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(hotelResult);
        //把json文本转化成javaBean集合
        List<Hotel> hotels = JSON.parseArray(jsonObject.getString("hotelPositionJSON"),Hotel.class);
        for (Hotel hotel : hotels){
            if (hotel.getName().equals("北京丽景湾国际酒店")){
                hotel.setPrice(815.0);
            }
            if (hotel.getName().equals("北京5L饭店")){
                hotel.setPrice(599.0);
            }
            if (hotel.getName().equals("北京骏马国际酒店")){
                hotel.setPrice(546.0);
            }
        }

		return hotels;
	}

	public  String getHotelListString(String cityId,String url){
		String hotelResult="";
    	try {
        	InputStream is = new FileInputStream(new File("src/step2/hotelResult.txt"));
        	byte[] b=new byte[1024];
        	int len=0;
        	try {
            	while((len=is.read(b))!=-1){
                	String str=new String(b,0,len);
                	hotelResult+=str;
            	}
        	} catch (IOException e) {
            	e.printStackTrace();
        	}
    	} catch (FileNotFoundException e) {
        	e.printStackTrace();
    	}

    	return hotelResult;
	}

}