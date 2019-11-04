package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.Importance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;



public interface ImportanceMapper extends BaseMapper<Importance> {
    Importance findimporById(@Param("id") Long id);
}
