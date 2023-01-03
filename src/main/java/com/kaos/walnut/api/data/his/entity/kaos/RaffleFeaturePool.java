package com.kaos.walnut.api.data.his.entity.kaos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("KAOS.RAFFLE_FEATURE_POOL")
public class RaffleFeaturePool {
    /**
     * 奖品名称
     */
    @TableId("FEATURE")
    @TableField("FEATURE")
    String feature;

    /**
     * 奖品数量
     */
    @TableField("COUNT")
    Integer count;
}
