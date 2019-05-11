package controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.alibaba.fastjson.JSON;
import entity.WebsiteConfigPO;
import service.WebsiteConfigBO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 网站配置的servlet
 * create at 2019-04-19 by MaXin
 */
public class WebsiteConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");
//        resp.setCharacterEncoding("utf-8");
//        List<WebsiteConfigPO> allWebsiteConfig = WebsiteConfigBO.findAllWebsiteConfig();
//        String json = JSON.toJSONString(allWebsiteConfig);
//        out.println(json);
//        ICaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
//        OutputStream out = resp.getOutputStream();
//        captcha.write(out);
//        String code = captcha.getCode();
//        System.out.println(code);
//        out.close();

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
