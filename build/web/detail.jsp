<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sản phẩm</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f8fa;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 80%;
                max-width: 900px;
                margin: 40px auto;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                padding: 30px;
            }

            h1 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 30px;
            }

            .product-detail {
                display: flex;
                flex-wrap: wrap;
                gap: 30px;
                align-items: flex-start;
                justify-content: center;
            }

            .product-detail img {
                width: 300px;
                height: 300px;
                object-fit: cover;
                border-radius: 8px;
                border: 1px solid #ddd;
            }

            .info {
                flex: 1;
                min-width: 250px;
            }

            .info p {
                font-size: 16px;
                margin: 10px 0;
                color: #333;
            }

            .info strong {
                color: #007bff;
            }

            .back-link {
                display: block;
                text-align: center;
                margin-top: 30px;
                text-decoration: none;
                color: #007bff;
                font-weight: bold;
            }

            .back-link:hover {
                color: #0056b3;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h1>Chi tiết sản phẩm</h1>

            <c:if test="${not empty detail}">
                <div class="product-detail">
                    <div class="image">
                        <c:choose>
                            <c:when test="${not empty detail.image}">
                                <img src="data:image/jpeg;base64,${detail.image}" alt="${detail.productName}">
                            </c:when>
                            <c:otherwise>
                                <img src="https://via.placeholder.com/300x300?text=No+Image" alt="No image">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="info">
                        <p><strong>ID:</strong> ${detail.productId}</p>
                        <p><strong>Tên sản phẩm:</strong> ${detail.productName}</p>
                        <p><strong>Giá:</strong> ${detail.price} VND</p>
                        <p><strong>Kích cỡ:</strong> ${detail.size}</p>
                        <p><strong>Màu sắc:</strong> ${detail.color}</p>
                        <p><strong>Tồn kho:</strong> ${detail.quantity}</p>
                        <p><strong>Danh mục:</strong> ${detail.categoryId}</p>
                        <p><strong>Mô tả:</strong> ${detail.description}</p>
                    </div>
                </div>
            </c:if>

            <c:if test="${empty detail}">
                <p style="text-align:center; color:#888;">Không tìm thấy sản phẩm.</p>
            </c:if>

            <a href="homeController" class="back-link">← Quay lại trang chủ</a>
        </div>
        <footer>
            <jsp:include page="/footer.jsp" />

        </footer>
    </body>
</html>
