<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Internet Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css?v=2">
</head>
<body>
<header class="site-header">
    <div class="container header-row">
        <a class="brand" href="${pageContext.request.contextPath}/products">Internet Shop</a>
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/products">Products</a>
            <a href="${pageContext.request.contextPath}/categories">Categories</a>
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <a href="${pageContext.request.contextPath}/cart">Cart</a>
                    <a href="${pageContext.request.contextPath}/orders">Orders</a>
                    <span class="muted">Hello, ${sessionScope.currentUser.name}</span>
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button type="submit">Logout</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                    <a href="${pageContext.request.contextPath}/register">Register</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>
<main class="container">
