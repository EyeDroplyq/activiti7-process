package com.lyq.activiti7Service;

import com.lyq.activiti7Service.pojo.SysUser;
import com.lyq.activiti7Service.mapper.SysUserMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lyq.activiti7Service.pojo.table.SysUserTableDef.SYS_USER;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-02 23:07
 * @version: 1.0
 */
@SpringBootTest
public class testDemo1 {
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private RepositoryService repositoryService;

    @Test
    public void test01(){
        QueryWrapper queryWrapper = QueryWrapper.create().select(SYS_USER.ALL_COLUMNS).from(SYS_USER).where(SYS_USER.USERNAME.eq("zd"));
        SysUser sysUser = userMapper.selectOneByQueryAs(queryWrapper, SysUser.class);
        System.out.println(sysUser);
    }
    @Test
    public void test02(){
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.modelKey("test001");
        List<Model> modelList = modelQuery.listPage(0, 10);
        System.out.println(modelList);
    }
}
