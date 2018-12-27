package com.dwms.birthday.domain.form;

import lombok.Data;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import com.dwms.birthday.domain.Wish;

@Data
@ToString
public class WishForm extends Wish {

    private Integer partyId;
    
    // 用于搜索条件中的时间字段
    private String timeField;
    private String beginField;
    private String endField;
    
    public String getBeginField() {
        if (StringUtils.isNotBlank(timeField)) {
            String[] timeArr = timeField.split("~");
            return timeArr[0];
        }
        return beginField;
    }

    public String getEndField() {
        if (StringUtils.isNotBlank(timeField)) {
            String[] timeArr = timeField.split("~");
            return timeArr[1];
        }
        return endField;
    }
}
