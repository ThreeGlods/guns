package cn.stylefeng.guns.core.util;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.entity.Importance;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.guns.modular.system.service.ImportanceService;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.net.ssl.SSLServerSocket;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * LDAP链接域获取信息
 * @author ThreeGlods
 * @date 2019/10/23
 */

public class LdapUtil {

    @Autowired
    private UserService userService;
    public  UserDto link(String userName, String password, Importance importance, String id) {

        try {
            String ip = importance.getIp();
            String suffix = importance.getSuffix();
            int port = importance.getPort();
            String adminname = userName + suffix;
            // 使用UPN格式：User@domain或SamAccountName格式：domain\\User
            String adminName = adminname;
            String adminPassword = password;// password
            System.out.println("账号："+adminName);
            System.out.println("密码："+adminPassword);

            String ldapURL = "LDAP://" + ip + ":" + port;// ip:port
            //String ldapURL = "LDAP://10.0.1.101:389";
            System.out.println("ldap:" + ldapURL);
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP访问安全级别："none","simple","strong"
            env.put(Context.SECURITY_PRINCIPAL, adminName);// AD User
            env.put(Context.SECURITY_CREDENTIALS, adminPassword);// AD Password
            env.put(Context.PROVIDER_URL, ldapURL);// LDAP工厂类


            LdapContext ctx = new InitialLdapContext(env, null);
            // 搜索控制器
            SearchControls searchCtls = new SearchControls();
            // 创建搜索控制器
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            // LDAP搜索过滤器类，此处只获取AD域用户，所以条件为用户user或者person均可
            String searchFilter = "objectCategory=person";
            // AD域节点结构
            String searchBase = "dc=" + id + ",dc=com";

            String returnedAtts[] = {"name", "telephoneNumber",
                    "department", "sAMAccountName", "distinguishedName",
                    "userPrincipalName"}; // 定制返回属性
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);


            String s1 = null;   //中文名
            String t1 = null;   //电话
            String tmp = "";  //部门
            while (answer.hasMoreElements()) {

                SearchResult sr = (SearchResult) answer.next();
                // System.out.println("<<<::[" + sr.getName() +
                // "]::>>>>");// 返回格式一般是CN=xxxx,OU=xxxx
                Attributes Attrs = sr.getAttributes();// 得到符合条件的属性集
                if (Attrs != null) {
                    for (NamingEnumeration ne = Attrs.getAll(); ne
                            .hasMore(); ) {
                        Attribute Attr = (Attribute) ne.next();// 得到下一个属性
                        // System.out.print(Attr.getID().toString() + ":");
                        // 读取属性值
                        for (NamingEnumeration e = Attr.getAll(); e
                                .hasMore(); ) {
                            e.next().toString();
                            // System.out.print(userInfo);
                        }
                    }
                    String sAMAccountName = Attrs.get("sAMAccountName")
                            .toString();

                    if (sAMAccountName
                            .equals("sAMAccountName: " + userName)) {

                        System.out.println("身份验证成功!");
                        String name = Attrs.get("name").toString();

                        String s[] = name.split("[:]"); // 拆分格式获得中文姓名
                        s1 = s[1].replaceAll(" ", "");// 去掉首尾和中间空格
                        if (Attrs.get("telephoneNumber") != null) { // 判断不等于空
                            String telephoneNumber = Attrs.get(
                                    "telephoneNumber").toString(); // 获取手机号
                            String t[] = telephoneNumber.split("[:]");
                            t1 = t[1].replaceAll(" ", "");// 去掉首尾和中间空格
                            /*ServletActionContext.getContext().getSession()
                                    .put("telephoneNumber", t1);*/
                        }
                        if (Attrs.get("distinguishedName") != null) { // 判断不等于空
                            String distinguishedName = Attrs.get(
                                    "distinguishedName").toString(); // 获取部门
                            String d[] = distinguishedName.split("[:]");

                            String bm[] = d[1].split("[,]");
                            if (bm.length > 3) {
                                List list = new ArrayList();
                                for (int m = 1; m <= (bm.length - 3); m++) {
                                    list.add(StringUtils.substringAfter(
                                            bm[m], "="));
                                }
                                for (int n = (list.size() - 1); n >= 0; n--) {
                                    if (n != 0) {
                                        tmp += (String) list.get(n) + "-";
                                    } else if (n == 0) {
                                        tmp += (String) list.get(n);
                                    }
                                }
                               /* ServletActionContext.getContext()
                                        .getSession()
                                        .put("departments", tmp);*/
                                /*
                                 * 创建用户用于保存sql
                                 *
                                 *
                                 */


                            }

                            Date nDate = new Date();
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = sf.parse(sf.format(nDate));     //字符串类型转化为date类型

                            UserDto user = new UserDto();
                            user.setAccount(userName);  //登录名、英文名
                            user.setPassword(password); //密码
                            user.setRoleId("1189377507298545665");    //设置关联的id
                            user.setSex("M");   //设置性别
                            user.setBirthday(date); //设置出生日期
                            user.setDeptId(24L);   //设置部门
                            user.setDepartment(tmp);    //部门
                            user.setName(s1);   //中文名
                            user.setPhone(t1);  //手机号
                            user.setEmail(userName + "@tairanchina.com"); //邮箱
                            return user;
                        }
                    }
                }
                ctx.close();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


}
