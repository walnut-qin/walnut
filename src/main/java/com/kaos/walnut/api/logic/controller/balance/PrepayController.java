package com.kaos.walnut.api.logic.controller.balance;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.entity.FinIpbInPrepay;
import com.kaos.walnut.api.data.mapper.FinIpbInPrepayMapper;
import com.kaos.walnut.api.logic.service.balance.PrepayService;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/balance/prepay")
public class PrepayController {
    /**
     * 预交金接口
     */
    @Autowired
    FinIpbInPrepayMapper finIpbInPrepayMapper;

    /**
     * 业务层
     */
    @Autowired
    PrepayService prepayService;

    /**
     * 读取预交金记录
     * 
     * @param patientNo
     * @return
     */
    @ApiName("获取预交金记录")
    @RequestMapping(value = "get", method = RequestMethod.GET, produces = MediaType.JSON)
    List<Map<String, Object>> get(@RequestParam @NotBlank(message = "住院号不能为空") String patientNo) {
        // 读取预交金表
        var wrapper = new QueryWrapper<FinIpbInPrepay>().lambda();
        wrapper.eq(FinIpbInPrepay::getInpatientNo, "ZY01" + patientNo);
        var prepays = this.finIpbInPrepayMapper.selectList(wrapper);

        // 对象映射
        return prepays.stream().map(x -> {
            Map<String, Object> result = Maps.newHashMap();
            result.put("happenNo", x.getHappenNo());
            result.put("prepayCost", x.getPrepayCost());
            result.put("payWay", x.getPayWay());
            result.put("balanceState", x.getBalanceState());
            return result;
        }).toList();
    }

    /**
     * 隔日召回修改预交金
     * 
     * @param reqBody
     * @return
     */
    @ApiName("隔日召回修改预交金")
    @RequestMapping(value = "fix", method = RequestMethod.POST, produces = MediaType.JSON)
    Object fix(@RequestBody @Valid Fix.ReqBody reqBody) {
        // 调用业务
        this.prepayService.fixPrepay(reqBody.patientNo);

        return ObjectUtils.EMPTY;
    }

    static class Fix {
        static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "住院号不能为空")
            String patientNo;
        }
    }
}
