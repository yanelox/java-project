<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<h1 class="page-title">Cart</h1>
<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
<section class="panel">
    <c:choose>
        <c:when test="${empty cartItems}">
            <p class="muted">Your cart is empty.</p>
            <a class="btn" href="${pageContext.request.contextPath}/products">Go to products</a>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>$${item.price}</td>
                            <td>
                                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/cart/update">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" style="width: 84px;">
                                    <button class="btn btn-secondary" type="submit">Update</button>
                                </form>
                            </td>
                            <td>$${item.subtotal}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/cart/remove">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <button class="btn btn-secondary" type="submit">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <p class="price">Total: $${total}</p>
            <a class="btn" href="${pageContext.request.contextPath}/checkout">Proceed to checkout</a>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
