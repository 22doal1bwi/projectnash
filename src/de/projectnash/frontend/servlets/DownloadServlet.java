/**
 * 
 */
package de.projectnash.frontend.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.projectnash.application.SessionLogic;
import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;
import de.projectnash.frontend.controllers.SessionController;

/**
 * Servlet which response with a file download
 * 
 * @author alexander
 *
 */
@WebServlet("/CrtDownload")
public class DownloadServlet extends HttpServlet{
	

	private static final long serialVersionUID = -2151569481809641648L;

	public void doGet(HttpServletRequest request, 
			   HttpServletResponse response) throws IOException, ServletException{
				String sessionIdStatus = SessionController.checkForSessionId(request, response);
				
				switch (sessionIdStatus) {
				default:
					User user = SessionLogic.loadSession(sessionIdStatus).getUser();
					String filename = user.getFirstName()+"_"+user.getLastName()+"_"+user.getPersonalId()+".p12";
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
				                     "attachment;filename="+filename);		 
					
					Certificate cert = user.getCertificate();
					byte[] crtBytes = cert.getCertificateFile();

					OutputStream os = response.getOutputStream();
					os.write(crtBytes);
					os.flush();
					os.close();	
					break;
				case "0":
				case "-1":
					response.sendError(501);
					break;
				}
	}
	
	
}
