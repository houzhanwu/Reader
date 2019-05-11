package dao;

import entity.WebsiteConfigPO;

import java.sql.SQLException;
import java.util.List;

/**
 * 操作website_config表，类中只实现原子性操作，不进行逻辑判断、数据有效性判断等操作
 * create at 2019-04-16 by MaXin
 */
public interface WebsiteConfigDao {

    /**
     * 插入一个新的网站配置，
     * @param website
     * @return
     */
    boolean insert(WebsiteConfigPO website) throws SQLException;

    /**
     * 插入一系列的网站配置
     * @param list
     * @return
     * @throws SQLException
     */
    boolean insertList(List<WebsiteConfigPO> list) throws SQLException;

    /**
     * 更新一个网站的配置信息
     * @param website
     * @return
     */
    boolean update(WebsiteConfigPO website) throws SQLException;

    /**
     * 通过ID查找网站配置信息
     * @param id
     * @return 如没有查询到，则返回null
     */
    WebsiteConfigPO findByID(Long id) throws SQLException;

    /**
     * 返回所有网站的配置
     * @return 如果数据表中没有数据，则返回的list的size为0
     * @throws SQLException
     */
    List<WebsiteConfigPO> getAll() throws SQLException;

    /**
     * 返回所有的网站的网址的正则信息，websiteconfigDTO类中只包含：website/website_name/website_id
     * @return
     * @throws SQLException
     */
    List<WebsiteConfigPO> getAllWebsite() throws SQLException;
}
