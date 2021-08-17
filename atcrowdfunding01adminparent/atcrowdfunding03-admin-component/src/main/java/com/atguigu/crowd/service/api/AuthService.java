package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Auth;
import com.ccctop.crowd.util.ResultEntity;

import java.util.List;
import java.util.Map;

public interface AuthService {

    List<Auth> getAll();

    List<Integer> getAssigndAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
