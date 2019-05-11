package dao;

import entity.WebsiteConfigPO;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * create at 2019-04-21 by MaXin
 */
public class WebsiteConfigDaoImpByMybatis implements WebsiteConfigDao {
    private SqlSession session;
    private static final String NAMESPACE = "WebsiteConfigDao.";

    public WebsiteConfigDaoImpByMybatis(SqlSession session) {
        this.session = session;
    }

    @Override
    public boolean insert(WebsiteConfigPO website) throws SQLException {
        int i = session.insert(NAMESPACE+"insert" , website);
        return i > 0;
    }

    @Override
    public boolean insertList(List<WebsiteConfigPO> list) throws SQLException {
        for (WebsiteConfigPO configPO : list) {
            int i = session.insert(NAMESPACE+"insert" , configPO);
            if (i <=0) return false;
        }
        return true;
    }



    @Override
    public boolean update(WebsiteConfigPO website) throws SQLException {
        int i = session.update(NAMESPACE + "update" , website);
        return i > 0;
    }

    @Override
    public WebsiteConfigPO findByID(Long id) throws SQLException {
        WebsiteConfigPO websiteConfig = session.selectOne(NAMESPACE+"findById" , id);
        return websiteConfig;
    }

    @Override
    public List<WebsiteConfigPO> getAll() throws SQLException {
        List<WebsiteConfigPO> list = session.selectList(NAMESPACE+"getAll");
        return list;
    }

    @Override
    public List<WebsiteConfigPO> getAllWebsite() throws SQLException {
        List<WebsiteConfigPO> list = session.selectList(NAMESPACE+"getAllWebsite");
        return list;
    }
}
