package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 表单
 * </p>
 *
 * @author ThreeGlods
 * @since 2019-10-11
 */

@TableName("sys_demand")
public class Demand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     * 主键id
     */
    @TableId(value = "demand_id",type = IdType.ID_WORKER)
    private Long demandId;

    /**
     * 父角色id
     */
    @TableField("pid")
    private long pid;

    /**
     * 姓名
     */
    @TableField("d_name")
    private String demandName;

    /**
     * 提示
     */
    @TableField("description")
    private String description;

    /**
     * 部门
     */
    @TableField("department")
    private String department;

    /**
     * 地点
     */
    @TableField("place")
    private String place;

    /**
     * 工程师
     */
    @TableField("engineer")
    private String engineer;

    /**
     * 状态
     */
    @TableField("state")
    private String state;


    /**
     * 描述
     */
    @TableField("phenomenon")
    private String phenomenon;

    /**
     * 异常
     */
    @TableField("exception")
    private String exception;

    /**
     * 分数
     */
    @TableField("score")
    private Integer score;

    /**
     * 建议
     */
    @TableField("advise")
    private  String advise;


    /**
     * 序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 乐观锁
     */
    @TableField("version")
    private Integer version;


    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     *修改时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     *用户权限
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private Long createUser;

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "demandId=" + demandId +
                ", pid=" + pid +
                ", name='" + demandName + '\'' +
                ", description='" + description + '\'' +
                ", department='" + department + '\'' +
                ", place='" + place + '\'' +
                ", engineer='" + engineer + '\'' +
                ", state='" + state + '\'' +
                ", phenomenon='" + phenomenon + '\'' +
                ", exception='" + exception + '\'' +
                ", score=" + score +
                ", advise='" + advise + '\'' +
                ", sort=" + sort +
                ", version=" + version +
                ", creatTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUser=" + createUser +
                '}';
    }
}
