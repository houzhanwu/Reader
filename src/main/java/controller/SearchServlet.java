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
import java.util.List;

/**
 * 搜索的servlet
 * create at 2019-05-12 by MaXin
 */
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String title = req.getParameter("title");
        List<NovelPO> list = NovelService.findByTitle(title);
        String json = JSONObject.toJSONString(list);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.close();
    }
}
