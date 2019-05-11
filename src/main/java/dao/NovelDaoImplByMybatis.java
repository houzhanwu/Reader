package dao;

import dbc.ConnectFactory;
import entity.NovelPO;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通过mybatis实现对novel的操作
 * create at 2019-04-21 by MaXin
 */
public class NovelDaoImplByMybatis implements NovelDao{

    private SqlSession session;
    private static final String NAMESPACE = "NovelDao.";

    public NovelDaoImplByMybatis(SqlSession session) {
        this.session = session;
    }


    @Override
    public Long doInsert(NovelPO novel) throws SQLException {
        int i = session.insert(NAMESPACE + "insert", novel);
        if (i > 0)
            return novel.getNovelId();
        return null;
    }

    @Override
    public boolean doInsert(List<NovelPO> novels) throws SQLException {
        for (NovelPO novel : novels) {
            int i = session.insert(NAMESPACE + "insert", novel);
            if (i <= 0) return false;
        }
        return true;
    }

    @Override
    public boolean doUpdate(NovelPO novel) throws SQLException {
        int i = session.update(NAMESPACE + "update" , novel);
        return i > 0;
    }

    @Override
    public boolean doUpdate(Set<NovelPO> novels) throws SQLException {
        for (NovelPO novel : novels) {
            int i = session.update(NAMESPACE + "update" , novel);
            if (i <= 0) return false;
        }
        return true;
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        int i = session.update(NAMESPACE + "deleteById" , id);
        return i > 0 ;
    }

    @Override
    public boolean doDelete(Set<Long> ids) throws SQLException {
        for (Long id : ids) {
            int i = session.update(NAMESPACE + "deleteById" , id);
            if (i <= 0) return false;
        }
        return true;
    }

    @Override
    public NovelPO findById(Long id) throws SQLException {
        NovelPO novel = session.selectOne(NAMESPACE + "findById" , id);
        return novel;
    }

    @Override
    public List<NovelPO> findByTitle(String title) throws SQLException {
        title = '%'+title+'%';
        List<NovelPO> list = session.selectList(NAMESPACE + "findByTitle" , title);
        return list;
    }

    @Override
    public List<NovelPO> findByAuthor(String author) throws SQLException {
        author = '%' + author + '%';
        List<NovelPO> list = session.selectList(NAMESPACE + "findByAuthor" , author);
        return list;
    }

    @Override
    public List<String> getAllCategory() throws SQLException {
        List<String> list = session.selectList(NAMESPACE + "getAllCategory");
        return list;
    }

    @Override
    public List<NovelPO> findByCategory(String category) throws SQLException {
        List<NovelPO> list = session.selectList(NAMESPACE + "findByCategory",category);
        return list;
    }

    @Override
    public List<NovelPO> getAll() throws SQLException {
        List<NovelPO> list = session.selectList(NAMESPACE+"getAll");
        return list;
    }

    @Override
    public List<NovelPO> getAllNotDelete() throws SQLException {
        List<NovelPO> list = session.selectList(NAMESPACE+"getAllNotDelete");
        return list;
    }

    @Override
    public Long existByTitleAndAuthor(String title, String author) throws SQLException {
        Map<String , String> map = new HashMap<>();
        map.put("title", title);
        map.put("author", author);
        Long id = session.selectOne(NAMESPACE+"existByTitleAndAuthor", map);
        return id;
    }

    @Override
    public Long existByNovelPO(NovelPO novel) throws SQLException {
        Long id = session.selectOne(NAMESPACE + "existByNovelPO", novel);
        return id;
    }

}
