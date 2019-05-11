package dao;

import entity.UrlDTO;

import java.util.Collection;
import java.util.Set;

/**
 * 对Redis数据库的操作类
 * 需要知道的是，在Redis数据库中，0号数据库不存放数据，1号数据库存放获取到的页面信息
 * create at 2019-04-16 by MaXin
 */
public interface RedisDao {

    /**
     * 删除redis的所有数据，即清空redis
     * @return
     */
    void deleteAll();
    void deletePageDatabase();

    /**
     * 删除redis中存储的页面的所有数据
     * @return
     */
    void deleteAllPage(Long id);

    /**
     * 判断页面是否被访问过
     * @param url
     * @return
     */
    boolean existUrl(Long id, String url);

    /**
     * 存储已经访问过的页面
     * @param url
     * @return
     */
    void insertPage(Long id , String url);

    /**
     * 存储解析到的小说连接
     * @param websiteId website的ID
     * @param url
     * @return
     */
    boolean insertNovel(Long websiteId, String url);

    /**
     * 存储一系列的小说连接
     * @param websiteId
     * @param list
     * @return 返回新增的小说连接
     */
    Collection<UrlDTO> insertNovel(Long websiteId, Collection<UrlDTO> list);

    /**
     * 获取所有解析到的小说连接
     * @param websiteId
     * @return
     */
    Set<String> getAllNovel(Long websiteId);

    /**
     * 删除redis中存储的对应网站的解析到的小说信息
     * @return
     */
    boolean deleteAllNovel(Long id);
}
