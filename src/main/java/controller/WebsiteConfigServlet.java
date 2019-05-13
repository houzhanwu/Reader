package controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.alibaba.fastjson.JSON;
import dao.DaoFactory;
import dao.WebsiteConfigDao;
import dbc.ConnectFactory;
import entity.WebsiteConfigPO;
import org.apache.ibatis.session.SqlSession;
import service.WebsiteConfigBO;
import spider.SpiderControllerSingle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * 开启爬虫 </br>
 * create at 2019-04-19 by MaXin
 */
public class WebsiteConfigServlet extends HttpServlet {

    private static boolean startSpider = false;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!startSpider){
            SqlSession session = ConnectFactory.connectMysql();
            try {
                WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);
                WebsiteConfigPO websiteConfigPO = null;
                try {
                    websiteConfigPO = websiteConfigDao.findByID(9l);
                    SpiderControllerSingle spiderControllerSingle = new SpiderControllerSingle(websiteConfigPO);
                    spiderControllerSingle.controlSpider();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }finally {
                session.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
