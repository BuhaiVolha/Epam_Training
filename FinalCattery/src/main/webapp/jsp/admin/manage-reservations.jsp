<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/parts/header.jsp" %>


<div class="col-lg-4 col-lg-offset-4">
    <input type="search" id="search" value="" class="form-control" placeholder="Search...">
</div>

<br>
<div class="container">
    <table id="cart" class="table table-hover table-condensed">

        <thead>
        <tr>
            <th style="width:20%">Cat</th>
            <th style="width:15%">Buyer</th>
            <th style="width:10%">Price</th>
            <th style="width:15%">Date</th>
            <th style="width:10%">Pedigree</th>
            <th style="width:22%" class="text-center">Status</th>
            <th style="width:10%"></th>
        </tr>
        </thead>
        <c:forEach items="${reservations}" var="reservation">
            <tbody>
            <tr>

                <td data-th="Product">
                    <div class="row">
                        <div class="col-sm-2 hidden-xs"><img src="http://placehold.it/100x100" alt="..."
                                                             class="img-responsive"/></div>
                        <div class="col-sm-10">
                            <h4 class="nomargin"><c:out value="${reservation.catName}"/> <c:out
                                    value="${reservation.catLastname}"/></h4>

                        </div>
                    </div>
                </td>
                <td data-th="Price"><c:out value="${reservation.userMadeReservationName}"/> <c:out
                        value="${reservation.userMadeReservationLastname}"/></td>
                <td data-th="Price"><c:out value="${reservation.totalCost}"/></td>
                <td data-th="Price"><c:out value="${reservation.dateOfReservation}"/></td>
                <td data-th="Price"><c:out value="${reservation.pedigreeType}"/></td>

                <td data-th="Subtotal" class="text-center"><p class="list-group-item-text">
                    <c:choose>
                        <c:when test="${reservation.expired}">
                            <span class="label label-danger">Expired</span>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-success">Active</span>
                        </c:otherwise>
                    </c:choose>
                </p></td>
                <td class="actions" data-th="">

                    <c:if test="${!reservation.expired}">
                        <a href="/controller?command=sell_cat&reservationId=${reservation.id}"
                           class="btn btn-info btn-sm"><i class="fas fa-hand-holding-usd"> Sell</i></a>
                    </c:if>


                </td>
            </tr>
            </tbody>
        </c:forEach>
        <tfoot>
        <tr class="visible-xs">
            <td class="text-center"><strong>Total</strong></td>
        </tr>
        <tr>
            <td colspan="2" class="hidden-xs"></td>
            <td colspan="2" class="hidden-xs"></td>
            <td colspan="2" class="hidden-xs"></td>
            <td><a href="/controller?command=decline_expired_reservations" class="btn btn-warning btn-block">Decline all
                expired <i class="fab fa-hotjar"></i></a></td>
        </tr>
        </tfoot>
    </table>
</div>
<script>
    $(function () {
        $('#cart').searchable({
            striped: true,
            oddRow: {'background-color': '#f5f5f5'},
            evenRow: {'background-color': '#fff'},
            searchType: 'fuzzy'
        });
    });
</script>
<%@ include file="/jsp/parts/footer.jsp" %>
