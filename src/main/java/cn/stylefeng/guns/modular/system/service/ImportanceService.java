package cn.stylefeng.guns.modular.system.service;


import cn.stylefeng.guns.modular.system.entity.Importance;
import cn.stylefeng.guns.modular.system.mapper.ImportanceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ThreeGlods
 * @date 2019/10/25
 */

@Service
public class ImportanceService  extends ServiceImpl<ImportanceMapper, Importance> {
    public Importance findimporById(long id){
        return this.baseMapper.findimporById(id);
    }
}
