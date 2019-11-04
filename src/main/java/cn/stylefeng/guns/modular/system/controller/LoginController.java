/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.system.controller;


import cn.stylefeng.guns.core.common.node.MenuNode;
import cn.stylefeng.guns.core.log.LogManager;
import cn.stylefeng.guns.core.log.factory.LogTaskFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.LdapUtil;
import cn.stylefeng.guns.modular.system.entity.Importance;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.guns.modular.system.service.ImportanceService;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImportanceService importanceService;

    /**
     * 跳转到主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        //获取当前用户角色列表
        ShiroUser user = ShiroKit.getUserNotNull();
        List<Long> roleList = user.getRoleList();

        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }

        List<MenuNode> menus = userService.getUserMenuNodes(roleList);
        model.addAttribute("menus", menus);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }
    /**
     * 点击登录执行的动作
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */


    LdapUtil util = new LdapUtil();
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {
        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");
        String record = null;

        if (util.link(username,password, importanceService.findimporById(1),"hzdomain1")!=null&&record==null){
                token_admin(username,password,remember,1,"hzdomain1");
                record = "record";
        }else if(util.link(username,password, importanceService.findimporById(2),"hzdomain2")!=null&&record==null){
                token_admin(username,password,remember,2,"hzdomain2");
                record = "record";
        }else if(util.link(username,password, importanceService.findimporById(3),"TRGroup")!=null&&record==null){
                token_admin(username, password, remember, 3, "TRGroup");
                record = "record";
        }else if(record==null){
            Subject currentUser3 = ShiroKit.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            currentUser3.login(token);
            //登录成功，记录登录日志
            ShiroUser shiroUser1 = ShiroKit.getUserNotNull();
            LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser1.getId(), getIp()));
            ShiroKit.getSession().setAttribute("sessionFlag", true);
        }

        return REDIRECT + "/";
    }

    public  void token_admin(String username,String password,String remember,long id, String domain) {

        UserDto userDto = util.link(username, password, importanceService.findimporById(id), domain);
        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "tairan@2019");
        currentUser.login(token);
        if (userService.getByAccount(username) != null) {

            ShiroKit.getSubject().logout();
            deleteAllCookie();

            Subject currentUser1 = ShiroKit.getSubject();
            UsernamePasswordToken token1 = new UsernamePasswordToken(username, password.toCharArray());
            //如果开启了记住我功能
            if ("on".equals(remember)) {
                token1.setRememberMe(true);
            } else {
                token1.setRememberMe(false);
            }
            //执行shiro登录操作
            currentUser1.login(token1);

            //登录成功，记录登录日志
            ShiroUser shiroUser1 = ShiroKit.getUserNotNull();
            LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser1.getId(), getIp()));
            ShiroKit.getSession().setAttribute("sessionFlag", true);
        }else if (userService.getByAccount(username)==null){
            userService.addUser(userDto);
            ShiroKit.getSubject().logout();
            deleteAllCookie();

            Subject currentUser1 = ShiroKit.getSubject();
            UsernamePasswordToken token2 = new UsernamePasswordToken(username, password.toCharArray());

            //执行shiro登录操作
            currentUser1.login(token2);

            //登录成功，记录登录日志
            ShiroUser shiroUser2 = ShiroKit.getUserNotNull();
            LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser2.getId(), getIp()));
            ShiroKit.getSession().setAttribute("sessionFlag", true);
        }
    }

    /**
     * 退出登录
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUserNotNull().getId(), getIp()));
        ShiroKit.getSubject().logout();
        deleteAllCookie();
        return REDIRECT + "/login";
    }
}
