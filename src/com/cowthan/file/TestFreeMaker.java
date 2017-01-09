package com.cowthan.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;  

public class TestFreeMaker {
	
	public static void main(String[] args) {
		
		String dir = "test";
		
		Configuration cfg = new Configuration();  
		  
        try {  
            // 从哪里加载模板文件  
            cfg.setDirectoryForTemplateLoading(new File(dir));  
              
            // 定义模版的位置，从类路径中，相对于FreemarkerManager所在的路径加载模版  
            // cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerManager.class, "templates"))  
  
            // 设置对象包装器  
            cfg.setObjectWrapper(new DefaultObjectWrapper());  
  
            // 设置异常处理器  
            cfg .setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);  
  
            // 定义数据模型  
            Map root = new HashMap();  
            root.put("abc", "世界，你好");  
            root.put("x", 3);  
            
            List<Animal> animals = new ArrayList<>();
            animals.add(new Animal());
            animals.add(new Animal());
            animals.add(new Animal());
            animals.add(new Animal());
            animals.add(new Animal());
            root.put("animals", animals);  
  
            // 通过freemarker解释模板，首先需要获得Template对象  
            Template template = cfg.getTemplate("test.ftl");  
  
            // 定义模板解释完成之后的输出  
            PrintWriter out = new PrintWriter(new BufferedWriter(  
                    new FileWriter(dir+"/out.txt")));  
  
              
            try {  
                // 解释模板  
                template.process(root, out);  
            } catch (TemplateException e) {  
                e.printStackTrace();  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public static class Animal{
		public String name = "猫狗树懒";
		public double price = 100.78;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		
	}
}
