package dbc;

import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;

/**
 * 连接工厂类
 * create at 2019-04-22 by MaXin
 */
public class ConnectFactory {
    public static SqlSession connectMysql(){
        return ConnectMysqlByMybatis.getSession();
    }

    public static Jedis connectRedis (){
        return ConnectRedis.getConnection();
    }
}
