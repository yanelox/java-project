<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<h1 class="page-title">Checkout</h1>
<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
<section class="panel">
    <c:choose>
        <c:when test="${empty cartItems}">
            <p class="muted">There are no items to checkout.</p>
            <a class="btn" href="${pageContext.request.contextPath}/products">Return to catalog</a>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Qty</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.quantity}</td>
                            <td>$${item.price}</td>
                            <td>$${item.subtotal}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <p class="price">Total amount: $${total}</p>
            <form method="post" action="${pageContext.request.contextPath}/checkout">
                <button class="btn" type="submit">Place order</button>
            </form>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
