package com.dwms.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;


public class ChapterManager {
    
    private String CHAPTER_BEGIN = "<c>";
    private String CHAPTER_END = "</c>";
    private String SECTION_BEGIN = "<s>";
    private String SECTION_END = "</s>";
    private String enter = "\n";
    
    //private File file;
    private BufferedReader reader;
    private String lastLine;
    
    private boolean nextSection;
    private boolean next;
    
    private int chapterNo;
    private int sectionNo;
    @Getter
    private StringBuilder title;
    @Getter
    private StringBuilder content;
    @Getter
    private boolean section;
    

    public ChapterManager(File file) throws IOException {
        reader = new BufferedReader(new FileReader(file));
        init();
    }

    public ChapterManager(InputStream inputStream) throws IOException {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        init();
    }
    
    private void init() throws IOException {
        lastLine = "";
        section = false;
        nextSection = false;
        next = readLine();
        chapterNo = 0;
        sectionNo = 0;
    }

    public boolean hasNext()  {
        return next;
    }
    
    public void next() throws IOException {
        title = new StringBuilder();
        content = new StringBuilder();
        section = nextSection;
        if (section) {
            ++sectionNo;
        } else {
            ++chapterNo;
            sectionNo = 0;
        }
        next = process();
    }
    
    private boolean process() throws IOException {
        if (!findBegin()) {
            return false;
        }
        boolean noEOF = true;
        //title
        clearBeginTag();
        while(noEOF && !findEnd()) {
            title.append(enter).append(lastLine);
            noEOF = readLine();
        }
        if (!noEOF) return false;
        clearEndTag();
        title.append(lastLine);
        
        //content
        String nextTag = null;
        noEOF = readLine();
        while(noEOF && StringUtils.isBlank(nextTag = findAnyBegin())) {
            content.append(enter).append(lastLine);
            noEOF = readLine();
        }
        if (!noEOF) return false;
        
        nextSection = StringUtils.equals(nextTag, SECTION_BEGIN); 
        
        return true;
    }
    
    private String findAnyBegin() {
        if (StringUtils.contains(lastLine, SECTION_BEGIN)) 
            return SECTION_BEGIN;
        if (StringUtils.contains(lastLine, CHAPTER_BEGIN))
            return CHAPTER_BEGIN;
        return null;
    }

    private boolean findBegin() {
        return StringUtils.contains(lastLine, section ? SECTION_BEGIN : CHAPTER_BEGIN);
    }
    
    private boolean findEnd() {
        return StringUtils.contains(lastLine, section ? SECTION_END : CHAPTER_END);
    }

    private void clearBeginTag() {
        lastLine = lastLine.replace(section ? SECTION_BEGIN : CHAPTER_BEGIN, "");
    }
    
    private void clearEndTag() {
        lastLine = lastLine.replace(section ? SECTION_END : CHAPTER_END, "");
    }
    
    private boolean readLine() throws IOException {
        lastLine = reader.readLine();
        return null != lastLine;
    }
    
    public int getChapterNo() {
        return section ? sectionNo : chapterNo;
    }
    
    public static void main(String[] args) throws IOException {
//        ChapterManager cm = new ChapterManager(new File("d:/党务秘书/course_little.txt"));
        ChapterManager cm = new ChapterManager(new File("d:/党务秘书/course.txt"));
        while(cm.hasNext()) {
            cm.next();
            System.out.println("类型= " + (cm.isSection() ? "节" : "章"));
            System.out.println("title= " + cm.getTitle());
            System.out.println("chapterNo= " + cm.getChapterNo());
            //System.out.println("content= " + cm.getContent());
        }
    }

}
