package com.kaos.walnut.api.logic.controller.surgery;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kaos.walnut.api.logic.service.surgery.PrivilegeService;
import com.kaos.walnut.core.tool.lock.Lock;
import com.kaos.walnut.core.tool.lock.LockExecutor;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/surgery/dictionary")
public class DictionaryController {
    /**
     * 字典维护锁，批量操作，为保证安全性，锁数量设为1
     */
    static Lock lock = new Lock("手术字典维护锁", 1);

    /**
     * 字典操作业务
     */
    @Autowired
    PrivilegeService privilegeService;

    /**
     * 批量导入权限
     * src\main\java\com\kaos\doc\手术授权表-模板.xlsx
     * 
     * @param workbook
     * @return
     */
    @ApiName("导入手术授权数据")
    @RequestMapping(value = "importSurgery", method = RequestMethod.POST, produces = MediaType.JSON)
    Integer importSurgery(@RequestBody Workbook workbook) throws Exception {
        // 敏感操作，带锁执行
        LockExecutor.clear();
        LockExecutor.link(lock, 0);
        return LockExecutor.execute(() -> {
            return this.privilegeService.importDocPriv(workbook);
        });
    }

    @ApiName("清空权限")
    @RequestMapping(value = "clearPrivilege", method = RequestMethod.POST, produces = MediaType.JSON)
    Object clearPrivilege(@RequestBody @Valid ClearPrivilege.ReqBody reqBody) {
        if (reqBody.emplCode != null) {
            this.privilegeService.clearDoctPrivilege(reqBody.emplCode);
        }

        if (reqBody.deptCode != null) {
            this.privilegeService.clearDeptPrivilege(reqBody.deptCode);
        }
        return ObjectUtils.EMPTY;
    }

    public class ClearPrivilege {
        /**
         * 请求body
         */
        class ReqBody {
            /**
             * 医师编码
             */
            String emplCode;

            /**
             * 科室编码
             */
            String deptCode;
        }
    }

    @ApiName("添加某个科室的权限")
    @RequestMapping(value = "addDeptPrivilege", method = RequestMethod.POST, produces = MediaType.JSON)
    Object addDeptPrivilege(@RequestBody @Valid AddDeptPrivilege.ReqBody reqBody) {
        this.privilegeService.addDeptPrivilege(reqBody.deptCode, reqBody.icdCodes);
        return ObjectUtils.EMPTY;
    }

    public class AddDeptPrivilege {
        /**
         * 请求body
         */
        class ReqBody {
            /**
             * 科室编码
             */
            @NotBlank(message = "科室不能为空")
            String deptCode;

            /**
             * 手术项目
             */
            @NotNull(message = "手术不能为空")
            List<String> icdCodes;
        }
    }

    @ApiName("添加某个医师的权限")
    @RequestMapping(value = "addDoctPrivilege", method = RequestMethod.POST, produces = MediaType.JSON)
    Object addDoctPrivilege(@RequestBody @Valid AddDoctPrivilege.ReqBody reqBody) {
        if (reqBody.icdCodes != null) {
            this.privilegeService.addDoctPrivilege(reqBody.emplCode, reqBody.icdCodes);
        }

        if (reqBody.deptCode != null && reqBody.level != null) {
            this.privilegeService.addDoctPrivilege(reqBody.emplCode, reqBody.deptCode, reqBody.level);
        }

        return ObjectUtils.EMPTY;
    }

    public class AddDoctPrivilege {
        /**
         * 请求body
         */
        class ReqBody {
            /**
             * 科室编码
             */
            @NotBlank(message = "医师不能为空")
            String emplCode;

            /**
             * 手术项目
             */
            List<String> icdCodes;

            /**
             * 科室编码
             */
            String deptCode;

            /**
             * 科室编码
             */
            Integer level;
        }
    }
}
