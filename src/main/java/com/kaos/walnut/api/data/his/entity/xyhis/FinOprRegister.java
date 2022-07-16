package com.kaos.walnut.api.data.his.entity.xyhis;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.his.entity.xyhis.DawnOrgDept.DeptOwnEnum;
import com.kaos.walnut.api.data.his.enums.TransTypeEnum;
import com.kaos.walnut.api.data.his.enums.ValidEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.type.enums.SexEnum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_OPR_REGISTER")
public class FinOprRegister {
    /**
     * 
     */
    @TableId("CLINIC_CODE")
    @TableField("CLINIC_CODE")
    String clinicCode;

    /**
     * 
     */
    @TableId("TRANS_TYPE")
    @TableField("TRANS_TYPE")
    TransTypeEnum transType;

    /**
     * 
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 
     */
    @TableField("REG_DATE")
    LocalDateTime regDate;

    /**
     * 
     */
    @TableField("NOON_CODE")
    String noonCode;

    /**
     * 
     */
    @TableField("NAME")
    String name;

    /**
     * 
     */
    @TableField("IDENNO")
    String idenNo;

    /**
     * 
     */
    @TableField("SEX_CODE")
    SexEnum sex;

    /**
     * 
     */
    @TableField("BIRTHDAY")
    LocalDate birthday;

    /**
     * 
     */
    @TableField("RELA_PHONE")
    String relaPhone;

    /**
     * 
     */
    @TableField("ADDRESS")
    String address;

    /**
     * 
     */
    @TableField("CARD_TYPE")
    String cardType;

    /**
     * 结算类别号
     */
    @TableField("PAYKIND_CODE")
    String payKindCode;

    /**
     * 
     */
    @TableField("PAYKIND_NAME")
    String payKindName;

    /**
     * 
     */
    @TableField("PACT_CODE")
    String pactCode;

    /**
     * 
     */
    @TableField("PACT_NAME")
    String pactName;

    /**
     * 
     */
    @TableField("MCARD_NO")
    String mcardNo;

    /**
     * 
     */
    @TableField("REGLEVL_CODE")
    String regLevelCode;

    /**
     * 
     */
    @TableField("REGLEVL_NAME")
    String regLevelName;

    /**
     * 
     */
    @TableField("DEPT_CODE")
    String deptCode;

    /**
     * 
     */
    @TableField("DEPT_NAME")
    String deptName;

    /**
     * 
     */
    @TableField("SCHEMA_NO")
    String schemaNo;

    /**
     * 
     */
    @TableField("ORDER_NO")
    Integer orderNo;

    /**
     * 
     */
    @TableField("SEENO")
    Integer seeNo;

    /**
     * 
     */
    @TableField("BEGIN_TIME")
    LocalDateTime beginTime;

    /**
     * 
     */
    @TableField("END_TIME")
    LocalDateTime endTime;

    /**
     * 
     */
    @TableField("DOCT_CODE")
    String doctCode;

    /**
     * 
     */
    @TableField("DOCT_NAME")
    String doctName;

    /**
     * 收费标识
     */
    @TableField("YNREGCHRG")
    Boolean ynRegChrg;

    /**
     * 
     */
    @TableField("INVOICE_NO")
    String invoiceNo;

    /**
     * 
     */
    @TableField("RECIPE_NO")
    String recipeNo;

    /**
     * 
     */
    @TableField("YNBOOK")
    String ynBook;

    /**
     * 
     */
    @TableField("YNFR")
    String ynFr;

    /**
     * 加号标识
     */
    @TableField("APPEND_FLAG")
    Boolean appendFlag;

    /**
     * 
     */
    @TableField("REG_FEE")
    Double regFee;

    /**
     * 
     */
    @TableField("CHCK_FEE")
    Double checkFee;

    /**
     * 
     */
    @TableField("DIAG_FEE")
    Double diagFee;

    /**
     * 
     */
    @TableField("OTH_FEE")
    Double othFee;

    /**
     * 
     */
    @TableField("OWN_COST")
    Double ownCost;

    /**
     * 
     */
    @TableField("PUB_COST")
    Double pubCost;

    /**
     * 
     */
    @TableField("PAY_COST")
    Double payCost;

    /**
     * 
     */
    @TableField("VALID_FLAG")
    ValidEnum validFlag;

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
    @TableField("CANCEL_OPCD")
    String cancelOperCode;

    /**
     * 
     */
    @TableField("CANCEL_DATE")
    LocalDateTime cancelDate;

    /**
     * 
     */
    @TableField("MEDICAL_TYPE")
    String medicalType;

    /**
     * 疾病代码
     */
    @TableField("ICD_CODE")
    String icdCode;

    /**
     * 审批人
     */
    @TableField("EXAM_CODE")
    String examCode;

    /**
     * 
     */
    @TableField("EXAM_DATE")
    LocalDateTime examDate;

    /**
     * 核查标志
     */
    @TableField("CHECK_FLAG")
    Boolean checkFlag;

    /**
     * 核查人
     */
    @TableField("CHECK_OPCD")
    String checkOperCode;

    /**
     * 核查时间
     */
    @TableField("CHECK_DATE")
    LocalDateTime checkDate;

