package dbc;

import cn.hutool.core.io.resource.ClassPathResource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 通过c3p0连接池连接MySQL数据库
 * create at 2019-04-16 by MaXin
 */
public class ConnectMysqlByMybatis {

    private static final String RESOURCE = "mybatis/mybatis_config.xml";
    private static SqlSessionFactory factory ;
    static {
        InputStream inputStream = new ClassPathResource(RESOURCE).getStream();
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConnectMysqlByMybatis(){}

    public static SqlSession getSession (){
        return factory.openSession();
    }

    public static void closeSession(SqlSession session){
        session.close();
    }

    public static void main(String[] args) {
        SqlSession session = new ConnectMysqlByMybatis().getSession();
        System.out.println(session);
        session.close();
    }
}
