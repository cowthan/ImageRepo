# ImageRepo
图片浏览器，存一些小图


freemaker文档：
http://freemarker.org/docs/ref_directive_alphaidx.html



* 使用：
	* 直接访问：http://cowthan.github.com/ImageRepo  ，  这个是gh-pages分支下的项目主页，对应代码里的debug=false，会生成index.html，访问master下repo目录的图片
	* 下载到本地，访问start.sh或者start.bat，会运行run-me.jar包，对应代码里debug=true，会生成index.html，访问本地repo目录的图片
	* 只要repo目录有任何改动，必须运行一次IconRepo.java，并将debug设为false，以刷新index.html，并提交到gh-pages
	* git add ./
	* git commit -m "add picture and refresh index.html"
	* git push origin gh-pages
	* git checkout master
	* git merge gh-pages
	* get push origin master