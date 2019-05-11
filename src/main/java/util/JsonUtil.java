package util;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSON;
import entity.WebsiteConfigPO;

import java.io.File;
import java.util.*;

/**
 * 解析JSON数据串
 * create at 2019-04-06 by MaXin
 */
public class JsonUtil {



    /**
     * @param jsonString
     * @return
     */
    public static Object json2Map(String jsonString) {

        Stack<Map> maps = new Stack<Map>(); //用来表示多层的json对象
        Stack<List> lists = new Stack<List>(); //用来表示多层的list对象
        Stack<Boolean> islist = new Stack<Boolean>();//判断是不是list
        Stack<String> keys = new Stack<String>(); //用来表示多层的key

        String keytmp = null;
        Object valuetmp = null;
        StringBuilder builder = new StringBuilder();
        char[] cs = jsonString.toCharArray();

        for (int i = 0; i < cs.length; i++) {

            switch (cs[i]) {
                case '{': //如果是{map进栈
                    maps.push(new HashMap());
                    islist.push(false);
                    break;
                case ':'://如果是：表示这是一个属性建，key进栈
                    keys.push(builder.toString());
                    builder = new StringBuilder();
                    break;
                case '[':
                    islist.push(true);
                    lists.push(new ArrayList());
                    break;
                case ',':
                    //这是一个分割，因为可能是简单地string的键值对，也有可能是string=map
                    //的键值对，因此valuetmp 使用object类型；
                    //如果valuetmp是null 应该是第一次，如果value不是空有可能是string，那是上一个键值对，需要重新赋值
                    //还有可能是map对象，如果是map对象就不需要了

                    boolean listis = islist.peek();

                    if (builder.length() > 0)
                        valuetmp = builder.toString();
                    builder = new StringBuilder();
                    if (!listis) {
                        keytmp = keys.pop();
                        maps.peek().put(keytmp, valuetmp);
                    } else
                        lists.peek().add(valuetmp);

                    break;
                case ']':
                    islist.pop();

                    if (builder.length() > 0)
                        valuetmp = builder.toString();
                    builder = new StringBuilder();
                    lists.peek().add(valuetmp);
                    valuetmp = lists.pop();
                    break;
                case '}':
                    islist.pop();
                    //这里做的和，做的差不多，只是需要把valuetmp=maps.pop();把map弹出栈
                    keytmp = keys.pop();

                    if (builder.length() > 0)
                        valuetmp = builder.toString();

                    builder = new StringBuilder();
                    maps.peek().put(keytmp, valuetmp);
                    valuetmp = maps.pop();
                    break;
                default:
                    builder.append(cs[i]);
                    break;
            }
        }
        return valuetmp;
    }

    public static List<WebsiteConfigPO> getConfig(File configFile){
        List<WebsiteConfigPO> configList = new ArrayList<>();
        FileReader fileReader = new FileReader(configFile);
        String json = fileReader.readString();
        configList = JSON.parseArray(json,WebsiteConfigPO.class);
        return configList;
    }
}
