<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/parts/header.jsp" %>


<div class="container">
    <div class="row">
        <h2>Review</h2>

        <form class="form-horizontal" role="form" method="POST" action="/controller">
            <input type="hidden" name="command" value="write_review"/>
            <fieldset>
                <br>
                <br>

                <!-- Message body -->
                <div class="form-group">
                    <label class="col-md-3 control-label" for="message">Your message</label>
                    <div class="col-md-9">
                                    <textarea style="margin: 0;" class="form-control" required="required" id="message" name="message"
                                              placeholder="Please enter your review here..." rows="5"></textarea>
                    </div>
                </div>


                <!-- Rating -->
                <div class="form-group">
                    <label class="col-md-3 control-label" for="rating">Your rating</label>
                    <div class="rating" id="rating">
                        <input type="radio" required="required" id="star5" name="rating" value="5"/><label for="star5" title="Purrfect!">5 stars</label>
                        <input type="radio" required="required" id="star4" name="rating" value="4"/><label for="star4" title="Quite good">4
                        stars</label>
                        <input type="radio" required="required" id="star3" name="rating" value="3"/><label for="star3" title="Normal">3 stars</label>
                        <input type="radio" required="required" id="star2" name="rating" value="2"/><label for="star2" title="Worse than average">2
                        stars</label>
                        <input type="radio" required="required" id="star1" name="rating" value="1"/><label for="star1" title="Sucks big time">1
                        star</label>
                    </div>
                </div>

                <!-- Form actions -->
                <div class="form-group">
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-primary btn-md">Submit</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>

<%@ include file="/jsp/parts/footer.jsp" %>
