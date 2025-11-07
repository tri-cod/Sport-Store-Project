<%-- 
    Document   : insertproduct
    Created on : Nov 6, 2025, 2:25:31 AM
    Author     : btkha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm sản phẩm mới</title>
    </head>
    <body>
        <h2>Thêm sản phẩm mới</h2>

        <h3 style="color: red;">${requestScope.errorMessage}</h3>

        <form action="productController" method="POST" enctype="multipart/form-data">    
            <input type="hidden" name="action" value="insertProcess" />

            <table border="0">
                <tbody>
                    <tr>
                        <td>Product ID:</td>
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
                        <td>Chọn ảnh đại diện:</td>
                        <td><input type="file" name="mainImage" required /></td>
                    </tr>

                    <tr>
                        <td>Chọn ảnh gallery (có thể chọn nhiều):</td>
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
                        <td colspan="2">
                            <br/>
                            <input type="submit" value="Lưu sản phẩm" />
                            <a href="productController">Hủy</a> 
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>