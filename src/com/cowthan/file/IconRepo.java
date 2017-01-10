package com.cowthan.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;


/**
 * 遍历一个目录，返回目录结构，返回的格式是json
 *
 */
public class IconRepo {
	
	public static final String github_root = "https://raw.githubusercontent.com/cowthan/ImageRepo/master/";
	public static final String repo_root = "repo";
	
	public static boolean debug = false;
	
	///得传入repo里的目录和文件，不要以/开头，不要带repo
	public static String generateUrlOfGithub(String file){
		return github_root + repo_root + "/" + file;
	}
	
	public static void main(String[] args) {
		
		String path1111 = System.getProperty("user.dir");
		System.out.println(path1111);
		
		String rootDir = path1111 + "\\repo";
		Dir dir = dirToDir(rootDir);
		//String json = JsonUtilsUseFast.toJson(dir); 
		//System.out.println(json);
		
		//String data = "var data={json};".replace("{json}", json);
		//putContent(path1111 + "\\data.js", data);
		
		Map data = new HashMap();
		data.put("dirs", dir);
		
		if(!debug){
			processForRemote(dir);
		}
		
		FreeMaker.template("./", "image-tmpl.html", "./image.html", data);
		
		
		String url = ("file:///" + path1111 + "/index.html").replace("\\", "/");
		System.out.println(url);
		openBrowser(url);
	}
	
	private static void processForRemote(Dir dir) {
		for(Dir d: dir.subDirs){
			for(JustFile f: d.subFiles){
				f.path = generateUrlOfGithub(d.name + "/" + f.name);
			}
		}
	}

	public static Dir dirToDir(String rootDir){
		
		String path1111 = System.getProperty("user.dir");
		System.out.println(path1111);
		
		Dir root = new Dir();
		root.path = rootDir;
		
		///---
		File fr = new File(root.path);
		root.name = fr.getName();
		
		File[] files = fr.listFiles();
		if(files == null || files.length == 0){
			return root;
		}else{
			for(File f: files){
				if(f.isDirectory()){
					root.subDirs.add(dirToDir(f.getAbsolutePath()));
				}else{
					JustFile jf = new JustFile();
					jf.path = f.getAbsolutePath().replace("\\", "/");
					jf.name = f.getName();
					jf.size = getFileSize(jf.path);
					
					jf.path = jf.path.replace(path1111 + "\\", "./");
					
					int[] size = getImageSize(jf.path);
					jf.w = size[0];
					jf.h = size[1];
					
					root.subFiles.add(jf);
				}
			}
		}
		
		///---
		
		return root;
	}
	
	public static class JustFile{
		public String name;
		public String path;
		public String suffix;
		public String size; 
		public int w, h;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		
		
		public String getW() {
			return w + "";
		}
		public void setW(int w) {
			this.w = w;
		}
		public String getH() {
			return h +"";
		}
		public void setH(int h) {
			this.h = h;
		}
		public String getInfo(){
			String s = "";
			
			s += "File Name: " + this.name + "<br/>";
			s += "File Size: " + this.size + "<br/>";
			s += "尺寸: " + w + " * " + h + "<br/>";
			
			return s;
		}
	}
	public static class Dir{
		public String name;
		public String path;
		public List<Dir> subDirs = new ArrayList<>();
		public List<JustFile> subFiles = new ArrayList<>();
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public List<Dir> getSubDirs() {
			return subDirs;
		}
		public void setSubDirs(List<Dir> subDirs) {
			this.subDirs = subDirs;
		}
		public List<JustFile> getSubFiles() {
			return subFiles;
		}
		public void setSubFiles(List<JustFile> subFiles) {
			this.subFiles = subFiles;
		}
		
		
	}
	
	public static void putContent(String absolutePath, String content){
		try {
			File f = new File(absolutePath);
			if(!f.exists()){
				f.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(absolutePath));   
			out.write(content.getBytes()); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}
	
	public static void openBrowser(String url){
		if(url == null){
			System.out.println("url为空");
			return;
		}
		if(java.awt.Desktop.isDesktopSupported()){
            try{
                //创建一个URI实例,注意不是URL
                java.net.URI uri=java.net.URI.create(url);
                //获取当前系统桌面扩展
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    //获取系统默认浏览器打开链接
                    dp.browse(uri);
                }else{
                	System.out.println("不支持打开浏览器功能");
                }
            }catch(java.io.IOException e){
                //此为无法获取系统默认浏览器
            	e.printStackTrace();
            	System.out.println("无法获取系统默认浏览器");
            }
        }
	}
	
	public static String getFileSize(String path){
		 File f= new File(path);  
		    if (f.exists() && f.isFile()){  
		          return getDataSize(f.length());
		    }else{  
		    	return getDataSize(0);
		    }  
	}

	public static String getDataSize(long size){  
        DecimalFormat formater = new DecimalFormat("####.00");  
        if(size<1024){  
            return size+"bytes";  
        }else if(size<1024*1024){  
            float kbsize = size/1024f;    
            return formater.format(kbsize)+"KB";  
        }else if(size<1024*1024*1024){  
            float mbsize = size/1024f/1024f;    
            return formater.format(mbsize)+"MB";  
        }else if(size<1024*1024*1024*1024){  
            float gbsize = size/1024f/1024f/1024f;    
            return formater.format(gbsize)+"GB";  
        }else{  
            return "size: error";  
        }  
	}
	
	public static int[] getImageSize(String path){
		File picture = new File(path);
        BufferedImage sourceImg;
		try {
			sourceImg = ImageIO.read(new FileInputStream(picture));
//			System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
//	        System.out.println(sourceImg.getWidth()); // 源图宽度
//	        System.out.println(sourceImg.getHeight()); // 源图高度
	        return new int[]{sourceImg.getWidth(), sourceImg.getHeight()};
		} catch (FileNotFoundException e) {
			System.out.println("出错：" + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("出错：" + path);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("出错：" + path);
			e.printStackTrace();
		} 
        return new int[]{0, 0};
	}
}