    /**
     * 
     */
    @TableField("BALANCE_FLAG")
    Boolean balanceFlag;

    /**
     * 
     */
    @TableField("BALANCE_NO")
    String balanceNo;

    /**
     * 
     */
    @TableField("BALANCE_OPCD")
    String balanceOperCode;

    /**
     * 
     */
    @TableField("BALANCE_DATE")
    LocalDateTime balanceDate;

    /**
     * 看诊标识
     */
    @TableField("YNSEE")
    Boolean ynSee;

    /**
     * 
     */
    @TableField("SEE_DATE")
    LocalDateTime seeDate;

    /**
     * 分诊标志
     */
    @TableField("TRIAGE_FLAG")
    Boolean triageFlag;

    /**
     * 分诊员
     */
    @TableField("TRIAGE_OPCD")
    String triageOperCode;

    /**
     * 
     */
    @TableField("TRIAGE_DATE")
    LocalDateTime triageDate;

    /**
     * 发票打印数量
     */
    @TableField("PRINT_INVOICECNT")
    Integer printInvoiceCnt;

    /**
     * 
     */
    @TableField("SEE_DPCD")
    String seeDeptCode;

    /**
     * 
     */
    @TableField("SEE_DOCD")
    String seeDoctCode;

    /**
     * 来源
     */
    @TableField("IN_SOURCE")
    String inSource;

    /**
     * 需提取病案
     */
    @TableField("IS_SENDINHOSCASE")
    Boolean isSendInhoscase;

    /**
     * 是否加密姓名
     */
    @TableField("IS_ENCRYPTNAME")
    Boolean isEncryptName;

    /**
     * 密文
     */
    @TableField("NORMALNAME")
    String normalName;

    /**
     * 留观开始时间
     */
    @TableField("IN_DATE")
    LocalDateTime inDate;

    /**
     * 留观结束时间
     */
    @TableField("OUT_DATE")
    LocalDateTime outDate;

    /**
     * 转归代号
     */
    @TableField("ZG")
    String zg;

    /**
     * 
     */
    @TableField("IN_STATE")
    InstateEnum inState;

    /**
     * 优惠金额
     */
    @TableField("ECO_COST")
    Double ecoCost;

    /**
     * 账户流程标识
     */
    @TableField("IS_ACCOUNT")
    Boolean isAccount;

    /**
     * 是否急诊号
     */
    @TableField("IS_EMERGENCY")
    Boolean isEmergency;

    /**
     * 扩展字段1（hangwq：襄阳用于系统自动挂号标志）
     */
    @TableField("MARK1")
    String mark1;

    /**
     * 接诊医生
     */
    @TableField("RECEIPT_DOCT_CODE")
    String receiptDoctCode;

    /**
     * 
     */
    @TableField("RECEIPT_DOCT_DATE")
    String receiptDoctName;

    /**
     * 
     */
    @TableField("RECEIPT_FLAG")
    String receiptFlag;

    /**
     * 门诊一卡通账号
     */
    @TableField("ACCOUNT_NO")
    String accountNo;

    /**
     * EMR挂号流水号 SEQ_FIN_REGID
     */
    @TableField("EMR_REGID")
    String emrRegId;

    /**
     * 健康之路订单号
     */
    @TableField("NET_CARDNO")
    String netCardNo;

    /**
     * 
     */
    @TableField("DEPTOWN")
    DeptOwnEnum deptOwn;

    /**
     * 
     */
    @TableField("EMPLOYEEOWN")
    DeptOwnEnum employeeOwn;

    /**
     * 号源顺序，分时段挂号
     */
    @TableField("SCHEMAQUEUE")
    Integer schemaQueue;

    /**
     * 
     */
    @TableField("PAYMODE")
    PayModeEnum payModel;

    /**
     * fin_opr_preregister表主键(预约ID)
     */
    @TableField("REC_ID")
    String recId;

    /**
     * 支付成功订单号
     */
    @TableField("PAYORDERNO")
    String payOrderNo;

    /**
     * 客户端订单号
     */
    @TableField("CLIENTORDERID")
    String clientOrderId;

    /**
     * 打印标识
     */
    @TableField("ISMODFIYPRINT")
    Boolean isModifyPrint;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinOprRegister) {
            var that = (FinOprRegister) arg0;
            return StringUtils.equals(this.clinicCode, that.clinicCode)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(clinicCode, transType);
    }

    @Getter
    @AllArgsConstructor
    public enum InstateEnum implements Enum {
        正常挂号("N", "正常挂号"),
        留观登记("R", "留观登记"),
        正在留观("I", "正在留观"),
        出观登记("P", "出观登记"),
        留观出院完成("B", "留观出院完成"),
        留观转住院登记("E", "留观转住院登记"),
        留观转住院完成("C", "留观转住院完成");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum PayModeEnum implements Enum {
        正账户余额("1", "账户余额"),
        工行银行卡("2", "工行银行卡"),
        支付宝("3", "支付宝"),
        微信("4", "微信"),
        现金("5", "现金"),
        老医保("6", "老医保"),
        新医保("7", "新医保"),
        诊间银行卡("8", "诊间银行卡"),
        建行银行卡("10", "建行银行卡"),
        扫码挂号("11", "扫码挂号");

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
