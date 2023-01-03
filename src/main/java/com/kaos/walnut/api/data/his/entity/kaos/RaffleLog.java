package com.kaos.walnut.api.data.his.entity.kaos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("KAOS.RAFFLE_LOG")
public class RaffleLog {
    /**
     * 索引号
     */
    @TableId("KEY")
    @TableField("KEY")
    Integer key;

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
