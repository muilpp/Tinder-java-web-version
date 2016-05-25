<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="model.TinderClient"%>
<%@page import="model.webservice_data_model.Message"%>
<%@page import="model.webservice_data_model.Match"%>
<%@page import="model.webservice_data_model.AllMatchesDTO"%>
<%@page import="model.webservice_data_model.Photos"%>
<%@page import="model.webservice_data_model.ProcessedFiles"%>
<%@page import="model.webservice_data_model.Results"%>
<%@page import="static com.google.common.base.Strings.isNullOrEmpty"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Recommendations</title>

    <style type="text/css">
        #showMessage {
		    width:300px;
		    height:20px;
		    height:auto;
		    position:fixed;
		    left:40%;
		    top:60px;
		    background-color: #383838;
		    color: #F0F0F0;
		    font-family: Calibri;
		    font-size: 20px;
		    padding:10px;
		    text-align:center;
		    border-radius: 2px;
		    -webkit-box-shadow: 0px 0px 24px -1px rgba(56, 56, 56, 1);
		    -moz-box-shadow: 0px 0px 24px -1px rgba(56, 56, 56, 1);
		    box-shadow: 0px 0px 24px -1px rgba(56, 56, 56, 1);
		}
    </style>
    
    <script type="text/javascript">
        function sendLike(resID, userName) {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'controller?like=true&id='+resID, true);
            xhr.send();

            document.getElementById(resID).style.display = "none";

            setTimeout(function() {
                showMessage('Like to ', userName);
            }, 50);

            setTimeout("hideMessage()", 4000);
        }

        function sendPass(resID, userName) {
            console.log('Entro');
            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'controller?pass=true&id='+resID, true);
            xhr.send();

            document.getElementById(resID).style.display = "none";

            setTimeout(function() {
                showMessage('Pass to ', userName);
            }, 50);

            setTimeout("hideMessage()", 3000);
        }

        function showMessage(action, userName) {
            document.getElementById("showMessage").style.display = "inline";
            
            document.getElementById("showMessage").innerHTML = action + userName;
        }

        function hideMessage() {
            document.getElementById("showMessage").style.display = "none";
        }
    </script>
</head>
<body>
    <div id="showMessage" style="display:none"></div>
<%
    String userID = request.getParameter("userID") != null ? request.getParameter("userID") : "";
    boolean showMatches = request.getParameter("showMatches") != null ? true : false;

    TinderClient tinderClient = new TinderClient();
    List<Results> recsList = new ArrayList<Results>();
    boolean showButtons = true;

    if (showMatches) {
        AllMatchesDTO matchesResult = tinderClient.getAllMatches(request.getSession().getServletContext());
        List<Match> matchList = matchesResult.getMatchList();
        HashMap<String, List<Message>> messageMap = new HashMap<String, List<Message>>();

        for (Match match : matchList) {
            int numMessages = 0;
            if (match.getMessageList().size() > 0) {
                messageMap.put(match.getId().trim(), match.getMessageList());
                numMessages = match.getMessageList().size();
            }

            if (match.getPerson()!= null) {

                List<Photos> photoList = match.getPerson().getPhotoList();
                for (Photos photo : photoList) {
                    String name = numMessages > 0 ? match.getPerson().getName() + "("+numMessages+")" : match.getPerson().getName();
%>
                    <div id="<%=match.getId()%>" style="display: inline-block; margin-right: 10px">
                        <p style="text-align: center;"><%=name%></p>
                        <a target='_blank' href="match.jsp?id=<%=match.getId()%>"><img width='75' height='75' src='<%=photo.getUrl()%>' /></a>
                    </div>
<%
                    break;
                }
            }
        }

        if (messageMap.size() > 0)
            session.setAttribute("messages", messageMap);
%>
        <br/><hr/>
        <p>Blocks list:</p>
<%
        for (String blockID : matchesResult.getBlocks()) {
%>
            <p><a target='_blank' href="match.jsp?id=<%=blockID%>"><%=blockID%></a></p>
<%
        }
    } else {
	    if (!isNullOrEmpty(userID)) {
	        recsList.add(tinderClient.getUserLikes(userID, request.getSession().getServletContext()));
	        showButtons = false;
	    } else
	        recsList = tinderClient.getRecommendations(request.getSession().getServletContext());

	    for (Results res : recsList) {
%>
	        <div id="<%=res.getId()%>">
		        <p>Name: <%=res.getName()%></p>
		        <p>Distance: <%=res.getDistance()%> miles</p>
	
<%
		        DateTime birthDate = new DateTime(res.getBirth_date());
%>
		        <p>Birth date: <%=birthDate.getDayOfMonth()%>-<%=birthDate.getMonthOfYear()%>-<%=birthDate.getYear()%></p>

<%
		        if (res.getTeaser() != null && !isNullOrEmpty(res.getTeaser().getString()) && !isNullOrEmpty(res.getTeaser().getType())) {
		           String goalInLife = ""; 

		           String goalType = res.getTeaser().getType();
		           if (goalType.contains("job") || (goalType.contains("position")))
		               goalInLife = "Job";
		           else if (goalType.contains("school"))
		               goalInLife = "College";
		           else goalInLife = goalType;
%>
		           <p><%=goalInLife%>: <%=res.getTeaser().getString()%></p>

<%
		        }

		        DateTime lastConnectionTime = new DateTime(res.getLastConnection());
%>
		        <p>Last connection: <%=lastConnectionTime.getDayOfMonth()%>-<%=lastConnectionTime.getMonthOfYear()%>-<%=lastConnectionTime.getYear()%>
		            <%=lastConnectionTime.getHourOfDay()%>:<%=lastConnectionTime.getMinuteOfHour()%>h</p>
		        <p>Connection count (not very reliable): <%=res.getConnection_count()%></p>
<%
		        if (!isNullOrEmpty(res.getBio())) {
%>
		            <p>Bio: <%=res.getBio()%></p>
<%
		        }

	            if (res.getInstagram() != null) {
%>
	                <p><u><%=res.getInstagram().getMedia_count()%></u> fotos a <a target='_blank' href="https://www.instagram.com/<%=res.getInstagram().getUsername()%>">Instagram</a></p>
<%
	            }

	            if (showButtons) {
%>
		        <p>
		            <img src='http://ih0.redbubble.net/image.48780513.6515/ap,550x550,16x12,1,transparent,t.u3.png'
		                width='75px' height='75px' onclick='sendLike("<%=res.getId()%>", "<%=res.getName()%>")' style='cursor: pointer;' /> 
	
		                &nbsp;&nbsp;&nbsp;&nbsp;
	
		                <img src='http://ih0.redbubble.net/image.48782735.6709/ap,550x550,16x12,1,transparent,t.u3.png'
		                width='75px' height='75px' onclick='sendPass("<%=res.getId()%>", "<%=res.getName()%>")' style='cursor: pointer;' />
		        </p>
<%
	            }

		        for(Photos photo : res.getPhotos()) {
		           for (ProcessedFiles file : photo.getProcessedFiles()) { 
		               String bigPic = ""; 

		               if (file.getHeight()==172) { 
		                   bigPic = file.getUrl().replace("172x172_", "");
%>
		                   <a target='_blank' href='<%=bigPic%>'><img src='<%=file.getUrl()%>' /></a> 
<%                      }
		            }
		        }
%>  
		        <hr/><br/>
	        </div>
<% 
	    }
    }
%>

</body>
</html>