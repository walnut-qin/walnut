package com.kaos.walnut.api.data.his.entity.kaos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("KAOS.RAFFLE_LOG")
public class RaffleLog {
    /**
     * 索引号
     */
    @TableId("IDX")
    @TableField("IDX")
    Integer idx;

    /**
     * 姓名
     */
    @TableField("NAME")
    String name;

    /**
     * 奖品
     */
    @TableField("FEATURE")
    String feature;
}
