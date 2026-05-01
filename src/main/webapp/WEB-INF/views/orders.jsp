<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<h1 class="page-title">Order history</h1>
<section class="panel" style="margin-bottom: 32px;">
    <c:choose>
        <c:when test="${empty orders}">
            <p class="muted">No orders yet.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="order" items="${orders}">
                <article class="panel" style="margin-bottom: 16px;">
                    <p><strong>Order #${order.id}</strong></p>
                    <p class="muted">Created: ${order.createdAt}</p>
                    <p class="muted">Status: ${order.status}</p>
                    <p class="price">Total: $${order.totalAmount}</p>
                    <div class="table-wrap">
                        <table>
                            <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Qty</th>
                                <th>Subtotal</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${order.items}">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>$${item.price}</td>
                                    <td>${item.quantity}</td>
                                    <td>$${item.subtotal}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </article>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
