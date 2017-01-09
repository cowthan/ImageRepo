<span style="font-family:Microsoft YaHei;">第一个测试程序：${abc}</span>  

<#-- 注释部分 -->

---循环开始---
<#list animals as being> 
   <li>${being.name} for ${being.price} Euros<br> 
</#list>
---循环结束---

---判断开始---
<#if x == 1>
	x is 1
<#elseif x == 2>
	x is 2
<#else>
	x is ${x}
</#if>
---判断结束---