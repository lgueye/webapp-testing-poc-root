<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
    <c:if test="${not empty user}">       
        <div>
            <label for="id">Id:</label>
            <div id="id">${user.id}</div>
        </div>
        <div>
            <label for="username">Username:</label>
            <div id="username">${user.username}</div>
        </div>
        <br/>
         <div>
            <label for="password">Password:</label>
            <div id="password">**************</div>
        </div>
    </c:if>
    <c:if test="${empty user}">No user found with this id.</c:if>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
