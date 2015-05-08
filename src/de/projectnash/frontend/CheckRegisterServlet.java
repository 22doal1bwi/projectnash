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

import de.projectnash.application.UserLogic;

/**
 * Servlet implementation class CheckRegisterServlet
 */
@WebServlet("/CheckRegisterServlet")
public class CheckRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean inputAlreadyExists = false;
		String keyInput = "";
		String valueInput = "";
		
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			keyInput = entry.getKey();
			valueInput = entry.getValue()[0].toString();
		}
		
		if (keyInput.equals("personalId")) {
			inputAlreadyExists = UserLogic.personalIdAlreadyExists(valueInput);	
		} else {
			inputAlreadyExists = UserLogic.emailAlreadyExists(valueInput);
		}
		
		map.put("alreadyExists", inputAlreadyExists);
		write(response, map);
	};

	private void write(HttpServletResponse resp, Map<String, Object> map)
			throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
}
