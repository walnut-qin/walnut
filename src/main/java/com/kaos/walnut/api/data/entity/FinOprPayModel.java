package com.kaos.walnut.api.data.entity;

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
     * 就诊卡号 - 住院患者存住院号
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 就诊卡号 - 住院患者存住院号
     */
    @TableField("TRADECODE")
    TradeCodeEnum tradeCode;

    /**
     * 金额
     */
    @TableField("AMOUNT")
    Double amount;

    /**
     * 关联号
     */
    @TableField("REFERNUM")
    String referNum;

    /**
     * 关联号
     */
    @TableField("PINGTAIJSBH")
    String pingTaiJSBH;

    /**
     * 在院状态
     */
    @Getter
    @AllArgsConstructor
    public enum TradeCodeEnum implements Enum {
        收费("S1", "收费"),
        退费("S2", "退费");

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
