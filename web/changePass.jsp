<%-- File: enterNewPassword.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <form action="mainController" method="POST">
            <input type="hidden" name="txtAction" value="performReset" />

            <input type="hidden" name="token" value="${requestScope.token}" />

            <h2>Tạo Mật Khẩu Mới</h2>
            <div>
                <label>Mật khẩu mới:</label>
                <input type="password" name="txtNewPassword" required />
            </div>
            <div>
                <label>Xác nhận mật khẩu:</label>
                <input type="password" name="txtConfirmPassword" required />
            </div>
            <button type="submit">Xác Nhận</button>
        </form>

    <c:if test="${not empty requestScope.msg}">
        <p style="color:red;">${requestScope.msg}</p>
    </c:if>
</body>
</html>