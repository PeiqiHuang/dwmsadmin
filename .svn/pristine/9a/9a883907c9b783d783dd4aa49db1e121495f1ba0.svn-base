package com.dwms.examine.domain.vo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ExamQuWithData extends ExamQuWithQu {

    private List<Option> answersList;
    
    public void addAnswer(String answer, int selectNum, float rate) {
        if(CollectionUtils.isEmpty(answersList)) {
            answersList = Lists.newArrayList();
        }
        answersList.add(new Option(answer, selectNum, rate));
    }
    
    @AllArgsConstructor
    @Data
    public class Option {
        
        private String answer;
        
        private int selectNum;//选择该选项的人数
        
        private float rate;//选择该选项的百分比(%)
    }
	
}
