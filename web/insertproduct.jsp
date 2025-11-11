<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm sản phẩm mới</title>
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
                width: 600px;
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
                width: 150px;
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
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 15px;
                transition: background-color 0.2s;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
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
        </style>
    </head>
    <body>

        <h2>Thêm sản phẩm mới</h2>

        <h3 class="error">${requestScope.errorMessage}</h3>

        <form action="productController" method="POST" enctype="multipart/form-data">    
            <input type="hidden" name="action" value="insertProcess" />

            <table>
                <tr>
                    <td>Mã sản phẩm:</td>
                    <td><input type="text" name="productId" required /></td>
                </tr>
                <tr>
                    <td>Tên sản phẩm:</td>
                    <td><input type="text" name="productName" required /></td>
                </tr>
                <tr>
                    <td>Giá (VND):</td>
                    <td><input type="number" name="price" step="any" required /></td>
                </tr>
                <tr>
                    <td>Size:</td>
                    <td><input type="text" name="size" /></td>
                </tr>
                <tr>
                    <td>Ảnh đại diện:</td>
                    <td><input type="file" name="mainImage" required /></td>
                </tr>
                <tr>
                    <td>Ảnh gallery:</td>
                    <td><input type="file" name="galleryImages" multiple /></td>
                </tr>
                <tr>
                    <td>Màu:</td>
                    <td><input type="text" name="color" /></td>
                </tr>
                <tr>
                    <td>Số lượng:</td>
                    <td><input type="number" name="quantity" required /></td>
                </tr>
                <tr>
                    <td>Mô tả:</td>
                    <td><textarea name="description"></textarea></td>
                </tr>
                <tr>
                    <td>Category ID:</td>
                    <td><input type="text" name="categoryId" /></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <br/>
                        <input type="submit" value="Lưu sản phẩm" />
                        <a href="productController">Hủy</a>
                    </td>
                </tr>
            </table>
        </form>

        <div class="footer">
            © 2025 SportStore — Quản lý sản phẩm
        </div>

    </body>
</html>
