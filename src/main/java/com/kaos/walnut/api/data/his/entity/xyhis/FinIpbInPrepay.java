package com.kaos.walnut.api.data.his.entity.xyhis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.his.entity.xyhis.DawnOrgDept.DeptOwnEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.IntegerUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_IPB_INPREPAY")
public class FinIpbInPrepay {
    /**
     * 住院流水号 {@link FinIpbInPrepay.AssociateEntity#inMainInfo}
     */
    @TableId("INPATIENT_NO")
    private String inpatientNo;

    /**
     * 发生序号
     */
    @TableId("HAPPEN_NO")
    private Integer happenNo;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 预交金额
     */
    @TableField("PREPAY_COST")
    private Double prepayCost;

    /**
     * 支付方式
     */
    @TableField("PAY_WAY")
    private PayWayEnum payWay;

    /**
     * 科室代码
     */
    @TableField("DEPT_CODE")
    private String deptCode;

    /**
     * 预交金收据号码
     */
    @TableField("RECEIPT_NO")
    private String receiptNo;

    /**
     * 统计日期
     */
    @TableField("STAT_DATE")
    private LocalDateTime statDate;

    /**
     * 结算时间
     */
    @TableField("BALANCE_DATE")
    private LocalDateTime balanceDate;

    /**
     * 预交金状态
     */
    @TableField("PREPAY_STATE")
    private PrepayStateEnum prepayState;

    /**
     * 原票据号
     */
    @TableField("OLD_RECIPENO")
    private String oldRecipeNo;

    /**
     * 开户银行
     */
    @TableField("OPEN_BANK")
    private String openBank;

    /**
     * 开户帐户
     */
    @TableField("OPEN_ACCOUNTS")
    private String openAccounts;

    /**
     * 结算发票号
     */
    @TableField("INVOICE_NO")
    private String invoiceNo;

    /**
     * 结算序号
     */
    @TableField("BALANCE_NO")
    private Integer balanceNo;

    /**
     * 结算人代码
     */
    @TableField("BALANCE_OPERCODE")
    private String balanceOperCode;

    /**
     * 上缴标志（1是 0否）
     */
    @TableField("REPORT_FLAG")
    private Boolean reportFlag;

    /**
     * 审核序号
     */
    @TableField("CHECK_NO")
    private String checkNo;

    /**
     * 财务组代码
     */
    @TableField("FINGRP_CODE")
    private String finFrpCode;

    /**
     * 工作单位
     */
    @TableField("WORK_NAME")
    private String workName;

    /**
     * 0非转押金，1转押金，2转押金已打印
     */
    @TableField("TRANS_FLAG")
    private String transFlag;

    /**
     * 转押金时结算序号
     */
    @TableField("CHANGE_BALANCE_NO")
    private Integer changeBalanceNo;

    /**
     * 转押金结算员
     */
    @TableField("TRANS_CODE")
    private String transCode;

    /**
     * 转押金时间
     */
    @TableField("TRANS_DATE")
    private LocalDateTime transDate;

    /**
     * 打印标志
     */
    @TableField("PRINT_FLAG")
    private Boolean printFlag;

    /**
     * 正常收取 1 结算召回 2
     */
    @TableField("EXT_FLAG")
    private String extFlag;

    /**
     * 日结标志 0未日结 1日结
     */
    @TableField("EXT1_FLAG")
    private String ext1Flag;

    /**
     * pos交易流水号或支票号或汇票号
     */
    @TableField("POSTRANS_NO")
    private String posTransNo;

    /**
     * 操作员
     */
    @TableField("OPER_CODE")
    private String operCode;

    /**
     * 操作日期
     */
    @TableField("OPER_DATE")
    private LocalDateTime operDate;

    /**
     * 操作员科室
     */
    @TableField("OPER_DEPTCODE")
    private String operDeptCode;

    /**
     * 存交易的原始信息
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 职员院区标识
     */
    @TableField("DEPTOWN")
    private DeptOwnEnum deptOwn;

    /**
     * 职员院区标识
     */
    @TableField("EMPLOYEEOWN")
    private DeptOwnEnum employeeOwn;

    /**
     * 自助机交易订单号
     */
    @TableField("REFERNUM")
    private String referNum;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbInPrepay) {
            var that = (FinIpbInPrepay) arg0;
            return StringUtils.equals(this.inpatientNo, that.inpatientNo)
                    && IntegerUtils.equals(this.balanceNo, that.balanceNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(inpatientNo, balanceNo);
    }

    /**
     * 预交金支付方式字典
     */
    @Getter
    @AllArgsConstructor
    public enum PayWayEnum implements Enum {
        现金("CA", "现金"),
        支票("CH", "支票"),
        信用卡("CD", "信用卡"),
        借记卡("DB", "借记卡"),
        转押金("AJ", "转押金"),
        汇票("PO", "汇票"),
        保险帐户("PS", "保险帐户"),
        院内账户("YS", "院内账户"),
        东风卡("DFK", "东风卡"),
        日间手术账户("RJSH", "日间手术账户"),
        微信("WX", "微信"),
        支付宝("ZFB", "支付宝");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 预交金状态字典
     */
    @Getter
    @AllArgsConstructor
    public enum PrepayStateEnum implements Enum {
        收取("0", "收取"),
        作废("1", "作废"),
        补打("2", "补打"),
        结算召回作废("3", "结算召回作废");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}
