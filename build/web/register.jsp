<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #81ecec, #74b9ff);
                height: 100vh;
                margin: 0;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .register-container {
                background-color: #fff;
                width: 420px;
                padding: 30px 40px;
                border-radius: 15px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
                animation: fadeIn 0.6s ease-in;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(-20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            h2 {
                text-align: center;
                color: #2d3436;
                margin-bottom: 25px;
            }

            table {
                width: 100%;
            }

            td {
                padding: 8px 0;
            }

            input[type="text"],
            input[type="password"],
            input[type="date"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
                transition: border 0.3s;
            }

            input[type="text"]:focus,
            input[type="password"]:focus,
            input[type="date"]:focus {
                border-color: #0984e3;
                outline: none;
                box-shadow: 0 0 5px #74b9ff;
            }

            input[type="reset"],
            input[type="submit"] {
                background-color: #0984e3;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 6px;
                cursor: pointer;
                transition: 0.3s;
            }

            input[type="reset"]:hover,
            input[type="submit"]:hover {
                background-color: #74b9ff;
            }

            .footer-text {
                text-align: center;
                margin-top: 15px;
                font-size: 14px;
            }

            .footer-text a {
                color: #0984e3;
                text-decoration: none;
                font-weight: bold;
            }

            .footer-text a:hover {
                text-decoration: underline;
            }

            p.error {
                color: red;
                text-align: center;
                margin-top: 15px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="register-container">
            <h2>Đăng ký tài khoản</h2>

            <form action="mainController" method="post">
                <input type="hidden" name="txtAction" value="register"/>

                <table>
                    <tr>
                        <td>Họ và tên</td>
                        <td><input type="text" name="txtFullname" required /></td>
                    </tr>

                    <tr>
                        <td>Tên đăng nhập</td>
                        <td><input type="text" name="txtUsername" value="${username}" required /></td>
                    </tr>

                    <tr>
                        <td>Mật khẩu</td>
                        <td><input type="password" name="txtPassword" required /></td>
                    </tr>

                    <tr>
                        <td>Nhập lại mật khẩu</td>
                        <td><input type="password" name="txtRePassword" required /></td>
                    </tr>

                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="txtEmail" required /></td>
                    </tr>

                    <tr>
                        <td>Ngày sinh</td>
                        <td><input type="date" name="dateOfBirth" required /></td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center">
                            <input type="reset" value="Reset" />
                            <input type="submit" value="Register" />
                        </td>
                    </tr>
                </table>
            </form>

            <div class="footer-text">
                <p>Bạn đã có tài khoản? 
                    <a href="mainController?txtAction=login">Đăng nhập</a>
                </p>
            </div>

            <c:if test="${not empty msg}">
                <p class="error">${msg}</p>
            </c:if>
        </div>
    </body>
</html>
