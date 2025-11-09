<%-- File: forgotPassword.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
    <form action="mainController" method="POST">
        <input type="hidden" name="txtAction" value="requestReset" />
        <h2>Quên Mật Khẩu</h2>
        <p>Vui lòng nhập email, chúng tôi sẽ gửi link đặt lại mật khẩu.</p>
        
        <div>
            <label>Email:</label>
            <input type="email" name="txtEmail" required />
        </div>
        <button type="submit">Gửi Email Xác Nhận</button>
    </form>

    <c:if test="${not empty requestScope.msg}">
        <p style="color:blue;">${requestScope.msg}</p>
    </c:if>
</body>
</html>