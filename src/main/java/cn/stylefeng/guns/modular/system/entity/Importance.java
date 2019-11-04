package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author ThreeGlods
 * @date 2019/10/23
 */

@TableName("sys_importance")
public class Importance {

    /**
     *
     * 主键id
     */
    @TableId(value = "imp_id",type = IdType.ID_WORKER)
    private Long id;

    @TableField("ip")
    private String ip;

    @TableField("suffix")
    private String suffix;

    @TableField("port")
    private int port;

    @TableField("interfaces")
    private String interfaces;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public String toString() {
        return "Importance{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", suffix='" + suffix + '\'' +
                ", port=" + port +
                ", interfaces='" + interfaces + '\'' +
                '}';
    }
}
