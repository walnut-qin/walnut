package com.kaos.walnut.api.data.his.entity.kaos;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.Data;

@Data
@TableName("KAOS.ESCORT_ANNEX_INFO")
public class EscortAnnexInfo {
    /**
     * 附件编号
     */
    @TableId("ANNEX_NO")
    String annexNo;

    /**
     * 就诊卡号
     */
    @TableField("CARD_NO")
    String cardNo;

    /**
     * 附件链接
     */
    @TableField("ANNEX_URL")
    String annexUrl;

    /**
     * 上传时间
     */
    @TableField("OPER_DATE")
    LocalDateTime operDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof EscortAnnexInfo) {
            var that = (EscortAnnexInfo) arg0;
            return StringUtils.equals(this.annexNo, that.annexNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(annexNo);
    }
}
