<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>About Us - Fashion Store</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            margin:0;
            background-color:#f4f6f8;
        }

        /* Header */
        .top-bar {
            background-color:#007bff;
            color:white;
            display:flex;
            justify-content: space-between;
            align-items:center;
            padding:12px 30px;
        }

        .top-bar a {color:white; text-decoration:none; margin-left:15px;}
        .top-bar a:hover {text-decoration:underline;}

        .page-title {
            text-align:center;
            margin:30px 0;
            color:#007bff;
        }

        /* About container */
        .about-container {
            max-width:1200px;
            margin:auto;
            padding:20px;
            display:flex;
            flex-wrap:wrap;
            gap:40px;
            justify-content: space-between;
        }

        .about-text, .about-image {
            flex:1;
            min-width:300px;
            background:white;
            border-radius:10px;
            padding:25px;
            box-shadow:0 2px 8px rgba(0,0,0,0.1);
        }

        .about-text h2, .values h3 {
            color:#007bff;
            margin-bottom:15px;
        }

        .about-text p {
            font-size:14px;
            line-height:1.6;
        }

        /* Team or image */
        .about-image img {
            width:100%;
            border-radius:10px;
        }

        /* Values / highlights */
        .values {
            display:flex;
            flex-wrap:wrap;
            gap:20px;
            margin-top:30px;
            justify-content:center;
        }

        .value-box {
            flex:1;
            min-width:200px;
            background:white;
            border-radius:10px;
            padding:20px;
            text-align:center;
            box-shadow:0 2px 8px rgba(0,0,0,0.1);
        }

        .value-box h4 {
            color:#007bff;
            margin-bottom:10px;
        }

        .value-box p {
            font-size:14px;
        }

        footer {
            background-color:#007bff;
            color:white;
            text-align:center;
            padding:30px 0;
            margin-top:40px;
        }

    </style>
</head>
<body>

    <!-- Header -->
    <div class="top-bar">
        <strong>🛍️ Fashion Store</strong>
        <div>
            <a href="home.jsp">Home</a>
            <a href="categoryController">Products</a>
            <a href="about.jsp">About</a>
            <a href="contact.jsp">Contact</a>
            <c:if test="${sessionScope.user != null}">
                <a href="mainController?txtAction=logout">Logout</a>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <a href="login.jsp">Login</a>
            </c:if>
        </div>
    </div>

    <h1 class="page-title">About Us</h1>

    <div class="about-container">
        <!-- Text -->
        <div class="about-text">
            <h2>Our Story</h2>
            <p>
                Fashion Store ra đời với sứ mệnh mang đến những sản phẩm thời trang chất lượng, hợp xu hướng và giá cả hợp lý.
                Chúng tôi luôn đặt khách hàng lên hàng đầu, đảm bảo trải nghiệm mua sắm tốt nhất.
            </p>
            <p>
                Với đội ngũ thiết kế chuyên nghiệp và hệ thống phân phối rộng khắp, Fashion Store tự tin trở thành lựa chọn số 1 cho những tín đồ thời trang.
            </p>
        </div>

    <!-- Values / Highlights -->
    <div class="values">
        <div class="value-box">
            <h4>Quality Products</h4>
            <p>Sản phẩm thời trang chất lượng, được tuyển chọn kỹ lưỡng.</p>
        </div>
        <div class="value-box">
            <h4>Fast Delivery</h4>
            <p>Giao hàng nhanh chóng, đúng hẹn trên toàn quốc.</p>
        </div>
        <div class="value-box">
            <h4>Customer Support</h4>
            <p>Hỗ trợ khách hàng tận tình, giải đáp mọi thắc mắc.</p>
        </div>
        <div class="value-box">
            <h4>Latest Trends</h4>
            <p>Luôn cập nhật xu hướng thời trang mới nhất.</p>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        © 2025 Fashion Store. All Rights Reserved.
    </footer>

</body>
</html>
