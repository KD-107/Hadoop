import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Task1 {
	/**
	 * @param filePath	文件路径：backups/www.ctrip.com.txt/
	 * @return
	 * @throws IOException
	 */
	public Document getHtml1(String filePath) throws IOException{
        Document root_document = Jsoup.parse(new File("./backups/www.ctrip.com.txt/"),"UTF-8","https://www.educoder.net/");
		return root_document;
	}

	/**
	 *
	 * @param filePath	文件路径：backups/hotels.ctrip.com_domestic-city-hotel.txt/
	 * @return
	 * @throws IOException
	 */
	public Document getHtml2(String filePath) throws IOException{
        Document root_document = Jsoup.parse(new File("./backups/hotels.ctrip.com_domestic-city-hotel.txt/"),"UTF-8","https://www.educoder.net/");
		return root_document;

	}
}

public class Task2 {

	//通过filePath文件路径获取Docment对象
	public Document getDoc1(String filePath) throws IOException{
        File file = new File("./backups/www.ctrip.com.txt");
        Document document = Jsoup.parse(file, "UTF-8", "http://www.ctrip.com/");
		return document;
	}


	public Document getDoc2(String filePath) throws IOException{
        File file = new File("./backups/you.ctrip.com.txt");
        Document document = Jsoup.parse(file, "UTF-8", "http://you.ctrip.com/");
		return document;
	}

	//获取所有链接
	public Elements getLinks(Document doc){
        Elements links = doc.select("link[href]");
		return links;
	}

	//获取第一个class为“pop_attention”的div
	public Element getDiv(Document doc){
        Element element = doc.select("div.pop_attention").first();
		return element;
	}

	//获取所有li之后的i标签
	public Elements getI(Document doc){
        Elements element = doc.select("li>i");
		return element;
	}

}


public class Task3 {

    //通过filePath文件路径获取Docment对象
	public Document getDoc(String filePath) throws IOException{
        File file = new File("./backups/hotel.ctrip.com.txt");
        Document document = Jsoup.parse(file,"UTF-8","http://hotels.ctrip.com");

		return document;
	}

	//获取所有链接
	public List<String> getLinks(Document doc){
		List<String> arr = new ArrayList<>();
        Elements k = doc.select("a[href]");
        for (Element g:k){
            arr.add(g.tagName()+"$"+g.attr("abs:href")+"("+g.text()+")");
        }

		return arr;
	}

	//获取图片
	public List<String> getMedia(Document doc){
		List<String> list = new ArrayList<>();
        Elements l = doc.select("[src]");
        for (Element h:l){
            if (h.tagName().equals("img")){
                list.add(h.tagName()+"$"+h.attr("abs:src"));
            }
        }

		return list;
	}

	//获取link[href]链接
	public List<String> getImports(Document doc){
		List<String> list = new ArrayList<>();
        Elements k = doc.select("link[href]");
        for (Element g:k){
            list.add(g.tagName()+"$"+g.attr("abs:href")+"("+g.attr("rel")+")");
        }

        return list;
	}

}


public class Task4 {

    //通过filePath文件路径获取Docment对象
	public Document getDoc(String filePath) throws IOException{
        File file = new File("backups/hotels.ctrip.com_domestic-city-hotel.txt");
        Document doc = Jsoup.parse(file,"UTF-8","http://hotels.ctrip.com/");

		return doc;
	}

	/**
	 * 获取所有城市返回城市信息集合
	 * @param doc
	 * @return
	 */
	public List<HotelCity> getAllCitys(Document doc){
        List<HotelCity> cities = new ArrayList<HotelCity>();
        Element pp = doc.getElementsByClass("pinyin_filter_detail layoutfix").first();
        Elements dd = pp.getElementsByTag("dd");
        Elements dt = pp.getElementsByTag("dt");

        for (int i = 0; i < dd.size(); i++){
           Element bb = dt.get(i);
           Element head_hotelsLink = dd.get(i);
           Elements links = head_hotelsLink.children();

           for (Element link : links){
               String pinyin_cityID = link.attr("href").replace("/hotel/","");
               String pinyin = pinyin_cityID.replace(StringUtil.getNumbers(link.attr("href")),"");
               HotelCity city = new HotelCity();
               city.setCityId(StringUtil.getNumbers(link.attr("href")));
               city.setCityName(link.html());
               city.setHeadPinyin(bb.text());
               city.setPinyin(pinyin);
               cities.add(city);
           }
        }

		return cities;
	}
}
