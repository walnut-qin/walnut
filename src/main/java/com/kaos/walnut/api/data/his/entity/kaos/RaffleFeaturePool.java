package com.kaos.walnut.api.data.his.entity.kaos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("KAOS.RAFFLE_FEATURE_POOL")
public class RaffleFeaturePool {
    @TableId("FEATURE")
    @TableField("FEATURE")
    String feature;

    @TableField("COUNT")
    Integer count;
}
