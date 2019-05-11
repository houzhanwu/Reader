package util;

/**
 * 对String的工具类
 * create at 2019-04-18 by MaXin
 */
public class StringUtil {
    /**
     * 判断是否是空字符串，如果为null，或者是""返回true
     * @param string
     * @return
     */
    public static boolean isNull (String string){
        return string == null ? true : "".equals(string);
    }

}
