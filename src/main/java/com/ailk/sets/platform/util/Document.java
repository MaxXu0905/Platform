package com.ailk.sets.platform.util;

import java.util.Map;

public interface Document {  
  
    /** 
     * 获取文档词频,辅助Constracter工作
     * @param content 
     * @return {@link Map} 
     */  
    public Map<String, Integer> segment();//文档向量
    public Map<String, Integer> documentFreq();//文档成词
} 