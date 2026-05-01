<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<section class="hero">
    <div class="hero-card">
        <h1>Shop everyday tech.</h1>
        <p>Browse the catalog, filter by category and move directly into a working checkout flow.</p>
    </div>
</section>

<h2 class="page-title">Products</h2>

<section class="panel">
    <form class="form-inline" method="get" action="${pageContext.request.contextPath}/products">
        <select name="categoryId">
            <option value="">All categories</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}" ${selectedCategoryId == category.id ? 'selected="selected"' : ''}>
                    ${category.name}
                </option>
            </c:forEach>
        </select>
        <button class="btn btn-secondary" type="submit">Apply filter</button>
    </form>
</section>

<section class="grid" style="margin: 20px 0 34px;">
    <c:forEach var="product" items="${products}">
        <article class="card">
            <img class="card-media" src="${product.imageUrl}" alt="${product.name}">
            <div class="card-body">
                <div class="eyebrow">${product.categoryName}</div>
                <h3>${product.name}</h3>
                <p class="muted">${product.description}</p>
                <div class="price">$${product.price}</div>
                <div class="form-inline">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/product?id=${product.id}">Details</a>
                    <c:choose>
                        <c:when test="${not empty sessionScope.currentUser}">
                            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/cart/add">
                                <input type="hidden" name="productId" value="${product.id}">
                                <input type="hidden" name="quantity" value="1">
                                <button class="btn" type="submit">Add to cart</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <a class="btn" href="${pageContext.request.contextPath}/login">Login to buy</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </article>
    </c:forEach>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
