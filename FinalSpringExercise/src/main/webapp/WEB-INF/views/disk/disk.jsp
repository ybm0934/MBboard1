<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>웹 폴더</title>
    <link rel="stylesheet" href="${path }/css/disk/disk.css">
</head>
<body>
<div id="wrap">
    <div id="titleDiv">
        <h1>웹 폴더</h1>
    </div>
    <div id="menuDiv">
        <input type="button" class="btn" value="폴더 생성">
    </div>
    <div id="pathDiv">
        <a href="#">Home</a>
    </div>
    <div id="contentDiv">
    <c:if test="${empty list }">
    	<div class="noDiv">
    		<span class="noSpn">파일이 없습니다.</span>
    	</div>
    </c:if>
    <c:if test="${!empty list }">
    <c:forEach var="vo" items="${list }">
        <div class="content">
            <div class="fileImg">
            <img src="${path }/img/${vo.type }.png">
            </div>
            <div class="fileName">
                <span class="nameSpn">${vo.name }</span>
            </div>
            <div class="fileSize">
                <span class="fileSizeSpn">
                <c:if test="${vo.type == 'file' }">
                	${vo.size }MB
                </c:if>
                </span>
            </div>
        </div>
    </c:forEach>
    </c:if>
    </div>
    <div id="pagingDiv">
        
    </div>
</div>
</body>
</html>