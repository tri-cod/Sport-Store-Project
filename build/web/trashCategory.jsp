<%-- 
    Document   : trashCategory
    Created on : Nov 6, 2025, 5:25:29 AM
    Author     : btkha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thùng rác - Danh mục</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-4">

    <h1>Thùng rác (Danh mục đã xóa)</h1>
    <hr>
    
    <c:if test="${param.restoreSuccess == 'true'}">
        <h5 style="color: green;">Khôi phục thành công!</h5>
    </c:if>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>Category ID</th>
                <th>Tên danh mục</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cat" items="${requestScope.trashList}">
                <tr>
                    <td>${cat.categoryId}</td>
                    <td>${cat.categoryName}</td>
                    <td>
                        <a href="managerCategoryController?action=restore&id=${cat.categoryId}"
                           class="btn btn-success btn-sm">
                           Khôi phục
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <a href="managerCategoryController?action=list">Quay lại danh sách chính</a>

</body>
</html>
