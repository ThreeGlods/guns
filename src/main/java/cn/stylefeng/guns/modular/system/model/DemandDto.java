package cn.stylefeng.guns.modular.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ThreeGlods
 * @date 2019/10/12
 */
@Data
public class DemandDto implements Serializable {

     private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long demandId;
    /**
     * 父角色id
     */
    private Long pid;
    /**
     * 角色名称
     */
    private String demandName;
    /**
     * 提示
     */
    private String description;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date createTime;

}
