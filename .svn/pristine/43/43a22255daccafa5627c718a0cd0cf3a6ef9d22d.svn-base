package com.dwms.due.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "tb_due_info_account")
@Data
public class DueInfoAccount {
    /**
     * 账户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diaId;

    /**
     * 缴费id
     */
    private Integer dueId;

    /**
     * 党费账户id
     */
    private Integer accId;

    /**
     * 状态 0屏蔽 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

}