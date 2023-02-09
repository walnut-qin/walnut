package com.kaos.walnut.api.data.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_IPB_BALANCEHEAD")
public class FinIpbBalanceHead {
    /**
     * 发票号
     */
    @TableId("INVOICE_NO")
    @TableField("INVOICE_NO")
    String invoiceNo;

    /**
     * 交易类型
     */
    @TableId("TRANS_TYPE")
    @TableField("TRANS_TYPE")
    TransTypeEnum transType;

    /**
     * 住院流水号
     */
    @TableField("INPATIENT_NO")
    String inpatientNo;

    /**
     * 结算序号
     */
    @TableField("BALANCE_NO")
    Integer balanceNo;

    /**
     * 结算类别
     */
    @TableField("PAYKIND_CODE")
    PayKindCodeEnum payKindCode;

    /**
     * 合同代码
     */
    @TableField("PACT_CODE")
    String pactCode;

    /**
     * 预交金额
     */
    @TableField("PREPAY_COST")
    Double prepayCost;

    /**
     * 转入预交金额
     */
    @TableField("CHANGE_PREPAYCOST")
    Double changePrepayCost;

    /**
     * 费用金额
     */
    @TableField("TOT_COST")
    Double totCost;

    /**
     * 自费金额
     */
    @TableField("OWN_COST")
    Double ownCost;

    /**
     * 自付金额
     */
    @TableField("PAY_COST")
    Double payCost;

    /**
     * 公费金额
     */
    @TableField("PUB_COST")
    Double pubCost;

    /**
     * 优惠金额
     */
    @TableField("ECO_COST")
    Double ecoCost;

    /**
     * 减免金额
     */
    @TableField("DER_COST")
    Double derCost;

    /**
     * 转入费用金额
     */
    @TableField("CHANGE_TOTCOST")
    Double changeTotCost;

    /**
     * 补收金额
     */
    @TableField("SUPPLY_COST")
    Double supplyCost;

    /**
     * 返还金额
     */
    @TableField("RETURN_COST")
    Double returnCost;

    /**
     * 转押金
     */
    @TableField("FOREGIFT_COST")
    Double foregiftCost;

    /**
     * 起始日期
     */
    @TableField("BEGIN_DATE")
    LocalDateTime beginDate;

    /**
     * 终止日期
     */
    @TableField("END_DATE")
    LocalDateTime endDate;

    /**
     * 结算类型
     */
    @TableField("BALANCE_TYPE")
    BalanceTypeEnum balanceType;

    /**
     * 结算人代码
     */
    @TableField("BALANCE_OPERCODE")
    String balanceOperCode;

    /**
     * 结算时间
     */
    @TableField("BALANCE_DATE")
    LocalDateTime balanceDate;

    /**
     * 本次账户支付
     */
    @TableField("ACCOUNT_PAY")
    Double accountPay;

    /**
     * 公务员补助
     */
    @TableField("OFFICE_PAY")
    Double officePay;

    /**
     * 大额补助
     */
    @TableField("LARGE_PAY")
    Double largePay;

    /**
     * 老红军
     */
    @TableField("MILTARY_PAY")
    Double militaryPay;

    /**
     * 本次现金支付
     */
    @TableField("CASH_PAY")
    Double cashPay;

    /**
     * 财务组代码
     */
    @TableField("FINGRP_CODE")
    String finGrpCode;

    /**
     * 打印次数
     */
    @TableField("PRINT_TIMES")
    Integer printTimes;

    /**
     * 审核序号
     */
    @TableField("CHECK_NO")
    String checkNo;

    /**
     * 主发票标志
     */
    @TableField("MAININVOICE_FLAG")
    Boolean mainInvoiceFlag;

    /**
     * 扩展标志
     */
    @TableField("WASTE_FLAG")
    Boolean wasteFlag;

    /**
     * 扩展标志1 线上结算发票打印状态标志 : 0未打印 1已打印
     */
    @TableField("EXT1_FLAG")
    Boolean ext1Flag;

    /**
     * 扩展代码
     */
    @TableField("EXT_CODE")
    String extFlag;

    /**
     * 生育保险最后一次结算标志
     */
    @TableField("LAST_FLAG")
    Boolean lastFlag;

    /**
     * 姓名
     */
    @TableField("NAME")
    String name;

    /**
     * 结算员科室
     */
    @TableField("BALANCEOPER_DEPTCODE")
    String balanceOperDeptCode;

    /**
     * 本次结算调整公费日限额超标金额
     */
    @TableField("BURSARY_ADJUSTOVERTOP")
    Double bursaryAdjustOvertop;

    /**
     * 作废操作员
     */
    @TableField("WASTE_OPERCODE")
    String wasteOperCode;

    /**
     * 作废时间
     */
    @TableField("WASTE_DATE")
    LocalDateTime wasteDate;

    /**
     * 0未核查/1已核查
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
     * 0未日结/1已日结
     */
    @TableField("DAYBALANCE_FLAG")
    Boolean daybalanceFlag;

    /**
     * 日结标识号
     */
    @TableField("DAYBALANCE_NO")
    String daybalanceNo;

    /**
     * 日结人
     */
    @TableField("DAYBALANCE_OPCD")
    String daybalanceOperCode;

    /**
     * 日结时间
     */
    @TableField("DAYBALANCE_DATE")
    LocalDateTime daybalanceDate;

    /**
     * 0没有处理欠费或者补收 1 处理完毕
     */
    @TableField("BALANCE_SAVE_TYPE")
    String balanceSaveType;

    /**
     * 处理欠费或者补收人员
     */
    @TableField("BALANCE_SAVE_OPER")
    String balanceSaveOperCode;

    /**
     * 处理欠费或者补收时间
     */
    @TableField("BALANCE_SAVE_DATE")
    LocalDateTime balanceSaveDate;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    @TableField("DEPTOWN")
    DeptOwnEnum deptOwn;

    /**
     * 职员院区标识 0 全院 1 南区, 2北区 3东区
     */
    @TableField("EMPLOYEEOWN")
    DeptOwnEnum employeeOwn;

    /**
     * 残联金额
     */
    @TableField("CL_COST")
    Double clCost;

    /**
     * 精准扶贫
     */
    @TableField("JZFPBCYZF_COST")
    Double jzfpCost;

    /**
     * 公益宝金额，湖北慈善总会
     */
    @TableField("GYBAPI_COST")
    Double gybCost;

    /**
     * 医院承担
     */
    @TableField("HOS_COST")
    Double hosCost;

    /**
     * 线上结算发票是否线上标志 : 0线下 1线上
     */
    @TableField("ONLINE_FLAG")
    Boolean onlineFlag;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbBalanceHead) {
            var that = (FinIpbBalanceHead) arg0;
            return StringUtils.equals(this.invoiceNo, that.invoiceNo)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(invoiceNo, transType);
    }

    @Getter
    @AllArgsConstructor
    public enum PayKindCodeEnum implements Enum {
        自费("01", "自费"),
        保险("02", "保险"),
        公费在职("03", "公费在职"),
        公费退休("04", "公费退休"),
        公费高干("05", "公费高干");

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
    public enum BalanceTypeEnum implements Enum {
        在院结算("I", "在院结算"),
        出院结算("O", "出院结算"),
        直接结算("D", "直接结算"),
        重结算("4", "重结算"),
        结转("5", "结转"),
        欠费结算("Q", "欠费结算");

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
