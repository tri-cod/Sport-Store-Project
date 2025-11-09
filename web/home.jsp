<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang chủ - Cửa hàng thời trang</title>
        <style>
            body {
                font-family: "Segoe UI", Arial, sans-serif;
                background-color: #f4f6f8;
                margin: 0;
            }


            /* Category bar */
            .category-bar {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 15px;
                background-color: white;
                padding: 15px;
                border-bottom: 1px solid #ddd;
            }

            .category-bar a {
                text-decoration: none;
                color: #333;
                padding: 8px 16px;
                border-radius: 20px;
                background-color: #f1f1f1;
                transition: all 0.2s;
                font-weight: 500;
            }

            .category-bar a:hover,
            .category-bar a.active {
                background-color: #007bff;
                color: white;
            }

            /* Search bar */
            .search-container {
                text-align: center;
                margin: 25px 0;
            }

            .search-container input[type="text"] {
                width: 300px;
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 25px;
                outline: none;
            }

            .search-container button {
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 25px;
                background-color: #007bff;
                color: white;
                cursor: pointer;
                margin-left: 10px;
            }

            .search-container button:hover {
                background-color: #0056b3;
            }

            h1 {
                text-align: center;
                color: #2c3e50;
                margin-top: 10px;
            }

            /* Product grid */
            .product-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 25px;
                padding: 20px;
                width: 90%;
                margin: auto;
            }

            .product {
                background-color: white;
                border-radius: 12px;
                padding: 15px;
                text-align: center;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                transition: transform 0.2s ease, box-shadow 0.2s ease;
            }

            .product:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            }

            .product img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                border-radius: 10px;
                background-color: #f0f0f0;
            }

            .product h4 {
                color: #007bff;
                margin: 10px 0 5px;
            }

            .product-price {
                color: #e74c3c;
                font-weight: bold;
                font-size: 18px;
            }

            .empty {
                text-align: center;
                color: #777;
                margin-top: 50px;
            }

        </style>
    </head>
    <body>

        <!-- Header -->
       <div class="container mt-4">
            <jsp:include page="header.jsp"/>
        </div>

        <!-- Category -->
        <form action="categoryController" method="get">
            <div class="category-bar">
                <a href="categoryController" class="${empty selectedCategory ? 'active' : ''}">Tất cả</a>
                <c:forEach var="c" items="${listC}">
                    <a href="categoryController?cid=${c.categoryId}" 
                       class="${selectedCategory == c.categoryId ? 'active' : ''}">
                        ${c.categoryName}
                    </a>
                </c:forEach>
            </div>
        </form>

        <!-- Search -->
        <div class="search-container">
            <form action="searchController" method="get">
                <input value="${txtS}" type="text" name="txtSearchProduct" placeholder="Nhập tên sản phẩm...">
                <button type="submit">🔍 Tìm kiếm</button>
            </form>
        </div>

        <h1>Danh sách sản phẩm</h1>

        <c:choose>
            <c:when test="${not empty listP}">
                <div class="product-grid">
                    <c:forEach var="p" items="${listP}">
                        <a href="detailController?pid=${p.productId}" style="text-decoration:none; color:inherit;">
                            <div class="product">
                                <c:choose>
                                    <c:when test="${not empty p.image}">
                                        <img src="data:image/jpeg;base64,${p.image}" alt="${p.productName}">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://via.placeholder.com/250x200?text=No+Image" alt="No image">
                                    </c:otherwise>
                                </c:choose>
                                <h4>${p.productName}</h4>
                                <p class="product-price">
                                <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> VND
                                </p>
                                <p>Màu: ${p.color}</p>
                                <p>Size: ${p.size}</p>
                                <p>Tồn kho: ${p.quantity}</p>
                            </div>
                        </a>
                    </c:forEach>
                </div>

                <h3 style="text-align:center;">Tổng cộng: ${fn:length(listP)} sản phẩm</h3>
            </c:when>

            <c:otherwise>
                <div class="empty">
                    <h3>Không có sản phẩm nào để hiển thị.</h3>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="container mt-4">
            <jsp:include page="footer.jsp"/>
        </div>

    </body>
</html>
