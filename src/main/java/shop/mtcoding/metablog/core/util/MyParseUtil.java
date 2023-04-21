package shop.mtcoding.metablog.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyParseUtil {
    public static String getThumbnail(String html){
        String thumbnail;
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");

        if(els.size() == 0){
            thumbnail= "/upload/person.png";
        }else{
            Element el = els.get(0);
            thumbnail = el.attr("src");
        }
        return thumbnail;
    }
}
