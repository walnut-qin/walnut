package com.kaos.skynet.api.logic.controller.outpatient.register;

import javax.validation.Valid;

import com.kaos.skynet.api.logic.service.outpatient.register.RegisterService;
import com.kaos.skynet.core.tool.lock.Lock;
import com.kaos.skynet.core.tool.lock.LockExecutor;
import com.kaos.skynet.core.type.MediaType;
import com.kaos.skynet.core.type.annotations.ApiName;
import com.kaos.skynet.core.util.ObjectUtils;

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
@RequestMapping("/api/outpatient/register")
public class RegisterController {
    /**
     * 挂号锁
     */
    Lock clinicCodeLock = new Lock("挂号锁", 20);

    /**
     * 事务业务
     */
    @Autowired
    RegisterService registerService;

    /**
     * 退号
     * 
     * @param reqBody
     * @return
     */
    @ResponseBody
    @ApiName("退号")
    @RequestMapping(value = "cancel", method = RequestMethod.POST, produces = MediaType.JSON)
    Object cancel(@RequestBody @Valid Cancel.ReqBody reqBody) throws Exception {
        LockExecutor.link(clinicCodeLock, reqBody.clinicCode);
        LockExecutor.execute(() -> {
            registerService.cancel(reqBody.clinicCode);
        });
        return ObjectUtils.EMPTY;
    }

    static class Cancel {
        static class ReqBody {
            /**
             * 门诊号
             */
            String clinicCode;
        }
    }
}
