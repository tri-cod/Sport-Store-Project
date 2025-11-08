<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@page import="DTO.productDTO"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sửa sản phẩm</title>
    </head>
    <body>
        
        <%
            // Lấy DTO từ controller
            productDTO p = (productDTO) request.getAttribute("productToEdit");
            String errorMessage = (String) request.getAttribute("errorMessage");
            
            if (p != null) { 
        %>

        <h2>Sửa sản phẩm: <%= p.getProductName() %></h2>

        <% if (errorMessage != null) { %>
            <h3 style="color: red;"><%= errorMessage %></h3>
        <% } %>

        <form action="productController" method="POST" enctype="multipart/form-data">    
            <input type="hidden" name="action" value="updateProcess" />
            
            <table border="1">
                <tbody>
                    <tr>
                        <td>Product ID:</td>
                        <td>
                            <input type="text" name="productId" 
                                   value="<%= p.getProductId() %>" readonly />
                        </td>
                    </tr>
                    <tr>
                        <td>Tên sản phẩm:</td>
                        <td>
                            <input type="text" name="productName" 
                                   value="<%= p.getProductName() %>" required />
                        </td>
                    </tr>
                    <tr>
                        <td>Giá (VND):</td>
                        <td>
                            <input type="number" name="price" step="any" 
                                   value="<%= p.getPrice() %>" required />
                        </td>
                    </tr>
                    <tr>
                        <td>Size:</td>
                        <td>
                            <input type="text" name="size" 
                                   value="<%= p.getSize() %>" />
                        </td>
                    </tr>
                    
                    <tr>
                        <td>Ảnh đại diện hiện tại:</td>
                        <td>
                            <img src="data:image/jpeg;base64,<%= p.getImage() %>" 
                                 width="100" height="100" alt="Ảnh chính">
                        </td>
                    </tr>
                    <tr>
                        <td>Chọn ảnh đại diện MỚI (Bỏ qua nếu không muốn đổi):</td>
                        <td><input type="file" name="mainImage" /></td>
                    </tr>
                    
                    <tr>
                        <td>Ảnh gallery hiện tại:</td>
                        <td>
                            <%
                                List<String> gallery = p.getGalleryImages();
                                if (gallery != null) {
                                    for (String imgBase64 : gallery) {
                            %>
                                        <img src="data:image/jpeg;base64,<%= imgBase64 %>" 
                                             width="60" height="60" alt="Ảnh phụ">
                            <%
                                    }
                                }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td>Chọn ảnh gallery MỚI (Sẽ xóa hết ảnh cũ):</td>
                        <td><input type="file" name="galleryImages" multiple /></td>
                    </tr>
                    
                    <tr>
                        <td>Màu:</td>
                        <td>
                            <input type="text" name="color" 
                                   value="<%= p.getColor() %>" />
                        </td>
                    </tr>
                    <tr>
                        <td>Số lượng:</td>
                        <td>
                            <input type="number" name="quantity" 
                                   value="<%= p.getQuantity() %>" required />
                        </td>
                    </tr>
                    <tr>
                        <td>Mô tả:</td>
                        <td>
                            <textarea name="description"><%= p.getDescription() %></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>Category ID:</td>
                        <td>
                            <input type="text" name="categoryId" 
                                   value="<%= p.getCategoryId() %>" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <br/>
                            <input type="submit" value="Cập nhật sản phẩm" />
                            <a href="productController">Hủy</a> 
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        
        <%
            } else { // Nếu p (productToEdit) bị null
        %>
            <h2>Lỗi: Không tìm thấy sản phẩm.</h2>
            <a href="productController">Quay lại danh sách</a>
        <%
            }
        %>
    </body>
</html>