<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #74b9ff, #a29bfe);
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .container {
            background: #fff;
            padding: 40px 60px;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
            text-align: center;
            animation: fadeIn 0.6s ease;
        }

        @keyframes fadeIn {
            from {opacity: 0; transform: translateY(-10px);}
            to {opacity: 1; transform: translateY(0);}
        }

        h1 {
            color: #2d3436;
            margin-bottom: 30px;
            font-weight: 700;
        }

        .btn-custom {
            background: #0984e3;
            color: white;
            font-size: 16px;
            padding: 12px 24px;
            border-radius: 10px;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-custom:hover {
            background: #0871c2;
            text-decoration: none;
            transform: translateY(-2px);
        }

        footer {
            margin-top: 30px;
            color: #636e72;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1><i class="bi bi-box-seam"></i> Quản lý sản phẩm</h1>

        <p class="lead mb-4">
            Chào mừng bạn đến với trang quản lý sản phẩm.  
            Hãy chọn chức năng để bắt đầu thao tác.
        </p>

        <a href="productController" class="btn-custom">
            <i class="bi bi-card-checklist"></i> Xem danh sách sản phẩm
        </a>

        <footer>
            <jsp:include page="/footer.jsp" />
        </footer>
    </div>

</body>
</html>
