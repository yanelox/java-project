<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<h1 class="page-title">Login</h1>
<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
<section class="panel" style="max-width: 480px;">
    <form class="form-stack" method="post" action="${pageContext.request.contextPath}/login">
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button class="btn" type="submit">Sign in</button>
    </form>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
