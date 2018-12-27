package com.dwms.user.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import com.dwms.common.annotation.ExportConfig;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "tb_user_info")
@Data
public class User {
	
	/**
	 * 状态
	 */
	public static final int STATUS_VALID = 1;
	public static final int STATUS_LOCK = 0;
	
    /**
     * 用户id
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT REPLACE(UUID(),'-','')")
    //@GeneratedValue(generator="UUID")
    @ExportConfig(value = "编号")
    private String userId;

    /**
     * 用户姓名
     */
    @ExportConfig(value = "用户姓名")
    private String userName;

    /**
     * 手机号
     */
    @ExportConfig(value = "手机号")
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 性别
     */
    @ExportConfig(value = "性别", convert = "s:0=未知,1=男,2=女")
    private Integer gender;

    /**
     * 党员类型 1申请入党人 2入党积极分子 3发展对象 4预备党员 5正式党员
     */
    @ExportConfig(value = "党员类型", convert = "s:1=申请入党人,2=入党积极分子,3=发展对象,4=预备党员,5=正式党员")
    private Integer partyType;

    /**
     * 工作状态 0离职 1在岗 2在职
     */
    @ExportConfig(value = "工作状态", convert = "s:0=离职,1=在岗,1=在职")
    private Integer workStatus;

    /**
     * 党内职务 1普通党员 2支部委员 3支部副书记 4支部书记
     */
    @ExportConfig(value = "党内职务")
    private Integer partyPosts;

    /**
     * 转为预备党员日期
     */
    @ExportConfig(value = "转为预备党员日期", convert = "c:com.dwms.common.util.poi.convert.DateConvert")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date probationaryTime;

    /**
     * 转为正式党员日期
     */
    @ExportConfig(value = "转为正式党员日期", convert = "c:com.dwms.common.util.poi.convert.DateConvert")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fullTime;

    /**
     * 状态 0禁用 1启用
     */
    @ExportConfig(value = "状态", convert = "s:0=禁用,1=启用")
    private Integer status;

    /**
     * 党支部ID
     */
    private Integer partyId;
    
    /**
     * 创建时间
     */
    @ExportConfig(value = "创建时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date createTime;

    /**
     * 修改时间
     */
    @ExportConfig(value = "修改时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date updateTime;

    /**
     * 
     */
    private String source;
}