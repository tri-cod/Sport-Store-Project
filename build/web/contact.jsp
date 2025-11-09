<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Contact Us - Fashion Store</title>
        <style>
            /* Giữ nguyên CSS của bạn */
            body {
                font-family:'Segoe UI', Arial, sans-serif;
                margin:0;
                background-color:#f4f6f8;
            }
            .top-bar {
                background-color:#007bff;
                color:white;
                display:flex;
                justify-content:space-between;
                align-items:center;
                padding:12px 30px;
            }
            .top-bar a {
                color:white;
                text-decoration:none;
                margin-left:15px;
            }
            .top-bar a:hover {
                text-decoration:underline;
            }
            .page-title {
                text-align:center;
                margin:30px 0;
                color:#007bff;
            }
            .contact-container {
                display:flex;
                flex-wrap:wrap;
                max-width:1200px;
                margin:auto;
                gap:40px;
                padding:20px;
                justify-content: space-between;
            }
            .contact-info, .contact-form {
                flex:1;
                min-width:300px;
                background:white;
                border-radius:10px;
                padding:25px;
                box-shadow:0 2px 8px rgba(0,0,0,0.1);
            }
            .contact-info h3, .contact-form h3 {
                color:#007bff;
                margin-bottom:20px;
            }
            .contact-info p {
                font-size:14px;
                margin:8px 0;
            }
            .contact-form form input, .contact-form form textarea {
                width:100%;
                padding:10px;
                margin-bottom:15px;
                border-radius:5px;
                border:1px solid #ccc;
                outline:none;
                font-size:14px;
            }
            .contact-form form textarea {
                resize:vertical;
                min-height:120px;
            }
            .contact-form form button {
                padding:10px 20px;
                background-color:#007bff;
                color:white;
                border:none;
                border-radius:5px;
                cursor:pointer;
                font-size:14px;
            }
            .contact-form form button:hover {
                background-color:#0056b3;
            }
            .success-msg {
                text-align:center;
                color:green;
                font-weight:bold;
                margin-top:20px;
            }
            .map-container {
                margin-top:30px;
                text-align:center;
            }
            .map-container iframe {
                width:90%;
                height:350px;
                border:0;
                border-radius:10px;
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

        <h1 class="page-title">Contact Us</h1>

        <div class="contact-container">

            <!-- Contact Info -->
            <div class="contact-info">
                <h3>Our Contact Info</h3>
                <p>📍 Address: 123 Fashion Street, HCM</p>
                <p>📞 Phone: +84 123 456 789</p>
                <p>✉️ Email: support@fashionstore.com</p>
                <p>⏰ Working Hours: Mon-Fri 9:00 - 18:00</p>
            </div>

            <!-- Contact Form -->
            <div class="contact-form">
                <h3>Send Us a Message</h3>
                <c:choose>
                    <c:when test="${not empty param.success}">
                        <p class="success-msg">✅ Cảm ơn bạn! Tin nhắn đã được gửi thành công.</p>
                    </c:when>
                    <c:otherwise>
                        <form action="contact.jsp" method="get">
                            <input type="text" name="name" placeholder="Your Name" required/>
                            <input type="email" name="email" placeholder="Your Email" required/>
                            <textarea name="message" placeholder="Your Message" required></textarea>
                            <button type="submit" name="success" value="true">Send Message</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Map -->
        <div class="map-container">
            <iframe 
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3477069933817!2d106.67732627521412!3d10.762622192329042!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f1f25a2e6f7%3A0x14b3c22ec9db2c60!2sBen%20Thanh%20Market!5e0!3m2!1sen!2s!4v1697099870123!5m2!1sen!2s" 
                allowfullscreen="" loading="lazy">
            </iframe>
        </div>

        <!-- Footer -->
        <footer>
            © 2025 Fashion Store. All Rights Reserved.
        </footer>

    </body>
</html>
