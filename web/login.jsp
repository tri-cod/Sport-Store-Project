<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>

        <%-- Nếu đã đăng nhập, tự động chuyển về trang chủ --%>
        <c:if test="${not empty sessionScope.user}">
            <c:redirect url="homeController"/>
        </c:if>

        <form action="mainController" method="post">
            <table border="1" cellpadding="5" align="center">
                <tr>
                    <th colspan="2">LOGIN FORM</th>
                </tr>
                <tr>
                    <td>Username</td>
                    <%-- Sửa lỗi: Giữ lại username nếu nhập sai pass --%>
                    <td><input type="text" name="txtUsername" value="${param.txtUsername}" required /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="txtPassword" required /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button type="submit" name="txtAction" value="login">Login</button>
                        <input type="reset" value="Reset" />
                    </td>
                </tr>
            </table>
        </form>

        <%-- Sửa lỗi: Nút Register phải là link (GET) đến trang register.jsp --%>
        <div style="text-align:center; margin-top:10px;">
            <a href="register.jsp">
                <button type="button">Register</button>
            </a>
        </div>

        <%-- Sửa lỗi: Link "Quên mật khẩu" trỏ đến resetPass.jsp --%>
        <div style="text-align:center; margin-top:10px;">
            <a href="resetPass.jsp">Quên mật khẩu?</a>
        </div>

        <%-- Hiển thị thông báo (nếu có) --%>
        <c:if test="${not empty requestScope.msg}">
            <p style="color:red; text-align:center;">${requestScope.msg}</p>
        </c:if>

    </body>
</html>