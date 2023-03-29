package com.kaos.walnut.api.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * 住院流水号
     */
    @TableId("INPATIENT_NO")
    String inpatientNo;

    /**
     * 陪护证序号
     */
    @TableId("HAPPEN_NO")
    Integer happenNo;

    /**
     * 金额
     */
    @TableField("PREPAY_COST")
    Double prepayCost;

    /**
     * 支付方式
     */
    @TableField("PAY_WAY")
    PayWayEnum payWay;

    /**
     * 结算状态
     */
    @TableField("BALANCE_STATE")
    Boolean balanceState;

    /**
     * 记录关联号 - link to 线上缴费记录
     */
    @TableField("REFERNUM")
    String referNum;

    /**
     * 历史信息(修改预交金后会在此记录)
     */
    @TableField("HISTORY")
    String history;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinIpbInPrepay) {
            var that = (FinIpbInPrepay) arg0;
            return StringUtils.equals(this.inpatientNo, that.inpatientNo)
                    && IntegerUtils.equals(this.happenNo, that.happenNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(inpatientNo, happenNo);
    }

    /**
     * 在院状态
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
        保险账户("PS", "保险账户"),
        院内账户("YS", "院内账户"),
        东风卡("DFK", "东风卡"),
        日间手术账户("RJSH", "日间手术账户"),
        微信("WX", "微信"),
        支付宝("ZFB", "支付宝"),
        东津聚合("DJJH", "东津聚合");

        /**
         * 数据库值
         */
        private String value;

        /**
         * 序列化值
         */
        private String description;
    }
}
