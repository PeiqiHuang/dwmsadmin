package com.dwms.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.dwms.common.domain.ValidateCodeProperties;
import com.dwms.common.shiro.ShiroProperties;

@Configuration
@ConfigurationProperties(prefix = "dwms")
public class DwmsProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private ValidateCodeProperties validateCode = new ValidateCodeProperties();

    private String timeFormat = "yyyy-MM-dd HH:mm:ss";

    private String dateFormat = "yyyy-MM-dd";

    private boolean openAopLog = true;

    public ShiroProperties getShiro() {
        return shiro;
    }

    public void setShiro(ShiroProperties shiro) {
        this.shiro = shiro;
    }

    public ValidateCodeProperties getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isOpenAopLog() {
        return openAopLog;
    }

    public void setOpenAopLog(boolean openAopLog) {
        this.openAopLog = openAopLog;
    }

}
