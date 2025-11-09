<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách sản phẩm</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            .action-link {
                font-size: 20px;
                margin: 0 6px;
                transition: transform 0.2s;
                text-decoration: none;
            }
            .action-link:hover {
                transform: scale(1.2);
            }
            .action-link.edit {
                color: #f1c40f; /* vàng */
            }
            .action-link.edit:hover {
                color: #d4ac0d;
            }
            .action-link.delete {
                color: #e74c3c; /* đỏ */
            }
            .action-link.delete:hover {
                color: #c0392b;
            }
        </style>
    </head>

    <body class="container py-4">

        <h1 class="mb-4 text-primary text-center">Danh sách sản phẩm</h1>

        <!-- Form tìm kiếm -->
        <form action="MainController" method="post" class="row g-2 mb-4">
            <input type="hidden" name="txtAction" value="searchProduct"/>
            <div class="col-auto">
                <input type="text" class="form-control" name="txtSearch" placeholder="Nhập tên sản phẩm..." value="${searchKey}"/>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
            </div>
        </form>

        <!-- Bảng sản phẩm -->
        <c:choose>
            <c:when test="${not empty listP}">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                        <thead class="table-dark text-center">
                            <tr>
                                <th>ID</th>
                                <th>Ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th>Giá (VND)</th>
                                <th>Màu</th>
                                <th>Size</th>
                                <th>Tồn kho</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${listP}">
                                <tr>
                                    <td class="text-center">${p.productId}</td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${not empty p.image}">
                                                <img src="data:image/jpeg;base64,${p.image}" alt="${p.productName}" style="width:80px; height:80px; object-fit:cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://via.placeholder.com/80?text=No+Image" alt="No image" style="width:80px; height:80px;">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${p.productName}</td>
                                    <td class="text-end">
                                        <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>
                                    </td>
                                    <td class="text-center">${p.color}</td>
                                    <td class="text-center">${p.size}</td>
                                    <td class="text-center">${p.quantity}</td>
                                    <td class="text-center">

                                        <!-- Nút sửa (dùng href) -->
                                        <a href="mainController?txtAction=editProduct=${p.productId}" 
                                           class="action-link edit" 
                                           title="Sửa sản phẩm">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                        </a>

                                        <!-- Nút xóa (dùng href + confirm) -->
                                        <a href="mainController?txtAction=deleteProduct&productId=${p.productId}"
                                           class="action-link delete"
                                           title="Xóa sản phẩm"
                                           onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này không?');">
                                            <i class="fa-solid fa-trash"></i>
                                        </a>


                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <h5 class="text-center mt-4">
                    Tổng cộng: <strong>${fn:length(listP)}</strong> sản phẩm
                </h5>
            </c:when>

            <c:otherwise>
                <div class="text-center text-secondary py-5">
                    <h4>Không có sản phẩm nào để hiển thị.</h4>
                </div>
            </c:otherwise>
        </c:choose>

    </body>
</html>
