package com.kaos.walnut.api.data.his.entity.xyhis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_OPR_PAYMODEL")
public class FinOprPayModel {
    /**
    * 
    */
    @TableField("CLINIC_CODE")
    String clinicCode;

    /**
     * 
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 
     */
    @TableField("NAME")
    String name;

    /**
     * 
     */
    @TableField("OPER_CODE")
    String operCode;

    /**
     * 
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    /**
     * 
     */
    @TableField("PAYTYPE")
    PayTypeEnum payType;

    /**
     * 
     */
    @TableField("TRADECODE")
    String tradeCode;

    /**
     * 
     */
    @TableField("AMOUNT")
    Double amt;

    /**
     * 银行卡号
     */
    @TableField("TRANCARDNUM")
    String bankCardNo;

    /**
     * 银行名称
     */
    @TableField("BANKNO")
    String bankNo;

    /**
     * 终端号
     */
    @TableField("TERMID")
    String termNo;

    /**
     * 交易检索参考号
     */
    @TableField("REFERNUM")
    String referNum;

    /**
     * 交易终端流水号(用作挂号交易流水号)
     */
    @TableField("TRACENUM")
    String traceNum;

    /**
     * TRDATETIME
     */
    @TableField("TRDATETIME")
    LocalDateTime trDateTime;

    /**
     * 交易商户号 （用作挂号交易支付流水号）
     */
    @TableField("MERCHANTNUM")
    String merchantNum;

    /**
     * 持卡人姓名
     */
    @TableField("BANKNAME")
    String bankName;

    /**
     * 批次号
     */
    @TableField("BATCHNUM")
    String batchNum;

    /**
     * 卡有效期
     */
    @TableField("EXPIRYDATE")
    String expireDate;

    /**
     * 授权码
     */
    @TableField("AUTHCODE")
    String authCode;

    /**
     * 原交易检索号
     */
    @TableField("ORGREFERNUM")
    String orgReferNum;

    /**
     * 发票号
     */
    @TableField("EXT1")
    String ext1;

    /**
     * 跑批回写状态(1:跑批成功;0或空:未跑批)
     */
    @TableField("EXT2")
    String ext2;

    /**
     * 是否跑批(空或0:不跑批;1:需跑批)
     */
    @TableField("EXT3")
    String ext3;

    /**
     * 1-正交易 2-负交易
     */
    @TableField("EXT4")
    String ext4;

    /**
     * 1:现场挂号; 2:预约挂号; 3:门诊收费; -3:门诊退费;4:住院收费;负的为相应退号退费
     */
    @TableField("EXT5")
    String ext5;

    /**
     * 预约表主键
     */
    @TableField("REC_ID")
    String recId;

    /**
     * 流水号
     */
    @TableField("PAY_ID")
    String payId;

    /**
     * 银行类型:1-工行 2-招行 3-建行
     */
    @TableField("MERCHANTAG")
    String merchanTag;

    /**
     * 平台交易ID(源启状态回写用)
     */
    @TableField("TRANID")
    String tranId;

    /**
     * 交易发起方 1：后台跑批 2：窗口 3：自助
     */
    @TableField("PLACEFLAG")
    String placeFlag;

    /**
     * 是否已打印，打印为1
     */
    @TableField("IS_PRINT")
    Boolean isPrint;

    /**
     * 银医二期当日撤销标记(1:当日撤销)
     */
    @TableField("CANCELDAY")
    String cancelDay;

    /**
     * 平台结算号
     */
    @TableField("PINGTAIJSBH")
    String platformNo;

    /**
     * 平台结算号回写日期
     */
    @TableField("PINGTAIJSBHDATE")
    LocalDateTime platformDate;

    /**
     * 医院内收退费编号
     */
    @TableField("YUANNEIPAYNO")
    String innerPayNo;

    /**
     * 医院内收退费时间
     */
    @TableField("YUANNEIPAYTIME")
    LocalDateTime innerPayTime;

    /**
     * 有效标识，空为有效,2作废
     */
    @TableField("FLAG")
    String flag;

    /**
     * 作废时间
     */
    @TableField("FLAGDATE")
    LocalDateTime flagDate;

    /**
     * 结算操作员
     */
    @TableField("BALANCE_NO")
    String balanceNo;

    /**
     * 源起自助机平台交易IDxin
     */
    @TableField("POWERTRANID")
    String powerTranId;

    /**
     * 源起自助机-平台支付渠道xin
     */
    @TableField("POWERPAYCHANNEL")
    String powerPayChannel;

    /**
     * 源起自助机--机器号xin
     */
    @TableField("AOPER")
    String aoper;

    /**
     * 平台退费交易ID
     */
    @TableField("REFUNDORDERID")
    String refundOrderId;

    @Getter
    @AllArgsConstructor
    public enum PayTypeEnum implements Enum {
        现金0("0", "现金"),
        就诊卡("1", "就诊卡"),
        银行卡("2", "银行卡"),
        支付宝("3", "支付宝"),
        微信("4", "微信"),
        现金5("5", "现金"),
        医保卡6("6", "医保卡6"),
        医保卡7("7", "医保卡7"),
        其他("9", "其他"),
        建行("10", "建行"),
        weimaiAPP("12", "weimaiAPP"),
        医保线上支付("13", "医保线上支付");

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
