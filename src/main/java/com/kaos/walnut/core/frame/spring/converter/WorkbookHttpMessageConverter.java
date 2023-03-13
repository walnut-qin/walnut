/*********************************************************
 * File: WorkbookHttpMessageConverter.java
 * Created Date: 2022-07-25
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  实现 WorkBook 类的消息转换，处理 Excel 导出
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.core.frame.spring.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

class WorkbookHttpMessageConverter extends AbstractHttpMessageConverter<Workbook> {
    /**
     * 构造函数
     */
    public WorkbookHttpMessageConverter() {
        super(new MediaType("application", "vnd.ms-excel", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Workbook.class.isAssignableFrom(clazz);
    }

    @Override
    protected Workbook readInternal(Class<? extends Workbook> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        // 仅支持xlsx
        return new XSSFWorkbook(inputMessage.getBody());
    }

    @Override
    protected void writeInternal(Workbook t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        // 设置header
        var header = outputMessage.getHeaders();
        if (t instanceof HSSFWorkbook) {
            header.set("Content-Disposition", "attachment;filename=data.xls");
        } else {
            header.set("Content-Disposition", "attachment;filename=data.xlsx");
        }
        header.set("Pragma", "no-cache");
        header.set("Cache-Control", "no-cache");
        header.setExpires(0);

        // 设置body
        var body = outputMessage.getBody();
        t.write(body);
    }
}
