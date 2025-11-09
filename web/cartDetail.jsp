<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>🛒 Giỏ hàng của bạn</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 40px;
                background-color: #f5f5f5;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            table {
                width: 90%;
                margin: 0 auto;
                border-collapse: collapse;
                background-color: #fff;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            }

            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: center;
            }

            th {
                background-color: #4CAF50;
                color: white;
            }

            img {
                width: 90px;
                height: 90px;
                object-fit: cover;
                border-radius: 8px;
            }

            input[type="number"] {
                width: 60px;
                padding: 4px;
                text-align: center;
            }

            .btn {
                padding: 6px 12px;
                background-color: #f44336;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .btn:hover {
                background-color: #d32f2f;
            }

            .btn-update {
                background-color: #2196F3;
            }

            .btn-update:hover {
                background-color: #1976D2;
            }

            .checkout {
                text-align: right;
                margin: 30px 100px;
            }

            .checkout button {
                background-color: #28a745;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
            }

            .checkout button:hover {
                background-color: #218838;
            }

            .total {
                text-align: right;
                font-weight: bold;
                font-size: 18px;
                margin: 20px 100px;
            }
        </style>
    </head>
    <body>

        <div class="container mt-4">
            <jsp:include page="header.jsp"/>
        </div>

        <h1>🛍 Giỏ hàng của bạn</h1>

        <c:if test="${empty listP}">
            <p style="text-align:center;">Giỏ hàng hiện đang trống.</p>
        </c:if>

        <c:if test="${not empty listP}">
            <table>
                <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Size</th>
                        <th>Màu</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="total" value="0" />
                    <c:forEach var="item" items="${listP}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty item.product.image}">
                                        <img src="data:image/jpeg;base64,${item.product.image}" alt="${item.product.productName}">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://via.placeholder.com/90x90?text=No+Image" alt="No Image">
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>${item.product.productName}</td>
                            <td>${item.product.price} VND</td>
                            <td>${item.product.size}</td>
                            <td>${item.product.color}</td>

                            <!-- Cho phép chỉnh số lượng -->
                            <td>
                                <input type="number" name="quantity" value="${item.quantity}" min="1">
                            </td>

                            <!-- Thành tiền -->
                            <td>
                                <c:set var="subtotal" value="${item.product.price * item.quantity}" />
                                ${subtotal}
                                <c:set var="total" value="${total + subtotal}" />
                            </td>

                            <td>
                                <!-- Nút cập nhật -->
                                <form action="" method="post" style="display:inline;">
                                    <!-- ⬅ chỗ này bạn điền servlet update -->
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <input type="hidden" name="productId" value="${item.product.productId}">
                                    <button type="submit" class="btn btn-update">Cập nhật</button>
                                </form>

                                <!-- Nút xóa -->
                                <form action="removeFromCartController" method="post" style="display:inline;">
                                    <!-- ⬅ chỗ này bạn điền servlet remove -->
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <button type="submit" class="btn">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="total">
                Tổng cộng: <span style="color:#e63946;">${amountPrice} VND</span>
            </div>

            <div class="checkout">
                <form action="paymentController" method="post">
                    <!-- ⬅ chỗ này bạn điền servlet checkout -->
                    <button type="submit">💳 Thanh toán</button>
                </form>
            </div>
        </c:if>

        <a href="homeController" class="back-link">← Quay lại trang chủ</a>

        <div class="container mt-4">
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
