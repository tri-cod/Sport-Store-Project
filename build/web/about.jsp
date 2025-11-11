<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>About Us - Fashion Store</title>
    
    <link rel="stylesheet" href="css/common.css"> 
    <link rel="stylesheet" href="css/about.css"> 
    
    </head>
<body>

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
    </div>
        
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

    <jsp:include page="/footer.jsp" /> 

</body>
</html>