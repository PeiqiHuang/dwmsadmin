package com.dwms.due.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Table(name = "tb_due_account")
@Data
public class DueAccount {
    /**
     * 账户状态
     */
    public static final int STATUS_VALID = 1;
    public static final int STATUS_LOCK = 0;
    /**
     * 账户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accId;

    /**
     * 党费账户名称
     */
    private String accName;

    /**
     * 党支部id
     */
    private Integer partyId;

    /**
     * 微信支付图片
     */
    private String wechatFilePath;

    /**
     * 支付宝支付图片
     */
    private String alipayFilePath;
    
    @Transient
    private MultipartFile wechatImg;
    
    @Transient
    private MultipartFile alipayImg;

    /**
     * 收款方姓名
     */
    private String payeeName;

    /**
     * 收款方开户银行
     */
    private String payeeBank;

    /**
     * 收款方开户地址
     */
    private String payeeAddress;

    /**
     * 收款方账号
     */
    private String payeeAccount;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 注意事项
     */
    private String tips;

    /**
     * 状态 0屏蔽 1启用
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}