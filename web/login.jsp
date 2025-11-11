<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(135deg, #74b9ff, #a29bfe);
                height: 100vh;
                margin: 0;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .login-container {
                background-color: #fff;
                padding: 30px 40px;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.2);
                width: 350px;
                text-align: center;
                animation: fadeIn 0.5s ease-in;
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
                margin-bottom: 20px;
                color: #2d3436;
            }

            table {
                width: 100%;
            }

            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 10px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                transition: 0.3s;
            }

            input[type="text"]:focus, input[type="password"]:focus {
                border-color: #0984e3;
                outline: none;
                box-shadow: 0 0 5px #74b9ff;
            }

            button, input[type="reset"] {
                background-color: #0984e3;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 10px 20px;
                margin: 8px 5px;
                cursor: pointer;
                transition: 0.3s;
            }

            button:hover, input[type="reset"]:hover {
                background-color: #74b9ff;
            }

            .register-btn {
                background-color: #6c5ce7;
            }

            .register-btn:hover {
                background-color: #a29bfe;
            }

            p.error {
                color: red;
                margin-top: 15px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <c:if test="${not empty user}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <div class="login-container">
            <h2>Đăng nhập</h2>
            <form action="mainController" method="post">
                <table>
                    <tr>
                        <td>Tên đăng nhập</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="txtUsername" value="${username}" required /></td>
                    </tr>
                    <tr>
                        <td>Mật khẩu</td>
                    </tr>
                    <tr>
                        <td><input type="password" name="txtPassword" required /></td>
                    </tr>
                    <tr>
                        <td>
                            <button type="submit" name="txtAction" value="login">Login</button>
                            <input type="reset" value="Reset" />
                        </td>
                    </tr>
                </table>
            </form>

            <form action="mainController" method="post">
                <input type="hidden" name="txtAction" value="register">
                <button type="submit" class="register-btn">Register</button>
            </form>

            <c:if test="${not empty msg}">
                <p class="error">${msg}</p>
            </c:if>
        </div>

    </body>
</html>
