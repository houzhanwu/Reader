package controller;

import com.alibaba.fastjson.JSONObject;
import entity.NovelPO;
import entity.WebsiteNovelPO;
import service.NovelService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 获取目录列表的servlet
 * create at 2019-05-12 by MaXin
 */
public class CatalogServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String book_id = req.getParameter("book_id");
        String website_id = req.getParameter("website_id");
        Long bookId = Long.valueOf(book_id);
        Long websiteId = Long.valueOf(website_id);
        PrintWriter out = resp.getWriter();
        WebsiteNovelPO chapter = NovelService.findCatalogById(bookId, websiteId);
        String json = chapter.getChapter();
        out.println(json);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
