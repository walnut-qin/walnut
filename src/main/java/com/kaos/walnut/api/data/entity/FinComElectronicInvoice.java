package com.kaos.walnut.api.data.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.api.data.enums.TransTypeEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("XYHIS.FIN_COM_ELECTRONICINVOICE")
public class FinComElectronicInvoice {
    /**
     * HIS发票号
     */
    @TableId("INVOICE_NO")
    String invoiceNo;

    /**
     * 交易类型
     */
    @TableId("TRANTYPE")
    TransTypeEnum transType;

    /**
     * 批次号
     */
    @TableField("BILLBATCHCODE")
    String billBatchCode;

    /**
     * 票据号
     */
    @TableField("BILLNO")
    String billNo;

    /**
     * 随机码
     */
    @TableField("RANDOM")
    String random;

    /**
     * 创建时间
     */
    @TableField("CREATETIME")
    String createTime;

    /**
     * 二维码信息
     */
    @TableField("BILLQRCODE")
    String billQrCode;

    /**
     * 票据链接
     */
    @TableField("PICTUREURL")
    String pictureUrl;

    /**
     * 票据链接
     */
    @TableField("OPERDATE")
    LocalDateTime operDate;

    /**
     * 票据状态
     */
    @TableField("STATE")
    StateEnum state;

    /**
     * 操作是否成功
     */
    @TableField("SUCCESSSTATE")
    SuccessStateEnum successState;

    /**
     * 业务类别
     */
    @TableField("BUSINESSTYPE")
    BusinessTypeEnum businessType;

    /**
     * 请求源头
     */
    @TableField("SOURCE")
    SourceTypeEnum sourceType;

    /**
     * 旧发票号
     */
    @TableField("OLD_INVOICE_NO")
    String oldInvoiceNo;

    /**
     * 就诊卡号
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 更新日期
     */
    @TableField("UPDATEDATE")
    LocalDateTime updateDate;

    /**
     * 操作员
     */
    @TableField("OPERID")
    String operId;

    /**
     * 流水号-票据平台唯一主键
     */
    @TableField("BUS_NO")
    String busNo;

    /**
     * 冲红原因
     */
    @TableField("WRITEOFFREASON")
    String writeOffReason;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    LocalDateTime createDate;

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
    public enum StateEnum implements Enum {
        有效("1", "1"),
        冲红("2", "2");

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
    public enum SuccessStateEnum implements Enum {
        失败("0", "0"),
        成功("1", "1"),
        补开成功("2", "2"),
        补冲红成功("3", "3");

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
    public enum BusinessTypeEnum implements Enum {
        住院("1", "01"),
        门诊("02", "02"),
        急诊("03", "03"),
        门特("04", "04"),
        体检中心("05", "05"),
        挂号("06", "06"),
        住院预交金("7", "07"),
        体检预交金("08", "08"),
        往来票("09", "09"),
        捐赠票("10", "10"),
        非税通用票("11", "11"),
        门诊预交金("12", "12");

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