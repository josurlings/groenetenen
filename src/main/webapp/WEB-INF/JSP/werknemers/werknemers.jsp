<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='v' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head><v:head title='Werknemers'/></head>
<body>
<v:menu/>
<h1>Werknemers</h1>

<table>
<thead><tr><th>Voornaam</th><th>Familienaam</th><th>Filiaal</th></tr></thead>
<tbody>
<c:forEach items='${werknemers}' var='werknemer'>
<tr><td>${werknemer.voornaam}</td>
<td>${werknemer.familienaam}</td>
<td>${werknemer.filiaal.naam}</td></tr>
</c:forEach>
</tbody>
</table>

</body>
</html>