<?xml version="1.0"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title><fmt:message key="container-exceptions.title" /></title>
    </head>
    <body>
        <fieldset>
            <legend>Error informations</legend>
            <div>
                <label for="error.class">Error class:</label>
                <label id="error.class">${requestScope['exception'].class}</label>
            </div>
            <div>
                <label for="error.message">Error message:</label>
                <label id="error.message">${requestScope['exception'].message}</label>
            </div>
            <div>
                <label for="error.trace">Error trace:</label>
                <label id="error.trace">
                    <%
                    Exception ex = (Exception) request.getAttribute("exception");
                    ex.printStackTrace(new java.io.PrintWriter(out));
                    %>
                </label>
            </div>
        </fieldset>
    </body>
</html>
