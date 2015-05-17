package de.projectnash.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sessionIdStatus = SessionController.checkForSessionId(request,
				response);
		User user = SessionLogic.loadSession(sessionIdStatus).getUser();

		Map<String, Object> map = new HashMap<String, Object>();
		boolean changeSuccessful = false;
		String passwordCurrent = "";
		String passwordNew = "";
		
		for (Entry<String, String[]> entry : request.getParameterMap()
				.entrySet()) {				
			if (entry.getKey().equals("password_current")) {
				passwordCurrent = entry.getValue()[0].toString();
			} else if (entry.getKey().equals("password_new")) {
				passwordNew = entry.getValue()[0].toString();
			}
		}
			changeSuccessful = UserLogic.changePassword(user, passwordCurrent, passwordNew);
			map.put("pwChangeSuccessful", changeSuccessful);
	
		write(response, map);
	};

	private void write(HttpServletResponse resp, Map<String, Object> map)
			throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
}
