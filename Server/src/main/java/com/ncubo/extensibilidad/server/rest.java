package com.ncubo.extensibilidad.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class rest {

	
	String message = "Welcome to Spring MVC!";
	 
	@RequestMapping("/hello")
	public @ResponseBody String showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "holaMundo";
	}
	
}
