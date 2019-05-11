package util;

import entity.UrlDTO;
import entity.WebsiteConfigPO;
import spider.GetPage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 获取配置文件信息
 * create at 2019-04-14 by MaXin
 */
public class ConfigUtil {
    public static String CLASS_BASE_PATH;
    public static String CONFIG_PATH ;
    public static List<WebsiteConfigPO> CONFIG;

    static {
        CLASS_BASE_PATH = ConfigUtil.class.getResource("/").getPath();
        CONFIG_PATH = CLASS_BASE_PATH +File.separator+"website_conf.json";
        try {
            File file = new File(CONFIG_PATH);
            if (!file.exists())
                throw new Exception("配置文件不存在");
            CONFIG = JsonUtil.getConfig(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) {
        String baseURL = "https://m.biquge.info";
        WebsiteConfigPO websiteConfigPO = null;
        for (WebsiteConfigPO t : ConfigUtil.CONFIG) {
            if (baseURL.matches(t.getWebsite())){
                websiteConfigPO = t;
                break;
            }
        }
//        System.out.println(websiteConfigPO);
        //获取网站的所有的页面
        ConcurrentLinkedDeque<UrlDTO> urls = new ConcurrentLinkedDeque<UrlDTO>();
        try {
            urls.addAll(GetPage.getListUrl(baseURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //保存已经访问过的链接
        Set<UrlDTO> urlSet = new LinkedHashSet<>();
        Set<UrlDTO> novelUrlSet = new HashSet<>();
        UrlDTO urlDTO ;
        while ((urlDTO = urls.removeFirst()) != null){
            String url = urlDTO.getUrl();
//            System.out.println(urlDTO);
            if (!url.matches(websiteConfigPO.getWebsite()))
                continue;
            if (urlSet.contains(urlDTO))
                continue;
            if (url.matches(websiteConfigPO.getNovelDetail()) ){
                if ("".equals(urlDTO.getText()))
                    continue;
                urlSet.add(urlDTO);
                novelUrlSet.add(urlDTO);
                System.out.println(urlDTO);
                continue;
            }
            urlSet.add(urlDTO);
            //如果链接是目录页或内容页，则跳过不进入
            if (url.matches(websiteConfigPO.getNovelCatalogUrl()) || url.matches(websiteConfigPO.getNovelChapterUrl())) {
                continue;
            }
            List<UrlDTO> urlList = null;
            try {
                urlList = GetPage.getListUrl(url);
                urls.addAll(urlList);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                continue;
            }
        }
        System.out.println(novelUrlSet.size());

    }

    /*
    public static void main(String[] args) {
        String url = "https://m.biquge.info/bookcase.php";
        WebsiteConfigPO websiteConfigPO = ConfigUtil.CONFIG.get(0);
        String website = websiteConfigPO.getWebsite();
        System.out.println(website);
        System.out.println(url.matches(website));
    }
    */

    public static void main(String[] args) {
//        for (WebsiteConfigPO websiteConfigPO : CONFIG) {
//            System.out.println(websiteConfigPO);
//            System.out.println(websiteConfigPO.getWebsite().hashCode());
//        }

        String url = "https://m.biquge.info/0_383/";

    }

}
