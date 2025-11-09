<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login Page</title>
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #6c5ce7, #0984e3);
                height: 100vh;
                margin: 0;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .login-container {
                background: #fff;
                padding: 40px;
                border-radius: 15px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.15);
                width: 380px;
                animation: fadeIn 0.6s ease;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(-10px);
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

            .form-group {
                margin-bottom: 15px;
            }

            label {
                display: block;
                color: #636e72;
                margin-bottom: 5px;
                font-weight: 500;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #dfe6e9;
                border-radius: 8px;
                font-size: 14px;
                outline: none;
                transition: border-color 0.2s;
            }

            input:focus {
                border-color: #0984e3;
            }

            .btn {
                width: 100%;
                padding: 10px;
                border: none;
                border-radius: 8px;
                background: #0984e3;
                color: white;
                font-size: 15px;
                font-weight: 600;
                cursor: pointer;
                transition: background 0.3s;
                margin-top: 10px;
            }

            .btn:hover {
                background: #0871c2;
            }

            .register-link {
                text-align: center;
                margin-top: 15px;
                color: #636e72;
            }

            .register-link a {
                color: #00b894;
                text-decoration: none;
                font-weight: 600;
            }

            .register-link a:hover {
                text-decoration: underline;
            }

            .error-msg {
                text-align: center;
                color: red;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>

        <c:if test="${not empty user}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <div class="login-container">
            <h2>LOGIN</h2>

            <form action="mainController" method="post">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="txtUsername" value="${username}" required />
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="txtPassword" required />
                </div>

                <button type="submit" name="txtAction" value="login" class="btn">Login</button>
            </form>

            <div class="register-link">
                Don?t have an account? <a href="mainController?txtAction=register">Register here</a>
            </div>

            <c:if test="${not empty msg}">
                <p style="
                   text-align: center;
                   margin-top: 10px;
                   font-weight: bold;
                   color:
                   ${msg == 'Registration successful! Please login.' ? 'green' : 'red'};
                   ">
                    ${msg}
                </p>
                <c:remove var="msg" scope="session"/>
            </c:if>

        </div>

    </body>
</html>
