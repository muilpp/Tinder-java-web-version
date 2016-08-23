<%@page import="com.google.common.base.Strings"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.awt.TextField"%>
<%@page import="java.awt.TextArea"%>
<%@page import="java.awt.BorderLayout"%>
<%@page import="model.TinderClient"%>
<%@page import="model.webservice_data_model.Message"%>
<%@page import="model.webservice_data_model.Photos"%>
<%@page import="model.webservice_data_model.Person"%>
<%@page import="model.webservice_data_model.MatchDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Match Info</title>

<style>
p {
    display: block;
}
</style>
    <script type="text/javascript">
        function sendMessage(matchID) {
            var xhr = new XMLHttpRequest();
            var message = document.getElementById('message').value;
            document.getElementById('message').value = '';
            xhr.open('GET', 'controller?message='+message+'&matchID='+matchID, true);
            xhr.send();

            window.location = "controller?matches=true";
        }
    </script>

</head>
<body>

<%
    String matchID = request.getParameter("id") != null ? request.getParameter("id") : "";
    TinderClient tinderClient = new TinderClient();
    MatchDTO matchDTO = tinderClient.getMatch(matchID, request.getSession().getServletContext());

    if (matchDTO != null) {
        Person person = matchDTO.getMatch().getPerson();
%>
        <div id="match">
        <p>Name: <%=person.getName()%></p>

<%		if (!Strings.isNullOrEmpty(person.getBio())) {
%>			<p>Bio: <%=person.getBio() %></p>
<%
		}
%>
	
<%
// 		System.out.println("Time: " + person.getPosition().getTime());
//         DateTime dateTime = new DateTime(person.getPosition().getTime());
%> 
<%--         <p>Dia: <%=dateTime.getDayOfMonth()%>-<%=dateTime.getMonthOfYear()%>-<%=dateTime.getYear()%></p> --%>
<%--         <p>Hora: <%=dateTime.getHourOfDay()%>:<%=dateTime.getMinuteOfHour()%>h</p> --%>
<%-- <% --%>
<%--	DateTime birthDate = new DateTime(person.getBirth_date()); %>
<%-- %> --%>
<%-- 		<p>Birth date: <%=birthDate.getDayOfMonth()%>-<%=birthDate.getMonthOfYear()%>-<%=birthDate.getYear()%></p> --%>

<%--         <p>Lat, Lon: <%=person.getPosition().getLatitude()%>, <%=person.getPosition().getLongitude()%></p> --%>

<!-- 		<iframe width="600" height="450" frameborder="0" style="border:0" -->
<%--           src="https://www.google.com/maps/embed/v1/view?key=AIzaSyAV3hwO0kIVWHTfQSjnkMtHEBgIsIyETr8 --%>
<%--               &center=<%=person.getPosition().getLatitude()%>,<%=person.getPosition().getLongitude()%> --%>
<%--               &zoom=17" allowfullscreen> --%>
<!--         </iframe> -->

<%
        for (Photos photo : person.getPhotoList()) {
%>
            <a target="_blank" href="controller?id=<%=person.getId()%>"><img height='250' width='250' src='<%=photo.getUrl()%>'/></a>
<%
            break;
        }

%>
        <div style="width:600px; height:auto">
<%            
        if (session.getAttribute("messages") != null) {
            HashMap<String, List<Message>> messageMap = (HashMap<String, List<Message>>) session.getAttribute("messages");  

            Iterator<String> iter = messageMap.keySet().iterator();

            while (iter.hasNext()) {
                if (iter.next().toString().equals(matchID.trim())) {
			        for (Message message : messageMap.get(matchID.trim())) {
			            if (message != null) {
			                DateTime messageTime = new DateTime(message.getTimestamp());
			                String sentTime = messageTime.getYear()+"-"+messageTime.getMonthOfYear()+"-"+messageTime.getDayOfMonth()+" "+messageTime.getHourOfDay()+":"+messageTime.getMinuteOfHour()+"h";
			                if (message.getFrom().equals(person.getId())) {
%>
                                <p style="background-color:grey"><%=message.getMessage()%><span style="float:right"><%=sentTime%></span></p>
<%
			                } else {
%>
                                <p><%=message.getMessage()%><span style="float:right"><%=sentTime%></span></p>
<%
			                }
			            }
			        }
			    }
            }
        }
    }
%>
            <input type="text" id="message" size="70">
            <input type="submit" value="Enviar" onclick='sendMessage("<%=matchID%>")'>
            </div>
        </div>
</body>
</html>