package cn.stylefeng.guns.core.common.constant.dictmap;

import cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * @author ThreeGlods
 * @date 2019/10/15
 */

public class DemandDict extends AbstractDictMap {
    @Override
    public void init() {
        put("demandId", "角色名称");
        put("sort", "角色排序");
        put("pid", "角色的父级");
        put("name", "角色名称");
        put("description", "备注");
        put("ids", "资源名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("pid", "getSingleDemandName");
        putFieldWrapperMethodName("deptId", "getDeptName");
        putFieldWrapperMethodName("demandId", "getSingleDemandName");
        putFieldWrapperMethodName("ids", "getMenuNames");
    }
}
