<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="java.text.NumberFormat"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách sản phẩm</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">

        <h1 class="mb-4 text-primary text-center">Danh sách sản phẩm</h1>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <form action="productController" method="GET" class="row g-2">

                <input type="hidden" name="action" value="search"/>

                <div class="col-auto">
                    <input type="text" class="form-control" name="txtSearch" 
                           placeholder="Nhập tên sản phẩm..." value="${requestScope.searchKey}"/>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </div>
            </form>
            <a href="productController?action=insert" class="btn btn-success">
                Thêm sản phẩm mới
            </a>
        </div>


        <c:choose>
            <c:when test="${not empty listP}">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                        <thead class="table-dark text-center">
                            <tr>
                                <th>ID</th>
                                <th>Ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th>Giá (VND)</th>
                                <th>Màu</th>
                                <th>Size</th>
                                <th>Tồn kho</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${listP}">
                                <tr>
                                    <td class="text-center">${p.productId}</td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${not empty p.image}">
                                                <img src="data:image/jpeg;base64,${p.image}" alt="${p.productName}" style="width:80px; height:80px; object-fit:cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://via.placeholder.com/80?text=No+Image" alt="No image" style="width:80px; height:80px;">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${p.productName}</td>
                                    <td class="text-end">
                                        <%= NumberFormat.getInstance().format(((DTO.productDTO) pageContext.getAttribute("p")).getPrice())%>
                                    </td>
                                    <td class="text-center">${p.color}</td>
                                    <td class="text-center">${p.size}</td>
                                    <td class="text-center">${p.quantity}</td>

                                    <td class="text-center">
                                        <a href="productController?action=edit&id=${p.productId}"
                                           class="btn btn-warning btn-sm">
                                            Sửa
                                        </a>
                                        <a href="productController?action=delete&id=${p.productId}"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
                                            Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <h5 class="text-center mt-4">
                    Tổng cộng: 
                    <strong>
                        <%-- Lấy listP từ request và gọi hàm size() của Java --%>
                        <%= ((java.util.List<DTO.productDTO>) request.getAttribute("listP")).size()%>
                    </strong> 
                    sản phẩm
                </h5>
            </c:when>

            <c:otherwise>
                <div class="text-center text-secondary py-5">
                    <h4>Không có sản phẩm nào để hiển thị.</h4>
                </div>
            </c:otherwise>
        </c:choose>
        <a href="adminDashboard.jsp">Quay lại Dashboard</a>
        <a href="mainController" class="btn btn-primary mt-3">🏠 Quay lại Trang chủ</a>

    </body>
</html>