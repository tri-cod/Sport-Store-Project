<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>📦 Xác nhận đơn hàng (COD)</title>
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

            .total {
                text-align: right;
                font-weight: bold;
                font-size: 18px;
                margin: 20px 100px;
            }

            .btn-confirm {
                display: block;
                width: 250px;
                margin: 30px auto;
                background-color: #28a745;
                color: white;
                padding: 14px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 18px;
            }

            .btn-confirm:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>

        <h1>📦 Xác nhận đơn hàng - Thanh toán khi nhận hàng</h1>

        <c:if test="${empty listP}">
            <p style="text-align:center;">Không có sản phẩm nào trong đơn hàng.</p>
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
                            <td>${item.quantity}</td>
                            <td>
                                <c:set var="subtotal" value="${item.product.price * item.quantity}" />
                                ${subtotal}
                                <c:set var="total" value="${total + subtotal}" />
                            </td>
                            <c:if test="${not empty msg}">
                        <p style="color:red; text-align:center;">${msg}</p>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="total">
        Tổng cộng: <span style="color:#e63946;">${total} VND</span>
    </div>

    <form action="confirmCOD" method="post">
        <input type="hidden" name="totalAmount" value="${total}">
        <button type="submit" class="btn-confirm">✅ Xác nhận đơn hàng</button>
    </form>
</c:if>

</body>
</html>
