package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.Demand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface DemandMapper extends BaseMapper<Demand> {

    Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition);

    /**
    * 通过创建者查询信息
    * */
    Demand getByCreateUser(@Param("createUser") Long createUser );
}
