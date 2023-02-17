package com.kaos.walnut.api.logic.controller.surgery;

import com.kaos.walnut.api.logic.service.surgery.DictionaryService;
import com.kaos.walnut.core.tool.lock.Lock;
import com.kaos.walnut.core.tool.lock.LockExecutor;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

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
    DictionaryService dictionaryService;

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
            return this.dictionaryService.importSurgery(workbook);
        });
    }
}
