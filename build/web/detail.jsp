<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sản phẩm</title>
        </head>
    <body>

        <div class="container mt-4">
            <jsp:include page="header.jsp"/>
        </div>
        <div class="container">

            <h1>Chi tiết sản phẩm</h1>

            <c:if test="${not empty detail}">
                <div>
                    <div>
                        <c:choose>
                            <c:when test="${not empty detail.image}">
                                <img src="data:image/jpeg;base64,${detail.image}" alt="${detail.productName}" style="width: 300px;">
                            </c:when>
                            <c:otherwise>
                                <img src="https://via.placeholder.com/300x300?text=No+Image" alt="No image">
                            </c:otherwise>
                        </c:choose>

                        <div>
                            <c:forEach var="imgBase64" items="${detail.galleryImages}">
                                <img src="data:image/jpeg;base64,${imgBase64}" alt="Ảnh phụ" style="width:80px; height:80px; object-fit: cover; margin: 5px; border: 1px solid #ddd;">
                            </c:forEach>
                        </div>
                    </div>

                    <div>
                        <p><strong>ID:</strong> ${detail.productId}</p>
                        <p><strong>Tên sản phẩm:</strong> ${detail.productName}</p>
                        <p><strong>Giá:</strong> 
                            <fmt:formatNumber value="${detail.price}" type="number" groupingUsed="true"/> VND
                        </p>
                        <p><strong>Kích cỡ:</strong> ${detail.size}</p>
                        <p><strong>Màu sắc:</strong> ${detail.color}</p>
                        <p><strong>Tồn kho:</strong> ${detail.quantity}</p>
                        <p><strong>Danh mục:</strong> ${detail.categoryId}</p>
                        <p><strong>Mô tả:</strong> ${detail.description}</p>
                    </div>
                </div>
            </c:if> 
            
            <c:if test="${empty detail}">
                <p>Không tìm thấy sản phẩm.</p>
            </c:if> 
            
            <a href="homeController">← Quay lại trang chủ</a>
        </div>

        <div class="container mt-4">
            <jsp:include page="footer.jsp"/>
        </div>

    </body>
</html>