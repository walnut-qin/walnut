package com.kaos.walnut.api.logic.controller.tool;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kaos.walnut.api.data.his.entity.kaos.RaffleFeaturePool;
import com.kaos.walnut.api.logic.service.tool.RaffleService;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
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

    @ResponseBody
    @ApiName("添加奖品")
    @RequestMapping(value = "addFeature", method = RequestMethod.POST, produces = MediaType.JSON)
    List<RaffleFeaturePool> addFeature(@RequestBody @Valid AddFeature.ReqBody reqBody) throws Exception {
        // 记录日志
        log.info(String.format("添加奖品：%d 个 %s", reqBody.feature, reqBody.count));

        return raffleService.addFeature(reqBody.feature, reqBody.count);
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
            @Size(min = 1, message = "奖品数量最小为1")
            Integer count;
        }
    }
}
