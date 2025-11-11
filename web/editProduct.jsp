<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@page import="DTO.productDTO"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sửa sản phẩm</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f9fafb;
                margin: 0;
                padding: 0;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-top: 30px;
            }

            form {
                width: 650px;
                margin: 20px auto;
                background: #fff;
                border-radius: 10px;
                padding: 30px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            td {
                padding: 10px;
                vertical-align: top;
            }

            td:first-child {
                width: 160px;
                font-weight: bold;
                color: #444;
            }

            input[type="text"],
            input[type="number"],
            input[type="file"],
            textarea {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
            }

            input[type="file"] {
                padding: 5px;
            }

            textarea {
                height: 80px;
                resize: vertical;
            }

            input[type="submit"] {
                background-color: #2196F3;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 15px;
                transition: background-color 0.2s;
            }

            input[type="submit"]:hover {
                background-color: #1976D2;
            }

            a {
                margin-left: 10px;
                text-decoration: none;
                color: #007BFF;
                font-weight: 500;
            }

            a:hover {
                text-decoration: underline;
            }

            img {
                border-radius: 8px;
                margin-right: 5px;
                border: 1px solid #ddd;
            }

            .error {
                color: red;
                text-align: center;
                margin-bottom: 10px;
            }

            .footer {
                text-align: center;
                margin-top: 30px;
                color: #888;
                font-size: 13px;
            }

            .img-gallery {
                display: flex;
                flex-wrap: wrap;
                gap: 8px;
            }
        </style>
    </head>
    <body>
        <%
            productDTO p = (productDTO) request.getAttribute("productToEdit");
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>

        <% if (p != null) { %>

        <h2>Sửa sản phẩm: <%= p.getProductName() %></h2>

        <% if (errorMessage != null) { %>
            <h3 class="error"><%= errorMessage %></h3>
        <% } %>

        <form action="productController" method="POST" enctype="multipart/form-data">    
            <input type="hidden" name="action" value="updateProcess" />
            
            <table>
                <tr>
                    <td>Mã sản phẩm:</td>
                    <td><input type="text" name="productId" value="<%= p.getProductId() %>" readonly /></td>
                </tr>
                <tr>
                    <td>Tên sản phẩm:</td>
                    <td><input type="text" name="productName" value="<%= p.getProductName() %>" required /></td>
                </tr>
                <tr>
                    <td>Giá (VND):</td>
                    <td><input type="number" name="price" step="any" value="<%= p.getPrice() %>" required /></td>
                </tr>
                <tr>
                    <td>Size:</td>
                    <td><input type="text" name="size" value="<%= p.getSize() %>" /></td>
                </tr>
                <tr>
                    <td>Ảnh đại diện hiện tại:</td>
                    <td>
                        <img src="data:image/jpeg;base64,<%= p.getImage() %>" width="100" height="100" alt="Ảnh chính">
                    </td>
                </tr>
                <tr>
                    <td>Chọn ảnh đại diện MỚI:</td>
                    <td><input type="file" name="mainImage" /></td>
                </tr>
                <tr>
                    <td>Ảnh gallery hiện tại:</td>
                    <td class="img-gallery">
                        <%
                            List<String> gallery = p.getGalleryImages();
                            if (gallery != null) {
                                for (String imgBase64 : gallery) {
                        %>
                            <img src="data:image/jpeg;base64,<%= imgBase64 %>" width="60" height="60" alt="Ảnh phụ">
                        <%
                                }
                            }
                        %>
                    </td>
                </tr>
                <tr>
                    <td>Chọn ảnh gallery MỚI:</td>
                    <td><input type="file" name="galleryImages" multiple /></td>
                </tr>
                <tr>
                    <td>Màu:</td>
                    <td><input type="text" name="color" value="<%= p.getColor() %>" /></td>
                </tr>
                <tr>
                    <td>Số lượng:</td>
                    <td><input type="number" name="quantity" value="<%= p.getQuantity() %>" required /></td>
                </tr>
                <tr>
                    <td>Mô tả:</td>
                    <td><textarea name="description"><%= p.getDescription() %></textarea></td>
                </tr>
                <tr>
                    <td>Category ID:</td>
                    <td><input type="text" name="categoryId" value="<%= p.getCategoryId() %>" /></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align:center;">
                        <br/>
                        <input type="submit" value="Cập nhật sản phẩm" />
                        <a href="productController">Hủy</a> 
                    </td>
                </tr>
            </table>
        </form>

        <div class="footer">
            © 2025 SportStore — Quản lý sản phẩm
        </div>

        <% } else { %>
            <h2 style="text-align:center;color:red;">Lỗi: Không tìm thấy sản phẩm.</h2>
            <div style="text-align:center;">
                <a href="productController">⬅ Quay lại danh sách</a>
            </div>
        <% } %>
    </body>
</html>
