<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<section class="hero">
    <div class="hero-card">
        <div class="eyebrow">${product.categoryName}</div>
        <h1>${product.name}</h1>
        <p>${product.description}</p>
    </div>
</section>

<section class="details-layout" style="margin-bottom: 32px;">
    <img class="card-media product-detail-media card" src="${product.imageUrl}" alt="${product.name}">
    <div class="panel">
        <div class="price">$${product.price}</div>
        <p><strong>Stock:</strong> ${product.stock}</p>
        <p class="muted">Category: ${product.categoryName}</p>
        <c:choose>
            <c:when test="${not empty sessionScope.currentUser}">
                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/cart/add">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" value="1" min="1">
                    <button class="btn" type="submit">Add to cart</button>
                </form>
            </c:when>
            <c:otherwise>
                <a class="btn" href="${pageContext.request.contextPath}/login">Login to buy</a>
            </c:otherwise>
        </c:choose>
    </div>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
