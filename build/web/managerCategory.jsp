<%-- 
    Document   : managerCategory
    Created on : Nov 6, 2025, 4:08:18 AM
    Author     : btkha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý danh mục</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">

        <h1>Quản lý danh mục</h1>
        <hr>

        <div class="card p-3 mb-4">
            <h3>Thêm danh mục mới</h3>

            <h5 style="color: red;">${requestScope.errorMessage}</h5>
            <h5 style="color: green;">${requestScope.message}</h5>

            <form action="managerCategoryController" method="POST">
                <input type="hidden" name="action" value="insert" />

                <div class="row g-2">
                    <div class="col-md-4">
                        <input type="text" name="categoryId" class="form-control" placeholder="Category ID (ví dụ: C04)" required>
                    </div>
                    <div class="col-md-6">
                        <input type="text" name="categoryName" class="form-control" placeholder="Tên danh mục (ví dụ: Giày dép)" required>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">Thêm</button>
                    </div>
                </div>
            </form>
        </div>

        <h3>Danh sách hiện có</h3>

        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>Category ID</th>
                    <th>Tên danh mục</th>
                    <th>Hành động</th> </tr>
            </thead>
            <tbody>
                <c:forEach var="cat" items="${requestScope.categoryList}">
                    <tr>
                        <td>${cat.categoryId}</td>
                        <td>${cat.categoryName}</td>

                        <td>
                            <a href="managerCategoryController?action=delete&id=${cat.categoryId}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">
                                Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h3>Danh sách hiện có</h3>

        <a href="managerCategoryController?action=trash" class="btn btn-secondary mb-2">
            Xem thùng rác
        </a>

        <table class="table table-bordered table-striped">
        </table>

        <a href="adminDashboard.jsp">Quay lại Dashboard</a>

    </body>
</html>