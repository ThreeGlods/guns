package cn.stylefeng.guns.modular.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.constant.dictmap.DeleteDict;
import cn.stylefeng.guns.core.common.constant.dictmap.NoticeMap;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.ChatbotSendUtil;
import cn.stylefeng.guns.modular.system.entity.Demand;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.service.DemandService;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.guns.modular.system.warpper.DemandWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author ThreeGlods
 * @date 2019/10/15
 */
@Controller
@RequestMapping("/demand")
public class DemandController extends BaseController {
    private static String PREFIX = "/modular/system/demand/";


    @Autowired
    private DemandService demandService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatbotSendUtil chatbotSendUtil;

    /**
     * 跳转到列表首页
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "demand.html";
    }

    /**
     * 跳转到添加通知
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/demand_add")
    public String demandAdd() {
        Long userId = ShiroKit.getUserNotNull().getId();
        User user = userService.getById(userId);
        user.getPlace1();
        user.getPlace2();
        user.getPlace3();
        String palce = user.getPlace1()+"-"+user.getPlace2()+"-"+user.getPlace3();
        super.setAttr("place",palce);
        return PREFIX + "demand_add.html";
    }

    /**
     * 跳转到修改通知
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/demand_update/{demandId}")
    public String demandUpdate(@PathVariable Long demandId, Model model) {
        Demand demand = this.demandService.getById(demandId);
        model.addAllAttributes(BeanUtil.beanToMap(demand));
        LogObjectHolder.me().set(demand);
        return PREFIX + "demand_edit.html";
    }


    /**
     * 跳转到修改通知
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/demand_updateD/{demandId}")
    public String demandUpdateE(@PathVariable Long demandId, Model model){
        Demand demand = this.demandService.getById(demandId);
        model.addAllAttributes(BeanUtil.beanToMap(demand));
        LogObjectHolder.me().set(demand);
        return PREFIX + "demand_details.html";
    }

    /**
     * 获取通知列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Map<String, Object>> list = this.demandService.list(condition);
        Page<Map<String, Object>> wrap = new DemandWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 新增需求
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "添加需求", key = "title", dict = NoticeMap.class)
    public Object add(Demand demand) {
        if (ToolUtil.isOneEmpty(demand)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Long userId = ShiroKit.getUserNotNull().getId();
        User user = this.userService.getById(userId);
        demand.setDepartment(demand.getDepartment());   //部门
        demand.setPhenomenon(demand.getPhenomenon());   //描述
        demand.setDemandName(user.getName());     //姓名
        demand.setCreateUser(ShiroKit.getUserNotNull().getId());    //创建者ID
        demand.setCreateTime(new Date());    //时间
        demand.setException(demand.getException());     //异常问题
        demand.setState("待处理");     //添加工单状态
        this.demandService.save(demand);
        chatbotSendUtil.ChatbotSend("测试"+"---"+"需求人："+user.getName());
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知", key = "demandId", dict = DeleteDict.class)
    public Object delete(@RequestParam Long demandId) {

        //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(demandId));
        this.demandService.removeById(demandId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    @BussinessLog(value = "修改需求", key = "title", dict = NoticeMap.class)
    public Object update(Demand demand) {
        if (ToolUtil.isOneEmpty(demand, demand.getDemandId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Demand old = this.demandService.getById(demand.getDemandId());
        if(old.getState().equals("待处理")){
            Long userId = ShiroKit.getUserNotNull().getId();
            User user = this.userService.getById(userId);
            String engineerName =  user.getName();
            old.setState("处理中");
            old.setEngineer(engineerName);
        }
        else if (old.getState().equals("处理中")){
            Long userId = ShiroKit.getUserNotNull().getId();
            User user = this.userService.getById(userId);
            String engineerName =  user.getName();


            old.setState("已完成");
            old.setEngineer(engineerName);
        }else {
            return SUCCESS_TIP;
        }
        this.demandService.updateById(old);
        return SUCCESS_TIP;
    }
}
