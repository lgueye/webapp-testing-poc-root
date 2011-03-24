<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />
<form:form action="${pageContext.request.contextPath}/users/register" method="PUT" modelAttribute="user">
    <form:errors path="*" />
    <div>
        <label for="username">Username:</label>
        <form:input cssStyle="width:250px" maxlength="30" path="username" size="30" />
    </div>
	<br />
	<div>
        <label for="password">Password:</label>
        <form:password cssStyle="width:250px" maxlength="30" path="password" size="30" />
    </div>
	<div class="submit">
	   <input id="proceed" type="submit" value="Save" />
    </div>
</form:form>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
