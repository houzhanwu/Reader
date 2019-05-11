package dao;

import dbc.ConnectFactory;
import entity.WebsiteNovelPO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * create at 2019-04-21 by MaXin
 */
public class WebsiteNovelDaoImplByMybatis implements WebsiteNovelDao {

    private Long websiteId;
    private SqlSession session;
    private static final String NAMESPACE = "WebsiteNovel.";
    private final static Logger logger = LoggerFactory.getLogger(WebsiteNovelDaoImplByMybatis.class);
    private boolean exist = false;
    private final static String TABLE_PRE_NAME = "website_novel_";
    private final String tableName ;

    public WebsiteNovelDaoImplByMybatis(Long websiteId, SqlSession session) {
        this.websiteId = websiteId;
        this.session = session;
        tableName = TABLE_PRE_NAME + websiteId;
    }

    @Override
    public boolean createTable() throws SQLException {
        if (existTable())
            return true;
        logger.debug("创建表：" + tableName);
        int i = session.update(NAMESPACE+"createTable" , websiteId);
        exist = true;
        return true;
    }

    @Override
    public boolean existTable() throws SQLException {
        if (exist)
            return exist;
        Integer i = session.selectOne(NAMESPACE + "existTable", tableName);
        if (i == 1)
            exist = true;
        return exist;
    }

    @Override
    public boolean insert(WebsiteNovelPO websiteNovel) throws SQLException {
        if (!existTable())
            createTable();
        logger.debug("插入表" + tableName + "数据："+ websiteNovel.getNovelId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("websiteId",websiteId);
        map.put("novel",websiteNovel);
        int i = session.insert(NAMESPACE + "insert", map);
        return i > 0;
    }

    @Override
    public boolean insertList(List<WebsiteNovelPO> list) throws SQLException {
        if (!existTable())
            createTable();
        for (WebsiteNovelPO websiteNovel : list) {
            logger.debug("插入表" + tableName + "数据："+ websiteNovel.getNovelId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("websiteId",websiteId);
            map.put("novel",websiteNovel);
            int i = session.insert(NAMESPACE + "insert", map);
            if (i <= 0) return false;
        }
        return true;
    }

    @Override
    public boolean update(WebsiteNovelPO websiteNovel) throws SQLException {
        if (!existTable())
            createTable();
        logger.debug("更新表website_novel_" + websiteId + "数据："+ websiteNovel.getNovelId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("websiteId",websiteId);
        map.put("novel",websiteNovel);
        int i = session.update(NAMESPACE + "update" , map);
        return i > 0;
    }

    @Override
    public WebsiteNovelPO findByNovelId(Long id) throws SQLException {
        if (!existTable())
            createTable();
        logger.debug("查询表website_novel_" + websiteId + "数据："+ id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("websiteId",websiteId);
        map.put("novel_id",id);
        WebsiteNovelPO websiteNovelPO = session.selectOne(NAMESPACE + "findByNovelId" , map);
        return websiteNovelPO;
    }

}
