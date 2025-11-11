<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 20px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .product-detail {
            display: flex;
            flex-wrap: wrap;
            gap: 40px;
        }
        .product-images {
            flex: 1;
            min-width: 300px;
        }
        .product-images img.main-img {
            width: 100%;
            border-radius: 10px;
            margin-bottom: 15px;
        }
        .gallery-images {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        .gallery-images img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border: 2px solid #ddd;
            border-radius: 6px;
            cursor: pointer;
            transition: 0.2s;
        }
        .gallery-images img:hover {
            border-color: #007bff;
        }
        .product-info {
            flex: 1;
            min-width: 300px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .product-info p {
            font-size: 16px;
            margin: 8px 0;
        }
        .product-info strong {
            color: #555;
        }
        .price {
            color: #e63946;
            font-size: 22px;
            font-weight: bold;
            margin: 15px 0;
        }
        .add-cart-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 30px;
            background-color: #28a745;
            color: #fff;
            text-decoration: none;
            border-radius: 8px;
            font-size: 16px;
            border: none;
            cursor: pointer;
            transition: 0.2s;
        }
        .add-cart-btn:hover {
            background-color: #218838;
        }
        a.back-link {
            display: inline-block;
            margin-top: 30px;
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
        a.back-link:hover {
            text-decoration: underline;
        }
        @media(max-width: 768px){
            .product-detail {
                flex-direction: column;
                gap: 20px;
            }
            .gallery-images {
                justify-content: start;
            }
        }
    </style>
    <script>
        // Thay đổi ảnh chính khi click gallery
        function changeMainImage(src) {
            document.getElementById("mainProductImg").src = src;
        }
    </script>
</head>
<body>

<div class="container">
    <jsp:include page="header.jsp"/>
</div>

<div class="container">
    <h1>Chi tiết sản phẩm</h1>

    <c:if test="${not empty detail}">
        <div class="product-detail">
            <!-- Ảnh sản phẩm chính và gallery -->
            <div class="product-images">
                <c:choose>
                    <c:when test="${not empty detail.image}">
                        <img id="mainProductImg" class="main-img" src="data:image/jpeg;base64,${detail.image}" alt="${detail.productName}">
                    </c:when>
                    <c:otherwise>
                        <img id="mainProductImg" class="main-img" src="https://via.placeholder.com/300x300?text=No+Image" alt="No image">
                    </c:otherwise>
                </c:choose>

                <div class="gallery-images">
                    <c:forEach var="imgBase64" items="${detail.galleryImages}">
                        <img src="data:image/jpeg;base64,${imgBase64}" alt="Ảnh phụ" onclick="changeMainImage(this.src)">
                    </c:forEach>
                </div>
            </div>

            <!-- Thông tin sản phẩm -->
            <div class="product-info">
                <div>
                    <p><strong>ID:</strong> ${detail.productId}</p>
                    <p><strong>Tên sản phẩm:</strong> ${detail.productName}</p>
                    <p class="price"><strong>Giá:</strong> 
                        <fmt:formatNumber value="${detail.price}" type="number" groupingUsed="true"/> VND
                    </p>
                    <p><strong>Kích cỡ:</strong> ${detail.size}</p>
                    <p><strong>Màu sắc:</strong> ${detail.color}</p>
                    <p><strong>Tồn kho:</strong> ${detail.quantity}</p>
                    <p><strong>Danh mục:</strong> ${detail.categoryId}</p>
                    <p><strong>Mô tả:</strong> ${detail.description}</p>
                </div>

                <!-- Nút thêm vào giỏ hàng -->
                <form action="addToCartController" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productId" value="${detail.productId}">
                    <input type="number" name="quantity" value="1" min="1" max="${detail.quantity}" style="width:60px; margin-right:10px;">
                    <button type="submit" class="add-cart-btn">🛒 Thêm vào giỏ hàng</button>
                </form>
            </div>
        </div>
    </c:if> 

    <c:if test="${not empty msg}">
        <p style="color:red; text-align:center;">${msg}</p>
    </c:if>

    <c:if test="${empty detail}">
        <p>Không tìm thấy sản phẩm.</p>
    </c:if> 

    <a href="homeController" class="back-link">← Quay lại trang chủ</a>
</div>

<div class="container mt-4">
    <jsp:include page="footer.jsp"/>
</div>

</body>
</html>
