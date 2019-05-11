package dao;

import entity.WebsiteConfigPO;
import entity.WebsiteNovelPO;

import java.sql.SQLException;
import java.util.List;

/**
 * 操作每一个网站对应的数据表，此表存入的数据为每本书的链接
 * create at 2019-04-16 by MaXin
 */
public interface WebsiteNovelDao {

    /**
     * 创建对应的网站信息表
     * @return
     * @throws SQLException
     */
    boolean createTable() throws SQLException;

    /**
     * 网站ID对应的表是否存在
     * @return
     * @throws SQLException
     */
    boolean existTable() throws SQLException;

    /**
     * 插入数据
     * @param websiteNovel
     * @return
     * @throws SQLException
     */
    boolean insert(WebsiteNovelPO websiteNovel) throws SQLException;

    /**
     * 批量插入
     * @param list
     * @return
     * @throws SQLException
     */
    boolean insertList(List<WebsiteNovelPO> list) throws SQLException;

    /**
     * 更新数据
     * @param websiteNovel
     * @return
     * @throws SQLException
     */
    boolean update(WebsiteNovelPO websiteNovel) throws SQLException;

    /**
     * 通过novelID查找数据
     * @param id
     * @return
     * @throws SQLException
     */
    WebsiteNovelPO findByNovelId(Long id) throws SQLException;


}
