<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>💳 Thanh toán đơn hàng</title>
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
            .payment-method, .shipping-info {
                width: 80%;
                margin: 30px auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            }
            .payment-method h3, .shipping-info h3 {
                color: #333;
                margin-bottom: 15px;
            }
            .payment-method label, .shipping-info label {
                display: block;
                padding: 8px 0;
            }
            .checkout {
                text-align: center;
                margin: 30px;
            }
            .checkout button {
                padding: 12px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                color: white;
            }
            .back-link {
                display: block;
                text-align: center;
                margin-top: 30px;
                text-decoration: none;
                color: #007bff;
            }
            .back-link:hover {
                text-decoration: underline;
            }
            input[type="text"], input[type="tel"], textarea {
                width: 100%;
                padding: 8px;
                margin: 5px 0 15px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>

        <div class="container mt-4">
            <jsp:include page="header.jsp"/>
        </div>

        <h1>💳 Thanh toán đơn hàng</h1>

        <c:if test="${empty listP}">
            <p style="text-align:center;">Không có sản phẩm nào để thanh toán.</p>
        </c:if>

        <c:if test="${not empty listP}">
            <table>
                <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
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
                            <td>${item.quantity}</td>
                            <td>
                                <c:set var="subtotal" value="${item.product.price * item.quantity}" />
                                ${subtotal}
                                <c:set var="total" value="${total + subtotal}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="total">
                Tổng cộng: <span style="color:#e63946;">${amountPrice} VND</span>
            </div>

            <!-- Nút chuyển sang trang quản lý địa chỉ -->
            <div class="checkout" style="margin-top: 15px;">
                <a href="address" 
                   style="display:inline-block; background-color:#28a745; color:white; padding:10px 20px; border-radius:6px; text-decoration:none;">
                    🛠 Quản lý địa chỉ
                </a>
            </div>

            <!-- 🟧 Form điền thông tin người nhận -->
            <form action="addressController?action=loadAddressList" method="post"> <!-- ⬅ Chuyển sang Servlet quản lý address -->
                <label for="selectedAddress">Chọn địa chỉ:</label>
                <select id="selectedAddress" name="selectedAddress">
                    <c:forEach var="addr" items="${listAddress}">
                        <option value="${addr.inforId}">
                            ${addr.name} - ${addr.phoneNumber} - ${addr.address}
                        </option>
                    </c:forEach>
                </select>
                <div class="checkout">
                    <button type="submit" name="txtAction" value="useSelectedAddress" style="background-color:#007bff;">
                        ✅ Chọn địa chỉ
                    </button>
                </div>
            </form>

            <!-- 🟩/🟦 Phương thức thanh toán -->
            <div class="payment-method">
                <h3>Chọn phương thức thanh toán:</h3>

                <!-- COD -->
                <form action="CODpayment" method="post" style="margin-bottom: 20px;">
                    <input type="hidden" name="paymentMethod" value="COD">
                    <input type="hidden" name="totalBill" value="${amountPrice}">
                    <div class="checkout">
                        <button type="submit" style="background-color:#007bff;">💵 Thanh toán khi nhận hàng (COD)</button>
                    </div>
                </form>

                <!-- VNPay -->
                <form action="payment" method="post">
                    <input type="hidden" name="paymentMethod" value="VNPay">
                    <input type="hidden" name="totalBill" value="${amountPrice}">
                    <div class="checkout">
                        <button type="submit" style="background-color:#28a745;">💳 Thanh toán qua VNPay</button>
                    </div>
                </form>
            </div>

        </c:if>

        <a href="cartController" class="back-link">← Quay lại giỏ hàng</a>

        <div class="container mt-4">
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
