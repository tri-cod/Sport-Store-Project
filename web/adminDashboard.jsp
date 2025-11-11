<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang Quản Trị</title>
        <style>
            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background: #f3f6f9;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
            }

            h1 {
                margin: 15px 0;
                font-size: 22px;
            }

            .container {
                background: white;
                border-radius: 12px;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
                padding: 40px 60px;
                text-align: center;
                width: 400px;
            }

            a {
                text-decoration: none;
                color: white;
                background: #007bff;
                padding: 12px 20px;
                border-radius: 8px;
                display: inline-block;
                transition: 0.3s;
                font-weight: 500;
            }

            a:hover {
                background: #0056b3;
                transform: translateY(-2px);
            }

            hr {
                border: none;
                height: 1px;
                background: #ddd;
                margin: 25px 0;
            }

            .back-home {
                background: #28a745;
            }

            .back-home:hover {
                background: #1e7e34;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h2>🔧 Trang Quản Trị</h2>
            <hr>

            <h1><a href="listOrder">📦 Quản lý đơn hàng</a></h1>
            <h1><a href="productController">🛍️ Quản lý sản phẩm</a></h1>
            <h1><a href="managerCategoryController">📂 Quản lý danh mục</a></h1>

            <hr>
            <a href="mainController" class="back-home">🏠 Quay lại Trang chủ</a>
        </div>
    </body>
</html>
