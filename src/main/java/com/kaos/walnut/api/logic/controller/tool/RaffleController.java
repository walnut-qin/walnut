package com.kaos.walnut.api.logic.controller.tool;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.walnut.api.data.his.entity.kaos.RaffleFeaturePool;
import com.kaos.walnut.api.data.his.entity.kaos.RaffleLog;
import com.kaos.walnut.api.data.his.mapper.kaos.RaffleFeaturePoolMapper;
import com.kaos.walnut.api.data.his.mapper.kaos.RaffleLogMapper;
import com.kaos.walnut.api.logic.service.tool.RaffleService;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/tool/raffle")
class RaffleController {
    /**
     * 抽奖业务
     */
    @Autowired
    RaffleService raffleService;

    /**
     * 抽奖记录接口
     */
    @Autowired
    RaffleLogMapper raffleLogMapper;

    /**
     * 奖池接口
     */
    @Autowired
    RaffleFeaturePoolMapper raffleFeaturePoolMapper;

    @ResponseBody
    @ApiName("获取抽奖日志")
    @RequestMapping(value = "queryRaffleLogs", method = RequestMethod.GET)
    List<RaffleLog> queryRaffleLogs() throws Exception {
        // 查询抽奖记录日志
        var logWrapper = new QueryWrapper<RaffleLog>().lambda();
        logWrapper.orderByAsc(RaffleLog::getKey);

        // 构造响应
        return raffleLogMapper.selectList(logWrapper);
    }

    @ResponseBody
    @ApiName("获取奖池数据")
    @RequestMapping(value = "queryFeaturePool", method = RequestMethod.GET)
    List<RaffleFeaturePool> queryFeaturePool() throws Exception {
        // 查询奖池数据
        var poolWrapper = new QueryWrapper<RaffleFeaturePool>().lambda();
        poolWrapper.orderByAsc(RaffleFeaturePool::getFeature);

        // 构造响应
        return raffleFeaturePoolMapper.selectList(poolWrapper);
    }

    @ResponseBody
    @ApiName("退回所有奖品")
    @RequestMapping(value = "cancelAll", method = RequestMethod.POST, produces = MediaType.JSON)
    Object cancelAll() throws Exception {
        // 调用业务
        raffleService.cancelAll();

        return ObjectUtils.EMPTY;
    }

    @ResponseBody
    @ApiName("添加奖品")
    @RequestMapping(value = "addFeature", method = RequestMethod.POST, produces = MediaType.JSON)
    Object addFeature(@RequestBody @Valid AddFeature.ReqBody reqBody) throws Exception {
        // 调用业务
        raffleService.addFeature(reqBody.feature, reqBody.count);

        return ObjectUtils.EMPTY;
    }

    static class AddFeature {
        static class ReqBody {
            /**
             * 奖品名称
             */
            @NotBlank(message = "奖品名称不能为空")
            String feature;

            /**
             * 数量
             */
            @NotNull(message = "奖品数量不能为空")
            @Min(value = 1, message = "奖品数量最小为1")
            Integer count;
        }
    }

    @ResponseBody
    @ApiName("单次抽奖")
    @RequestMapping(value = "raffleOnce", method = RequestMethod.POST)
    Object raffleOnce(@RequestBody @Valid RaffleOnce.ReqBody reqBody) throws Exception {
        // 执行业务
        this.raffleService.raffleOnce(reqBody.name);

        return ObjectUtils.EMPTY;
    }

    static class RaffleOnce {
        static class ReqBody {
            /**
             * 抽奖人姓名
             */
            @NotBlank(message = "抽奖人不能为空")
            String name;
        }
    }

    @ResponseBody
    @ApiName("列表抽奖")
    @RequestMapping(value = "raffleList", method = RequestMethod.POST)
    Object raffleList(@RequestBody @Valid RaffleList.ReqBody reqBody) throws Exception {
        // 执行业务
        this.raffleService.raffleList(reqBody.names);

        return ObjectUtils.EMPTY;
    }

    static class RaffleList {
        static class ReqBody {
            /**
             * 抽奖人姓名
             */
            @NotEmpty(message = "姓名列表不能为空")
            List<String> names;
        }
    }
}
