package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.entity.Demand;
import cn.stylefeng.guns.modular.system.mapper.DemandMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ThreeGlods
 * @date 2019/10/15
 */
@Service
public class DemandService extends ServiceImpl<DemandMapper, Demand> {

    /**
     * 获取通知列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:05 PM
     */

    /**
     * 获取通知列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:05 PM
     */
    public Page<Map<String, Object>> list(String condition) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, condition);

    }


    public Demand getByCreateUser(Long createUser) {
        System.out.println(createUser);

        return this.baseMapper.getByCreateUser(createUser);
    }
}