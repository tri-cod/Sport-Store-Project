<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Register Page</title>
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: linear-gradient(135deg, #74b9ff, #a29bfe);
                height: 100vh;
                margin: 0;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .register-container {
                background: #fff;
                padding: 40px;
                border-radius: 15px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.15);
                width: 400px;
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
            input[type="text"], input[type="password"], input[type="date"], input[type="email"] {
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
            .btn-container {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }
            .btn {
                width: 48%;
                padding: 10px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-size: 15px;
                font-weight: 600;
                transition: 0.3s;
            }
            .btn-register {
                background: #00b894;
                color: white;
            }
            .btn-register:hover {
                background: #019874;
            }
            .btn-cancel {
                background: #b2bec3;
                color: white;
                text-decoration: none;
                text-align: center;
                line-height: 38px;
            }
            .btn-cancel:hover {
                background: #636e72;
            }

            .error-msg {
                color: red;
                font-size: 13px;
                margin-top: 5px;
                display: none;
            }
            .error-msg.client-visible {
                display: block;
            }
            .error-msg.server-visible {
                display: block;
            }
        </style>
    </head>
    <body>

        <div class="register-container">
            <h2>REGISTER ACCOUNT</h2>

            <form action="userController" method="post" id="registerForm" onsubmit="return validateForm(event)">
                <input type="hidden" name="txtAction" value="register"/>
                <c:if test="${not empty errors.mainError}">
                    <div class="error-msg server-visible" 
                         style="margin-bottom: 20px; color: #d63030; font-weight: bold;">
                        ${errors.mainError}
                    </div>
                </c:if>
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="txtFullname" id="txtFullname" value="${param.txtFullname}"/>
                    <span id="txtFullnameClientError" class="error-msg">Full name cannot be empty</span>
                    <c:if test="${not empty errors.fullnameError}">
                        <div class="error-msg server-visible">${errors.fullnameError}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="txtUsername" id="txtUsername" value="${param.txtUsername}"/>
                    <span id="txtUsernameClientError" class="error-msg">Username cannot be empty</span>
                    <c:if test="${not empty errors.usernameError}">
                        <div class="error-msg server-visible">${errors.usernameError}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="txtPassword" id="txtPassword"/>
                    <span id="txtPasswordClientError" class="error-msg">Password must be at least 6 characters</span>
                    <c:if test="${not empty errors.passwordError}">
                        <div class="error-msg server-visible">${errors.passwordError}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>Confirm Password</label>
                    <input type="password" name="txtRePassword" id="txtRePassword"/>
                    <span id="txtRePasswordClientError" class="error-msg">Password confirmation does not match</span>
                    <c:if test="${not empty errors.repassError}">
                        <div class="error-msg server-visible">${errors.repassError}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="txtEmail" id="txtEmail" value="${param.txtEmail}"/>
                    <span id="txtEmailClientError" class="error-msg">Invalid email format</span>
                    <c:if test="${not empty errors.emailError}">
                        <div class="error-msg server-visible">${errors.emailError}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>Date of Birth</label>
                    <input type="date" name="dateOfBirth" id="dateOfBirth" value="${param.dateOfBirth}"/>
                    <span id="dateOfBirthClientError" class="error-msg">Please select your date of birth</span>
                    <c:if test="${not empty errors.dobError}">
                        <div class="error-msg server-visible">${errors.dobError}</div>
                    </c:if>
                </div>

                <div class="btn-container">
                    <input type="submit" value="Register" class="btn btn-register" />
                    <a href="mainController?txtAction=login" class="btn btn-cancel">Cancel</a>
                </div>
            </form>
        </div>

        <script>
            const touchedFields = {};

            function displayClientError(fieldId, isVisible, message) {
                const errorElement = document.getElementById(fieldId + 'ClientError');
                if (errorElement) {
                    errorElement.textContent = message || errorElement.textContent;
                    if (isVisible)
                        errorElement.classList.add('client-visible');
                    else
                        errorElement.classList.remove('client-visible');
                }
            }

            function hideServerError(inputElement) {
                const serverError = inputElement.closest('.form-group').querySelector('.error-msg.server-visible');
                if (serverError)
                    serverError.classList.remove('server-visible');
            }

            function validateField(fieldId) {
                const input = document.getElementById(fieldId);
                const value = input.value.trim();
                let isValid = true;
                let errorMessage = '';

                hideServerError(input);

                switch (fieldId) {
                    case 'txtFullname':
                        if (value === '') {
                            isValid = false;
                            errorMessage = 'Full name cannot be empty';
                        }
                        break;
                    case 'txtUsername':
                        if (value === '') {
                            isValid = false;
                            errorMessage = 'Username cannot be empty';
                        }
                        break;
                    case 'txtPassword':
                        if (value.length < 6) {
                            isValid = false;
                            errorMessage = 'Password must be at least 6 characters';
                        }
                        break;
                    case 'txtRePassword':
                        const password = document.getElementById('txtPassword').value;
                        if (value !== password || value === '') {
                            isValid = false;
                            errorMessage = (value === '') ? 'Password confirmation cannot be empty' : 'Password confirmation does not match';
                        }
                        break;
                    case 'txtEmail':
                        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                        if (!emailRegex.test(value)) {
                            isValid = false;
                            errorMessage = 'Invalid email format';
                        }
                        break;
                    case 'dateOfBirth':
                                        if (value === '') {
                                                isValid = false;
                                                errorMessage = 'Please select your date of birth';
                                        } else {
                                                const selectedDate = new Date(value);
                                                const currentDate = new Date();
                                                // Đặt giờ của ngày hiện tại về 00:00:00 để chỉ so sánh ngày
                                                currentDate.setHours(0, 0, 0, 0);
                            
                                                // Kiểm tra nếu ngày được chọn lớn hơn ngày hiện tại
                                                if (selectedDate.getTime() > currentDate.getTime()) {
                                                        isValid = false;
                                                        errorMessage = 'Date of birth cannot be a future date';
                                                }
                                        }
                                        break;
                }

                if (touchedFields[fieldId])
                    displayClientError(fieldId, !isValid, errorMessage);
                return isValid;
            }

            function markAsTouched(fieldId) {
                touchedFields[fieldId] = true;
            }

            window.addEventListener('DOMContentLoaded', () => {
                const fields = ['txtFullname', 'txtUsername', 'txtPassword', 'txtRePassword', 'txtEmail', 'dateOfBirth'];
                fields.forEach(id => {
                    const input = document.getElementById(id);
                    input.addEventListener('blur', () => {
                        markAsTouched(id);
                        validateField(id);
                    });
                    input.addEventListener('input', () => {
                        if (touchedFields[id])
                            validateField(id);
                    });
                });
            });

            function validateForm(event) {
                let isFormValid = true;
                const fields = ['txtFullname', 'txtUsername', 'txtPassword', 'txtRePassword', 'txtEmail', 'dateOfBirth'];
                fields.forEach(id => {
                    touchedFields[id] = true;
                    if (!validateField(id))
                        isFormValid = false;
                });
                if (!isFormValid)
                    event.preventDefault();
                return isFormValid;
            }
        </script>

    </body>
</html>
