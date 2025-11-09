<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Quản lý địa chỉ</title>
        <style>
            table {
                width: 80%;
                margin:auto;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 10px;
                text-align:center;
            }
            th {
                background: #4CAF50;
                color:white;
            }
            form {
                display:inline;
            }
            .btn {
                padding:5px 10px;
                border:none;
                border-radius:4px;
                cursor:pointer;
            }
            .btn-add {
                background: #28a745;
                color:white;
            }
            .btn-update {
                background: #2196F3;
                color:white;
            }
            .btn-delete {
                background: #f44336;
                color:white;
            }
        </style>
    </head>
    <body>
        <h2 style="text-align:center;">Quản lý địa chỉ nhận hàng</h2>

        <!-- Form thêm địa chỉ -->
        <form action="address" method="post" style="text-align:center;margin-bottom:20px;">
            <input type="hidden" name="action" value="add">
            Name: <input type="text" name="name" required>
            Phone: <input type="text" name="phone" required>
            Address: <input type="text" name="address" required>
            <button type="submit" class="btn btn-add">Thêm</button>
        </form>

        <!-- Hiển thị danh sách -->
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${listAddress}">
                    <tr>
                        <td>${item.name}</td>
                        <td>${item.phoneNumber}</td>
                        <td>${item.address}</td>
                        <td>
                            <!-- Form xóa -->
                            <form action="address" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="infoId" value="${item.inforId}">
                                <button type="submit" class="btn btn-delete">Xóa</button>
                            </form>
                            <!-- Form sửa (hiện ngay dưới bảng, bạn có thể customize modal nếu muốn) -->
                            <form action="address" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="infoId" value="${item.inforId}">
                                Name: <input type="text" name="name" value="${item.name}" required>
                                Phone: <input type="text" name="phone" value="${item.phoneNumber}" required>
                                Address: <input type="text" name="address" value="${item.address}" required>
                                <button type="submit" class="btn btn-update">Cập nhật</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${not empty msg}">
                <p style="color:red; text-align:center;">${msg}</p>
            </c:if>
        </tbody>
    </table>
</body>
</html>
