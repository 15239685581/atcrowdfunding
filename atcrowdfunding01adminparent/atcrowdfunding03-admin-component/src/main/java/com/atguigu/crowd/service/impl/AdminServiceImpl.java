package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.ccctop.crowd.constant.CrowdConstant;
import com.ccctop.crowd.exception.LoginFaildException;
import com.ccctop.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


//查找实现类快捷键ctrl+alt+B
//ctrl+h
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        //1 密码加密
        String userPswd = CrowdUtil.md5(admin.getUserPswd());
        admin.setUserPswd(userPswd);
        //2 生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);
        adminMapper.insert(admin);
        throw new RuntimeException();
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        //1 根据登录账号查询Admin对象
        //1.1 创建adminExample对象
        AdminExample adminExample = new AdminExample();
        //1.2 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        //1.3 在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        //1.4 调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        //2 判断Admin对象是否为null
        if (list == null || list.size() == 0)
        {
            throw new LoginFaildException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (list.size() >1)
        {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = list.get(0);
        //3 如果Admin对象为null则抛出异常
        if (admin == null)
        {
            throw new LoginFaildException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //4 如果Admin对象不为null则将数据库密码从admin对象中取出
        String userPswdDB = admin.getUserPswd();
        //5 将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        //6 对密码进行比较
        if (!Objects.equals(userPswdDB,userPswdForm))
        {
            //7 如果比较结构是不一致，则抛出异常
            throw new LoginFaildException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //8 如果一致则返回Admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        //1 调用Pagehelper的静态方法开启分页功能
        //这里充分体现了pageHelper的 非侵入式设计:原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum,pageSize);
        //2 执行查询
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);
        //3 封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }
}
