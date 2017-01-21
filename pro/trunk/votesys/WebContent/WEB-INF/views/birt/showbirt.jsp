<%@ taglib uri="/birt.tld" prefix="birt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>birt</title>
</head>
<body>
	<birt:viewer id="birtViewer1"
		reportDesign='<%=request.getAttribute("birtFileName").toString()%>'
		baseURL="<%=request.getContextPath()%>" pattern="frameset" title=""
		showTitle="false" height="600" width="1500" format="html"
		frameborder="false" isHostPage="false" isReportlet="true"
		showParameterPage="false">
	</birt:viewer>
</body>
</html>