package com.johnhancock.myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.johnhancock.myapp.fbloginmodel.FBConnection;
import com.johnhancock.myapp.fbloginmodel.FBGraph;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	private String code = "";

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/fbhome", method = RequestMethod.GET)
	public String authenticate(HttpServletRequest req, HttpServletResponse res, Model model) {
		logger.info("In authenticate of FbAuthenticationController..");
		code = req.getParameter("code");

		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);

		FBGraph fbGraph = new FBGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		HttpSession userSession = req.getSession();
		userSession.setAttribute("accesscode", accessToken);

			
		model.addAttribute("name", fbProfileData.get("name"));
		model.addAttribute("email", fbProfileData.get("email"));
		model.addAttribute("userid", Long.parseLong(fbProfileData.get("id")));

		return "home";
	}
	
	@RequestMapping(value = "logout.htm", method = RequestMethod.GET)
	public String logout(Locale locale, Model model,HttpServletRequest req,HttpServletResponse res) {
		logger.info("Visit Again! The client locale is {}.", locale);
		HttpSession session=req.getSession();
		//System.out.println(session.getAttribute("accesscode"));
		/*
		code = session.getAttribute("accesscode").toString();

		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		String accessToken = fbConnection.getAccessToken(code);
		
		String logoutUrl = fbConnection.getLogOutURL(accessToken);
		System.out.println(logoutUrl);
		URL fbGraphURL;
		try {
			fbGraphURL = new URL(logoutUrl);
			System.out.println(fbGraphURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid code received " + e);
		}
		URLConnection fbDisconnection;	
		StringBuffer b = null;
		try {
			fbDisconnection = fbGraphURL.openConnection();
			//fbDisconnection.connect();
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(
					fbDisconnection.getInputStream()));
			String inputLine;
			b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			System.out.println("Getting out");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to connect with Facebook "
					+ e);
		}
		*/
		
        session.invalidate();  
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
