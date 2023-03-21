package com.kaos.walnut.api.logic.service.balance;

import java.time.LocalDateTime;
import java.util.Deque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Queues;
import com.google.gson.reflect.TypeToken;
import com.kaos.walnut.api.data.entity.FinIpbInPrepay;
import com.kaos.walnut.api.data.entity.FinOprPayModel;
import com.kaos.walnut.api.data.mapper.FinIpbInPrepayMapper;
import com.kaos.walnut.api.data.mapper.FinOprPayModelMapper;
import com.kaos.walnut.core.util.DoubleUtils;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import lombok.AllArgsConstructor;

/**
 * 预交金业务
 */
@Service
public class PrepayService {
    /**
     * 预交金接口
     */
    @Autowired
    FinIpbInPrepayMapper finIpbInPrepayMapper;

    /**
     * 线上实付接口
     */
    @Autowired
    FinOprPayModelMapper finOprPayModelMapper;

    /**
     * 修复预交金
     * 
     * @param patientNo
     */
    @Transactional
    public void fixPrepay(String patientNo) {
        // 检索未结算的预交金
        var wrapper = new QueryWrapper<FinIpbInPrepay>().lambda();
        wrapper.eq(FinIpbInPrepay::getInpatientNo, "ZY01" + patientNo);
        wrapper.eq(FinIpbInPrepay::getBalanceState, false);
        var prepays = this.finIpbInPrepayMapper.selectList(wrapper);

        // 依次处理预交金
        for (var prepay : prepays) {
            // 若关联号为空，则说明非线上支付
            if (StringUtils.isBlank(prepay.getReferNum())) {
                continue;
            }

            // 检索线上实付记录
            var subWrapper = new QueryWrapper<FinOprPayModel>().lambda();
            subWrapper.eq(FinOprPayModel::getCardNo, patientNo);
            subWrapper.eq(FinOprPayModel::getTradeCode, FinOprPayModel.TradeCodeEnum.退费);
            subWrapper.eq(FinOprPayModel::getReferNum, prepay.getReferNum());
            var models = this.finOprPayModelMapper.selectList(subWrapper);

            // 计算新值
            var offset = models.stream().mapToDouble(x -> {
                return x.getAmount();
            }).sum();
            var newCost = prepay.getPrepayCost() + offset;
            newCost = Math.round(newCost * 100) / 100.0;

            // 若相等, 则不需要修改
            if (DoubleUtils.equals(prepay.getPrepayCost(), newCost)) {
                continue;
            }

            // 记录修改日志
            History history = new History(prepay.getHistory());
            history.push(prepay.getPrepayCost(), newCost);

            // 修改数据库
            var updateWrapper = new UpdateWrapper<FinIpbInPrepay>().lambda();
            updateWrapper.eq(FinIpbInPrepay::getInpatientNo, prepay.getInpatientNo());
            updateWrapper.eq(FinIpbInPrepay::getHappenNo, prepay.getHappenNo());
            updateWrapper.set(FinIpbInPrepay::getPrepayCost, newCost);
            updateWrapper.set(FinIpbInPrepay::getHistory, history.dump());
            this.finIpbInPrepayMapper.update(null, updateWrapper);
        }
    }

    /**
     * 结构化历史
     */
    static class History {
        /**
         * 节点列表
         */
        Deque<Node> nodes = null;

        /**
         * 节点结构
         */
        @AllArgsConstructor
        static class Node {
            /**
             * 操作日期
             */
            LocalDateTime operDate;

            /**
             * 旧值
             */
            Double oldValue;

            /**
             * 新值
             */
            Double newValue;
        }

        /**
         * 从原始字符串构造
         * 
         * @param jsonString
         */
        public History(String jsonString) {
            if (StringUtils.isBlank(jsonString)) {
                this.nodes = Queues.newArrayDeque();
            } else {
                this.nodes = ObjectUtils.deserialize(jsonString, new TypeToken<Deque<Node>>() {
                });
            }
        }

        /**
         * 添加新节点
         * 
         * @param oldValue
         * @param newValue
         */
        public void push(Double oldValue, Double newValue) {
            this.nodes.push(new Node(LocalDateTime.now(), oldValue, newValue));
        }

        /**
         * 转为日志
         * 
         * @return
         */
        public String dump() {
            return ObjectUtils.serialize(this.nodes);
        }
    }
}
