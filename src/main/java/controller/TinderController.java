package controller;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TinderClient;

@WebServlet("/controller")
public class TinderController extends HttpServlet{
    private static final long serialVersionUID = 7048429529968476829L;
    private static final Logger LOGGER = Logger.getLogger(TinderController.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ServletContext context = request.getSession().getServletContext();

        boolean like = request.getParameter("like") != null && request.getParameter("like").equalsIgnoreCase("true") ? true : false;
        boolean pass = request.getParameter("pass") != null && request.getParameter("pass").equalsIgnoreCase("true") ? true : false;
        String userID = request.getParameter("id") != null ? request.getParameter("id").toString() : "";
        boolean showMatches = request.getParameter("matches") != null ? true : false;

        String message = request.getParameter("message") != null ? request.getParameter("message").toString() : "";
        String matchID = request.getParameter("matchID") != null ? request.getParameter("matchID").toString() : "";

        TinderClient tinderClient = new TinderClient();
        String voy = "";

        if (!isNullOrEmpty(userID)) {
            if (like) {
                tinderClient.sendLike(userID, context);
            } else if (pass) { 
                tinderClient.sendPass(userID, context);
            } else {
                voy = "recs.jsp?userID="+userID;
            }
        } else if (showMatches) {
            voy = "recs.jsp?showMatches=true";
        } else if(!isNullOrEmpty(message) && !isNullOrEmpty(matchID)) {
            tinderClient.sendMessage(message, matchID, context);
        } else {
            voy = "recs.jsp";
        }
        
        if(!isNullOrEmpty(voy)) { 
            RequestDispatcher envio = request.getRequestDispatcher(voy);
            try {
                envio.forward(request,response);
            } catch (ServletException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}