<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<h1 class="page-title">Error</h1>
<section class="panel">
    <div class="error">${error}</div>
    <a class="btn" href="${pageContext.request.contextPath}/products">Back to catalog</a>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
