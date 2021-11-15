<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function(){
		$('#show').click(function(){
			$.ajax({
				url : 'boardList2.do',
				type : 'POST',
				datatype : 'JSON',
				success : function(data){
					var obj = JSON.stringify(data);
					var pobj = JSON.parse(obj);
					
					console.log(obj);
					
					var currentPage = pobj.pagingInfo.currentPage;
					var pageSize = pobj.pagingInfo.pageSize;
					
					alert(currentPage);
					alert(pageSize);
				},
				error : function(){
					alert('통신 실패');
				}
			});
		});
	});
</script>
</head>
<body>
<input type="button" id="show" value="목록">
</body>
</html>