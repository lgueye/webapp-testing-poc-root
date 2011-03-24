<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />
<c:choose>
<c:when test="${not empty users}">
	<table width="300px">
		<tr>
			<thead>
				<th>Id</th>
				<th>Username</th>
				<th>Password</th>
				<th />
				<th />
				<th />
			</thead>
		</tr>
		<c:forEach items="${users}" var="usr">
			<tr>
				<td>${usr.id}</td>
				<td>${usr.username}</td>
				<td>***********</td>
				<td>
				    <form:form action="${pageContext.request.contextPath}/users/load/${usr.id}" method="GET">
						<input alt="Display account" src="${pageContext.request.contextPath}/public/images/show.png" title="Display account" type="image" value="Display account" />
				    </form:form>
				</td>
				<td>
				    <form:form action="${pageContext.request.contextPath}/users/edit/${usr.id}" method="GET">
						<input alt="Edit account" src="${pageContext.request.contextPath}/public/images/update.png" title="Edit account" type="image" value="Edit account" />
                    </form:form>
                </td>
				<td>
				    <form:form action="${pageContext.request.contextPath}/users/delete/${usr.id}" method="DELETE">
					   <input alt="Delete user" src="${pageContext.request.contextPath}/public/images/delete.png" title="Delete user" type="image" value="Delete user" />
				    </form:form>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:when>
<c:otherwise>No user found.</c:otherwise>
</c:choose>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
