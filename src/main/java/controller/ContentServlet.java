package controller;

import com.alibaba.fastjson.JSONObject;
import dao.DaoFactory;
import dao.NovelDao;
import dao.WebsiteConfigDao;
import dbc.ConnectFactory;
import entity.ContentDTO;
import entity.WebsiteConfigPO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.PageParse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * create at 2019-05-11 by MaXin
 */
public class ContentServlet extends HttpServlet {
    private final static Logger LOGGER = LoggerFactory.getLogger(ContentServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String website_id = req.getParameter("website_id");
        String url = req.getParameter("url");
        Long websiteId = Long.valueOf(website_id);

        SqlSession session = ConnectFactory.connectMysql();
        WebsiteConfigDao websiteConfigDao = DaoFactory.getWebsiteConfigDao(session);

        try {
            WebsiteConfigPO websiteConfigPO = websiteConfigDao.findByID(websiteId);
            PageParse pageParse = new PageParse(websiteConfigPO);
            ContentDTO contentDTO = pageParse.parseChapterPage(url);
            System.out.println(contentDTO);
            String json = JSONObject.toJSONString(contentDTO);
            out.println(json);
        } catch (SQLException e) {
            LOGGER.error("查找网站ID为：-》" +  website_id + "出错", e);
            out.println(e);
        } catch (Exception e) {
            LOGGER.error("解析网页：-》" +  url + "出错", e);
            out.println(e);
        }finally {
            session.close();
            out.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
