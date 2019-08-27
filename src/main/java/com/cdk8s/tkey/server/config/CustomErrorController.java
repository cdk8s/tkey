package com.cdk8s.tkey.server.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == HttpStatus.NOT_FOUND.value()) {
			return "/404";
		} else {
			return "/error";
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
