package com.kaos.skynet.core.frame.module.spring.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
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
        return null;
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
