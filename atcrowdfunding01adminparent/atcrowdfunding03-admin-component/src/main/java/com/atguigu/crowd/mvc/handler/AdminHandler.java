package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.ccctop.crowd.constant.CrowdConstant;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/save.html")
    public String save(Admin admin)
    {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html";
    }
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword)
    {
        //执行删除
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html";
    }
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
                               //使用@requestParam注解的defaultValue属性,指定默认值,在请求中
                               //没有携带对应参数时使用默认值
                               //keyword默认值使用空字符串和sql语句配合实现两种情况适配
                              @RequestParam(value = "keyword",defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              ModelMap modelMap)
    {
        //调用service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword,pageNum,pageSize);

        //将pageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session)
    {
        //强制Session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session)
    {
        //调用Service方法
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        //Forword和redirect不能直接访问web-inf的地址，需要经过mvc处理
        //为了避免跳转到后台页面再刷新导致重复提交登录表单，重定向到目标页面
        return "redirect:/admin/to/main/page.html";
    }
}
