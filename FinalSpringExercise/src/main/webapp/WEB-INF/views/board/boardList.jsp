<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>게시판</title>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/board/boardList.css">
	<script>
		function firstPage() {
			document.boardList.currentPage.value = 1;
			document.boardList.submit();
		}
		
		function lastPage() {
			document.boardList.currentPage.value = ${pagingInfo.totalPage};
			document.boardList.submit();
		}
		
		function prevPage() {
			document.boardList.currentPage.value = ${pagingInfo.firstPage - 1};
			document.boardList.submit();
		}
		
		function nextPage() {
			document.boardList.currentPage.value = ${pagingInfo.lastPage + 1};
			document.boardList.submit();
		}
		
		function goPage(i) {
			document.boardList.currentPage.value = i;
			document.boardList.submit();
		}
	</script>
</head>
<body>
<div id="wrap">
	<form name="boardList" method="post" action="boardList.do">
	    <input type="hidden" name="currentPage" value="${pagingInfo.currentPage }">
	    <input type="hidden" name="category" value="${searchVo.category }">
	    <input type="hidden" name="keyword" value="${searchVo.keyword }">
	</form>
    <div id="titleDiv">
        <h1>게시판</h1>
    </div>
    <table id="listTable">
        <colgroup>
            <col width="10%;">
            <col width="*;">
            <col width="10%;">
            <col width="10%;">
            <col width="15%;">
        </colgroup>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>첨부파일</th>
            <th>작성자</th>
            <th>등록일</th>
        </tr>
        <c:if test="${empty list }">
        <tr>
        	<td colspan="5" class="noRecord">등록 된 게시글이 없습니다.</td>
        </tr>
        </c:if>
        <c:if test="${!empty list }">
        <c:forEach var="vo" items="${list }">
        <tr>
            <td>${vo.no }</td>
            <td>${vo.title }</td>
            <td>${vo.files }</td>
            <td>${vo.name }</td>
            <td><fmt:formatDate value="${vo.regdate }" pattern="yyyy-MM-dd hh:mm" /></td>
        </tr>
        </c:forEach>
        </c:if>
    </table>
    <div id="pagingDiv">
        <c:if test="${pagingInfo.firstPage > 1 }">
        	<a href="#" onclick="firstPage();">&lt;&lt;</a>
        	<a href="#" onclick="prevPage();">PREV</a>
        </c:if>
        <c:forEach var="i" begin="${pagingInfo.firstPage }" end="${pagingInfo.lastPage }">
        <c:if test="${i != pagingInfo.currentPage }">
        	<a href="#" onclick="goPage('${i}');">${i }</a>
        </c:if>
        <c:if test="${i == pagingInfo.currentPage }">
        	<span>${i }</span>
        </c:if>
        </c:forEach>
        <c:if test="${pagingInfo.lastPage < pagingInfo.totalPage }">
        	<a href="#" onclick="nextPage();">Next</a>
        	<a href="#" onclick="lastPage();">&gt;&gt;</a>
        </c:if>
    </div>
    <div id="searchDiv">
        <form name="search" method="post" action="boardList.do">
            <select name="category">
                <option value="title" 
                <c:if test="${searchVo.category == 'title' }">selected</c:if>>제목</option>
                <option value="content"
                <c:if test="${searchVo.category == 'content' }">selected</c:if>>내용</option>
                <option value="name"
                <c:if test="${searchVo.category == 'name' }">selected</c:if>>작성자</option>
            </select>
            <input type="text" name="keyword" class="box" value="${searchVo.keyword }">
            <input type="submit" class="btn" value="검색">
        </form>
    </div>
</div>
</body>
</html>