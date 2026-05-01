<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<h1 class="page-title">Categories</h1>
<section class="grid" style="margin-bottom: 32px;">
    <c:forEach var="category" items="${categories}">
        <article class="panel">
            <div class="eyebrow">Category</div>
            <h3>${category.name}</h3>
            <p class="muted">${category.description}</p>
            <a class="btn" href="${pageContext.request.contextPath}/products?categoryId=${category.id}">Open products</a>
        </article>
    </c:forEach>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
