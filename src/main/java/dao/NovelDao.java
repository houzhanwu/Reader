package dao;

import entity.NovelPO;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * novel表数据库操作接口
 */
public interface NovelDao {

    /**
     * 插入一本书
     * @param novel 传入书籍信息，只管对数据库的操作，不管数据的正确性
     * @return 插入成功返回true，失败返回false
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    Long doInsert(NovelPO novel) throws SQLException;

    /**
     * 插入较多书籍
     * @param novels 传入书籍列表信息，只管对数据库的操作，不管数据的正确性
     * @return 插入的成功的数量与列表的数量一样
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    boolean doInsert(List<NovelPO> novels) throws SQLException;

    /**
     * 根据ID修改对应行的全部数据
     * @param novel 传入书籍信息，只管对数据库的操作，不管数据的正确性
     * @return 修改成功返回true，失败返回false
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    boolean doUpdate(NovelPO novel) throws SQLException;

    /**
     * 根据ID修改对应行的全部数据
     * @param novels 传入书籍信息，只管对数据库的操作，不管数据的正确性
     * @return 修改成功返回true，失败返回false
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    boolean doUpdate(Set<NovelPO> novels) throws SQLException;

    /**
     * 根据给定的ID执行删除操作
     * @param id 传入书籍ID信息，只管对数据库的操作，不管数据的正确性
     * @return 删除成功返回true，失败返回false
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    boolean doDelete(Long id) throws SQLException;

    /**
     * 根据给定的ID集合删除对应数据
     * @param ids 传入要删除的书籍ID列表信息，只管对数据库的操作，不管数据的正确性
     * @return 删除成功返回true，失败返回false
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    boolean doDelete(Set<Long> ids) throws SQLException;

    /**
     * 根据ID查询对应的文章信息
     * @param id 传入书籍ID信息，只管对数据库的操作，不管数据的正确性
     * @return 如果查询到以NovelDO形式返回，否则返回null
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    NovelPO findById(Long id) throws SQLException;

    /**
     * 根据标题名称模糊查询
     * @param title 传入要查询的小说标题，进行模糊查询
     * @return 如果查询到数据，以List集合形式返回，否则返回的List集合长度为0
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    List<NovelPO> findByTitle(String title) throws SQLException;

    /**
     * 根据作者名称模糊查询
     * @param author 传入要查询的小说标题，进行模糊查询
     * @return 如果查询到数据，以List集合返回，否则返回一个长度为0的List集合
     * @throws SQLException 抛出SQL执行异常，交由业务层处理
     */
    List<NovelPO> findByAuthor(String author) throws SQLException;

    /**
     * 获取所有的类别
     * @return 如果查询到数据，以List集合返回，否则返回一个长度为0的List集合
     * @throws SQLException
     */
    List<String> getAllCategory() throws SQLException;

    /**
     * 通过类别获取书籍
     * @param category
     * @return 如果查询到数据，以List集合返回，否则返回一个长度为0的List集合
     * @throws SQLException
     */
    List<NovelPO> findByCategory(String category) throws SQLException;

    /**
     * 获取所有的书籍
     * @return
     * @throws SQLException
     */
    List<NovelPO> getAll() throws SQLException;

    /**
     * 获取所有没有被删除的小说
     * @return
     * @throws SQLException
     */
    List<NovelPO> getAllNotDelete() throws SQLException;

    /**
     * 通过判断小说名与作者是否存在
     * @param title
     * @param author
     * @return 如果存在返回ID，不存在返回null
     * @throws SQLException
     */
    Long existByTitleAndAuthor (String title, String author) throws SQLException;

    /**
     * 通过判断小说名与作者是否存在
     * @param novel
     * @return 如果存在返回ID，不存在返回null
     * @throws SQLException
     */
    Long existByNovelPO (NovelPO novel) throws SQLException;
}
