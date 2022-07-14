package com.kaos.skynet.api.data.his.entity.xyhis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_COM_ELECTRONICINVOICE")
public class FinComElectronicInvoice {
    /**
     * HIS内发票号
     */
    @TableId("INVOICE_NO")
    private String invoiceNo;

    /**
     * 电子票据批次号
     */
    @TableField("BILLBATCHCODE")
    private String billBatchCode;

    /**
     * 电子发票票据号
     */
    @TableField("BILLNO")
    private String billNo;

    /**
     * 电子票据系统随机数
     */
    @TableField("RANDOM")
    private String random;

    /**
     * 电子票据二维码
     */
    @TableField("BILLQRCODE")
    private String billQRCode;

    /**
     * 电子票据外链
     */
    @TableField("PICTUREURL")
    private String picUrl;

    /**
     * 最后操作时间
     */
    @TableField("OPERDATE")
    private LocalDateTime operDate;

    /**
     * 电子发票状态
     */
    @TableField("STATE")
    private StateEnum state;

    /**
     * 电子发票成功与否
     */
    @TableField("SUCCESSSTATE")
    private SuccessStateEnum successState;

    /**
     * 交易类型
     */
    @TableId("TRANTYPE")
    private TransTypeEnum transType;

    /**
     * 业务类型
     */
    @TableField("BUSINESSTYPE")
    private BusinessTypeEnum businessType;

    /**
     * 业务源
     */
    @TableField("SOURCE")
    private SourceTypeEnum sourceType;

    /**
     * 卡号
     */
    @TableField("CARD_NO")
    private String cardNo;

    /**
     * 开票员
     */
    @TableField("OPERID")
    private String operCode;

    /**
     * 业务流水号(电子票系统唯一)
     */
    @TableField("BUS_NO")
    private String busNo;

    /**
     * 冲红原因
     */
    @TableField("WRITEOFFREASON")
    private String writeOffReason;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinComElectronicInvoice) {
            var that = (FinComElectronicInvoice) arg0;
            return StringUtils.equals(this.invoiceNo, that.invoiceNo)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(invoiceNo, transType);
    }

    /**
     * 电子发票业务类型
     */
    @Getter
    @AllArgsConstructor
    public enum BusinessTypeEnum implements Enum {
        住院("01", "住院"),
        住院fix("1", "住院"),
        门诊("02", "门诊"),
        急诊("03", "急诊"),
        门特("04", "门特"),
        体检("05", "体检"),
        挂号("06", "挂号"),
        住院预交金("07", "住院预交金"),
        住院预交金fix("7", "住院预交金"),
        体检预交金("08", "体检预交金"),
        往来票("09", "体检预交金"),
        捐赠票("10", "体检预交金"),
        非税通用票("11", "体检预交金"),
        门诊预交金("12", "体检预交金");

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
     * 电子票据状态专用枚举
     */
    @Getter
    @AllArgsConstructor
    public enum StateEnum implements Enum {
        有效("1", "有效"),
        冲红("2", "冲红");

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
     * 操作状态
     */
    @Getter
    @AllArgsConstructor
    public enum SuccessStateEnum implements Enum {
        失败("0", "失败"),
        成功("1", "成功"),
        补开成功("2", "补开成功"),
        补冲红成功("3", "补冲红成功");

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
    public enum SourceTypeEnum implements Enum {
        窗口("1", "窗口"),
        自助机("2", "自助机"),
        微信("3", "微信"),
        支付宝("4", "支付宝");

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
