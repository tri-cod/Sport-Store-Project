<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <h2 align="center">REGISTER FORM</h2>

        <!-- form gửi dữ liệu về servlet -->
        <form action="mainController" method="post">
            <input type="hidden" name="txtAction" value="register"/>

            <table border="1" cellpadding="5" align="center">
                <tr>
                    <td>Full Name</td>
                    <td><input type="text" name="txtFullname" required /></td>
                </tr>

                <tr>
                    <td>Username</td>
                    <td><input type="text" name="txtUsername" value="${username}" required /></td>
                </tr>
                
               
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="txtPassword" required /></td>
                </tr>
                <tr>
                    <td>Confirm Password</td>
                    <td><input type="password" name="txtRePassword" required /></td>
                </tr>

                <tr>
                    <td>Email</td>
                    <td><input type="text" name="txtEmail" required /></td>
                </tr>

                <!-- 🗓️ Thêm ngày tháng năm sinh -->
                <tr>
                    <td>Date of Birth</td>
                    <td><input type="date" name="dateOfBirth" required /></td>
                </tr>

                <tr>
                    <td colspan="2" align="center">
                        <input type="reset" value="Reset" />
                        <input type="submit" value="Register" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>ban da co tai khoan quay lai </p><a href="mainController?txtAction=login">dang nhap</a>
                    </td>
                </tr>
            </table>
        </form>


        <c:if test="${not empty msg}">
            <p style="color:red; text-align:center;">${msg}</p>
        </c:if>

    </body>
</html>
