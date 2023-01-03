package com.kaos.walnut.api.logic.service.tool;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kaos.walnut.api.data.his.entity.kaos.RaffleFeaturePool;
import com.kaos.walnut.api.data.his.mapper.kaos.RaffleFeaturePoolMapper;
import com.kaos.walnut.api.data.his.mapper.kaos.RaffleLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RaffleService {
    /**
     * 奖池接口
     */
    @Autowired
    RaffleFeaturePoolMapper raffleFeaturePoolMapper;

    /**
     * 抽奖日志接口
     */
    @Autowired
    RaffleLogMapper raffleLogMapper;

    /**
     * 临时添加奖品
     * 
     * @param feature
     * @param count
     */
    @Transactional
    public List<RaffleFeaturePool> addFeature(String feature, Integer count) {
        // 查询当前奖池中是否含有该奖品
        var poolItem = raffleFeaturePoolMapper.selectById(feature);
        if (poolItem != null) {
            // 记录存在，更新数量
            var wrapper = new UpdateWrapper<RaffleFeaturePool>().lambda();
            wrapper.eq(RaffleFeaturePool::getFeature, feature);
            wrapper.set(RaffleFeaturePool::getCount, poolItem.getCount() + count);
            raffleFeaturePoolMapper.update(null, wrapper);
        } else {
            // 添加新纪录
            var builder = RaffleFeaturePool.builder();
            builder.feature(feature);
            builder.count(count);
            raffleFeaturePoolMapper.insert(builder.build());
        }

        // 重新查询记录
        var queryWrapper = new QueryWrapper<RaffleFeaturePool>().lambda();
        queryWrapper.orderByAsc(RaffleFeaturePool::getFeature);
        raffleFeaturePoolMapper.selectList(queryWrapper);
        return raffleFeaturePoolMapper.selectList(queryWrapper);
    }
}
