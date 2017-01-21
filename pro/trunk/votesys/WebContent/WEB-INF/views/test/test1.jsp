<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*,java.awt.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function aa(){
		 var s = "";   
	      s += " 网页可见区域宽："+ document.body.clientWidth+"\n";    
	      s += " 网页可见区域高："+ document.body.clientHeight+"\n";    
	      s += " 网页可见区域宽："+ document.body.offsetWidth + " (包括边线和滚动条的宽)"+"\n";    
	      s += " 网页可见区域高："+ document.body.offsetHeight + " (包括边线的宽)"+"\n";    
	      s += " 网页正文全文宽："+ document.body.scrollWidth+"\n";    
	      s += " 网页正文全文高："+ document.body.scrollHeight+"\n";    
	      s += " 网页被卷去的高(ff)："+ document.body.scrollTop+"\n";    
	      s += " 网页被卷去的高(ie)："+ document.documentElement.scrollTop+"\n";    
	      s += " 网页被卷去的左："+ document.body.scrollLeft+"\n";    
	      s += " 网页正文部分上："+ window.screenTop+"\n";    
	      s += " 网页正文部分左："+ window.screenLeft+"\n";    
	      s += " 屏幕分辨率的高："+ window.screen.height+"\n";    
	      s += " 屏幕分辨率的宽："+ window.screen.width+"\n";    
	      s += " 屏幕可用工作区高度："+ window.screen.availHeight+"\n";    
	      s += " 屏幕可用工作区宽度："+ window.screen.availWidth+"\n";    
	      s += " 你的屏幕设置是 "+ window.screen.colorDepth +" 位彩色"+"\n";    
	      s += " 你的屏幕设置 "+ window.screen.deviceXDPI +" 像素/英寸"+"\n";    
	      alert (s);
	}
	window.onload=function(){
		//aa();
	};
	function cc(obj){
		var ch = obj.href.replace("width=800px;height=800px","width="+window.screen.width+"px;height="+window.screen.height+"px");
		obj.href=ch;
	}
</script>
</head>
<body>

  <a href="<%=PageOfficeLink.openWindow(request,"/test/toPdf","width=800px;height=800px;")%>" onclick="cc(this)">在线打开PDF文件</a>
</body>
</html>