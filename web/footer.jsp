<%@page contentType="text/html" pageEncoding="UTF-8"%>
<footer style="background-color:#007bff; color:#fff; padding:50px 0; font-family:'Segoe UI', Arial, sans-serif;">
    <div style="max-width:1200px; margin:auto; display:flex; flex-wrap:wrap; justify-content:space-between; gap:30px;">

        <!-- About -->
        <div style="flex:1; min-width:200px;">
            <h3 style="margin-bottom:15px; color:#fff;">Fashion Store</h3>
            <p style="font-size:14px; line-height:1.6;">
                Chúng tôi mang đến những sản phẩm thời trang chất lượng, cập nhật xu hướng mới nhất.
            </p>
            <p style="margin-top:15px; font-size:14px;">
                📍 123 Fashion Street, HCM<br/>
                📞 +84 123 456 789<br/>
                ✉️ support@fashionstore.com
            </p>
        </div>

        <!-- Quick Links -->
        <div style="flex:1; min-width:150px;">
            <h4 style="margin-bottom:15px; color:#fff;">Quick Links</h4>
            <ul style="list-style:none; padding:0; font-size:14px;">
                <li><a href="homeController" style="color:#fff; text-decoration:none;">Home</a></li>
                <li><a href="categoryController" style="color:#fff; text-decoration:none;">Products</a></li>
                <li><a href="about.jsp" style="color:#fff; text-decoration:none;">About Us</a></li>
                <li><a href="contact.jsp" style="color:#fff; text-decoration:none;">Contact</a></li>
            </ul>
        </div>

        <!-- Social Media (chỉ chữ) -->
        <div style="flex:1; min-width:150px;">
            <h4 style="margin-bottom:15px; color:#fff;">Follow Us</h4>
            <ul style="list-style:none; padding:0; font-size:14px;">
                <li><a href="https://www.facebook.com" target="_blank" style="color:#fff; text-decoration:none;">Facebook</a></li>
                <li><a href="https://www.instagram.com" target="_blank" style="color:#fff; text-decoration:none;">Instagram</a></li>
                <li><a href="https://twitter.com" target="_blank" style="color:#fff; text-decoration:none;">Twitter</a></li>
                <li><a href="https://www.youtube.com" target="_blank" style="color:#fff; text-decoration:none;">YouTube</a></li>
            </ul>
        </div>

        <!-- Newsletter -->
        <div style="flex:1; min-width:200px;">
            <h4 style="margin-bottom:15px; color:#fff;">Newsletter</h4>
            <p style="font-size:14px;">Đăng ký nhận thông tin khuyến mãi mới nhất:</p>
            <form id="newsletterForm" style="display:flex; gap:10px; margin-top:10px;" onsubmit="return submitNewsletter();">
                <input type="email" name="email" id="newsletterEmail" placeholder="Nhập email của bạn" required
                       style="flex:1; padding:8px; border-radius:5px; border:none; outline:none;">
                <button type="submit" style="padding:8px 12px; border:none; border-radius:5px; background-color:#fff; color:#007bff; cursor:pointer;">Subscribe</button>
            </form>
            <div id="newsletterMsg" style="margin-top:10px; font-size:14px; color:#dff9fb;"></div>
        </div>

    </div>

    <div style="text-align:center; margin-top:30px; font-size:13px; color:#cce0ff;">
        © 2025 Fashion Store. All Rights Reserved.
    </div>

    <!-- JS cho Newsletter -->
    <script>
        function submitNewsletter() {
            var email = document.getElementById('newsletterEmail').value;
            document.getElementById('newsletterMsg').innerText = "Cảm ơn! Email " + email + " đã đăng ký thành công.";
            document.getElementById('newsletterForm').reset();
            return false; // không gửi form
        }
    </script>
</footer>
