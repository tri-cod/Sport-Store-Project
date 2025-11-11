<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý địa chỉ</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f7f9fb;
            margin: 0;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        .address-form {
            text-align: center;
            margin-bottom: 25px;
            background: white;
            padding: 15px 25px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            display: inline-block;
        }

        input[type="text"] {
            padding: 8px 10px;
            margin: 0 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
            width: 180px;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus {
            border-color: #007bff;
            outline: none;
        }

        .btn {
            padding: 8px 14px;
            border: none;
            border-radius: 6px;
            color: white;
            cursor: pointer;
            font-weight: 500;
            transition: 0.3s;
        }

        .btn:hover {
            opacity: 0.9;
            transform: translateY(-2px);
        }

        .btn-add {
            background: #28a745;
        }

        .btn-update {
            background: #2196F3;
        }

        .btn-delete {
            background: #f44336;
        }

        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        th, td {
            border-bottom: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        tr:hover {
            background-color: #f1f7ff;
        }

        .action-cell {
            display: flex;
            flex-direction: column;
            gap: 10px;
            align-items: center;
        }

        .update-form input {
            width: 100px;
            margin: 3px 0;
            font-size: 13px;
        }

        .msg {
            color: red;
            text-align: center;
            margin-top: 15px;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 30px;
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <h2>📍 Quản lý địa chỉ nhận hàng</h2>

    <!-- Form thêm địa chỉ -->
    <form class="address-form" action="addressController" method="post">
        <input type="hidden" name="action" value="add">
        <input type="text" name="name" placeholder="Họ và tên" required>
        <input type="text" name="phone" placeholder="Số điện thoại" required>
        <input type="text" name="address" placeholder="Địa chỉ nhận hàng" required>
        <button type="submit" class="btn btn-add">➕ Thêm địa chỉ</button>
    </form>

    <!-- Hiển thị danh sách địa chỉ -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Số điện thoại</th>
                <th>Địa chỉ</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${listAddress}">
                <tr>
                    <td>${item.inforId}</td>
                    <td>${item.name}</td>
                    <td>${item.phoneNumber}</td>
                    <td>${item.address}</td>
                    <td class="action-cell">
                        <!-- Form xóa -->
                        <form action="addressController" method="post">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="infoId" value="${item.inforId}">
                            <button type="submit" class="btn btn-delete">🗑 Xóa</button>
                        </form>

                        <!-- Form cập nhật -->
                        <form action="addressController" method="post" class="update-form">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="infoId" value="${item.inforId}">
                            <input type="text" name="name" value="${item.name}" required>
                            <input type="text" name="phone" value="${item.phoneNumber}" required>
                            <input type="text" name="address" value="${item.address}" required>
                            <button type="submit" class="btn btn-update">💾 Cập nhật</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${not empty msg}">
        <p class="msg">${msg}</p>
    </c:if>

    <a href="paymentController" class="back-link">← Quay lại Thanh toán</a>
</body>
</html>
