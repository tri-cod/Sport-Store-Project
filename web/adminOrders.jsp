<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý đơn hàng</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background:#f4f4f9;
            }
            .container {
                max-width: 1200px;
                margin: 40px auto;
                background:#fff;
                padding:20px;
                border-radius:10px;
                box-shadow:0 4px 12px rgba(0,0,0,0.1);
            }
            h1 {
                text-align:center;
                color:#333;
                margin-bottom:30px;
            }
            table {
                width:100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ddd;
                padding:12px;
                text-align:center;
            }
            th {
                background:#4CAF50;
                color:white;
            }
            tr:hover {
                background:#f1f1f1;
            }
            .status-Spending {
                color: #ff9800;
                font-weight:bold;
            }
            .status-Completed {
                color: #28a745;
                font-weight:bold;
            }
            .status-Failed {
                color: #f44336;
                font-weight:bold;
            }
            .status-Unpaid {
                color: #2196F3;
                font-weight:bold;
            }
            .back-btn {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border-radius: 6px;
                transition: 0.2s;
            }
            .back-btn:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Quản lý đơn hàng</h1>

            <c:if test="${empty orders}">
                <p style="text-align:center;">Chưa có đơn hàng nào.</p>
            </c:if>

            <c:if test="${not empty orders}">
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>User ID</th>
                            <th>Info ID</th>
                            <th>Ngày tạo</th>
                            <th>Giá trị</th>
                            <th>Trạng thái</th>
                            <th>Phương thức thanh toán</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="o" items="${orders}">
                            <tr>
                                <td>${o.orderId}</td>
                                <td>${o.userId}</td>
                                <td>${o.inforId != null ? o.inforId : "-"}</td>
                                <td>${o.createdDate}</td>
                                <td>${o.amountPrice} VND</td>
                                <td class="status-${o.status}">${o.status}</td>
                                <td>${o.paymentMethod != null ? o.paymentMethod : "Chưa có"}</td>
                                
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <a href="homeController" class="back-btn">← Trở về trang chủ</a>
        </div>
    </body>
</html>
