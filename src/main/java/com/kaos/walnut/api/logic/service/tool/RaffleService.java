package com.kaos.walnut.api.logic.service.tool;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kaos.walnut.api.data.his.entity.kaos.RaffleFeaturePool;
import com.kaos.walnut.api.data.his.entity.kaos.RaffleLog;
import com.kaos.walnut.api.data.his.mapper.SequenceMapper;
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
     * 序列接口
     */
    @Autowired
    SequenceMapper sequenceMapper;

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

    /**
     * 抽奖一次
     * 
     * @param name
     * @return
     */
    private void raffle(String name) {
        // 获取当前奖池
        var queryWrapper = new QueryWrapper<RaffleFeaturePool>().lambda();
        queryWrapper.orderByAsc(RaffleFeaturePool::getFeature);
        var items = raffleFeaturePoolMapper.selectList(queryWrapper);

        // 产生随机数
        var idx = (int) Math.floor(Math.random() * items.size());

        // 锁定奖池
        var item = items.get(idx);

        // 若此为最后一个奖项，则删除记录，否则数量减一
        if (item.getCount() == 1) {
            raffleFeaturePoolMapper.deleteById(item.getFeature());
        } else {
            var wrapper = new UpdateWrapper<RaffleFeaturePool>().lambda();
            wrapper.eq(RaffleFeaturePool::getFeature, item.getFeature());
            wrapper.set(RaffleFeaturePool::getCount, item.getCount() - 1);
            raffleFeaturePoolMapper.update(null, wrapper);
        }

        // 插入抽奖记录
        var builder = RaffleLog.builder();
        builder.key(Integer.parseInt(sequenceMapper.query("KAOS.SEQ_RAFFLE_LOG_KEY")));
        builder.name(name);
        builder.feature(item.getFeature());
        raffleLogMapper.insert(builder.build());
    }

    /**
     * 抽奖一次
     * 
     * @param name
     * @return
     */
    @Transactional
    public void raffleOnce(String name) {
        // 查询奖池
        var queryWrapper = new QueryWrapper<RaffleFeaturePool>().lambda();
        queryWrapper.orderByAsc(RaffleFeaturePool::getFeature);
        var poolItems = raffleFeaturePoolMapper.selectList(queryWrapper);
        if (poolItems.isEmpty()) {
            throw new RuntimeException("奖池已空！");
        }

        // 可以抽奖
        this.raffle(name);
    }

    /**
     * 列表抽奖
     * 
     * @param names
     */
    public void raffleList(List<String> names) {
        // 查询奖池
        var queryWrapper = new QueryWrapper<RaffleFeaturePool>().lambda();
        queryWrapper.orderByAsc(RaffleFeaturePool::getFeature);
        var poolItems = raffleFeaturePoolMapper.selectList(queryWrapper);
        if (poolItems.size() < names.size()) {
            throw new RuntimeException("奖池数量不支持一轮抽奖！");
        }

        // 可以抽奖
        for (String name : names) {
            this.raffle(name);
        }
    }
}
