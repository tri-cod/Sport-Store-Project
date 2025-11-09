<%-- 
    Document   : header
    Created on : Nov 8, 2025, 4:26:15 AM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <style>
            body {
                font-family: "Segoe UI", Arial, sans-serif;
                background-color: #f4f6f8;
                margin: 0;
            }

            /* Header */
            .top-bar {
                background-color: #007bff;
                color: white;
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 12px 30px;
            }

            .top-bar a {
                color: white;
                text-decoration: none;
                background-color: #0056b3;
                padding: 8px 16px;
                border-radius: 6px;
                font-weight: 600;
                transition: background-color 0.2s;
            }

            .top-bar a:hover {
                background-color: #003d80;
            }
        </style>
        <!-- Header -->
        <div class="top-bar">
            <div>
                <strong style="font-size:18px;">🛍️ Fashion Store</strong>
            </div>
            <div>
                <c:if test="${sessionScope.user != null}">
                    <c:if test="${sessionScope.user.isAdmin==true}">
                        <a href="adminDashboard.jsp" >Admin Dashboard,</a>
                    </c:if>
                    <span>Xin chào, <b>${sessionScope.user.fullName}</b>!</span>
                    <a href="mainController?txtAction=viewCart&userId=${sessionScope.user.userId}" >Xem gio hang</a>
                    <a href="mainController?txtAction=logout">Đăng xuất</a>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <a href="login.jsp">Đăng nhập</a>
                </c:if>
            </div>
        </div>
    </body>
</html>
