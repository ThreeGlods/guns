package cn.stylefeng.guns.modular.system.warpper;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.util.DecimalUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author ThreeGlods
 * @date 2019/10/15
 */

public class DemandWrapper extends BaseControllerWrapper {


    public DemandWrapper(Map<String, Object> single) {
        super(single);
    }

    public DemandWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public DemandWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public DemandWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {

    }
}
