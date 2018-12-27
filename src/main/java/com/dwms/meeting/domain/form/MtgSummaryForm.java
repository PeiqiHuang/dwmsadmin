package com.dwms.meeting.domain.form;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import com.dwms.meeting.domain.MtgSummary;

@Data
public class MtgSummaryForm extends MtgSummary {

	private String[] users;
	
	private MultipartFile[] files;
	
	private String[] delFileNames;
}
