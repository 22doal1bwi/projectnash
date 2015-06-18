package de.projectnash.application.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * This class provides all methods to handle with {@link HttpServletResponse}s.
 * 
 * @author Silvio D'Alessandro
 *
 */
public class ServletResponseHandler {

	/**
	 * Writes the {@link Map} into the {@link HttpServletResponse} (.json).
	 * 
	 * @param resp The {@link HttpServletResponse} that will be sent to the frontend.
	 * @param map The {@link Map} that contains all necessary data for frontend logic.
	 * @throws IOException
	 */
	public static void write(HttpServletResponse resp, Map<String, Object> map) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(map));
	}
}