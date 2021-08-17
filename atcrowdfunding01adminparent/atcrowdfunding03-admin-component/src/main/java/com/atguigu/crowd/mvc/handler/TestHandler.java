package com.atguigu.crowd.mvc.handler;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.ccctop.crowd.util.CrowdUtil;
import com.ccctop.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);
    @Autowired
    private AdminService  adminService;
    //@RequestParam 用于controller层,是Spring的注解
    //@Param  用于dao层,是mybatis中的注解
    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array)
    {
        for (Integer number : array)
        {
            System.out.println("number="+number);
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(ParamData paramData)
    {
        List<Integer> array = paramData.getArray();
        logger.info("array="+array);
        //将查询到的Student对象封装到ResultEntity中返回
       return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/three.json")
    public ResultEntity<List<Integer>> testReceiveArrayTwo(@RequestBody List<Integer> array,HttpServletRequest request)
    {
        boolean judgResult = CrowdUtil.judgRequestType(request);
        logger.info("judgResult="+judgResult);
        for (Integer number : array)
        {
            logger.info("number="+number);
        }
        ResultEntity<List<Integer>> resultEntity = ResultEntity.successWithData(array);
        return resultEntity;
    }

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap, HttpServletRequest request)
    {
        boolean judgResult = CrowdUtil.judgRequestType(request);
        logger.info("judgResult="+judgResult);
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        //System.out.println(10 /0);
        String a = null;
        System.out.println(a.length());
        return "target";
    }

    @ResponseBody
    @RequestMapping("/test/ajax/async.html")
    public String testAsync() throws InterruptedException {
        Thread.sleep(2000);
        return "success";
    }

}
