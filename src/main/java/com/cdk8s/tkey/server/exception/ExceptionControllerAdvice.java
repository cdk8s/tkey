package com.cdk8s.tkey.server.exception;

import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.enums.ResponseProduceTypeEnum;
import com.cdk8s.tkey.server.util.ExceptionUtil;
import com.cdk8s.tkey.server.util.StringUtil;
import com.cdk8s.tkey.server.util.response.R;
import com.cdk8s.tkey.server.util.response.ResponseErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final String JSON_TYPE = "application/json";
	private static final String X_TYPE = "XMLHttpRequest";


	//=====================================业务处理 start=====================================


	/**
	 * 系统异常
	 */
	@ExceptionHandler(SystemException.class)
	public Object handleSystemException(HttpServletRequest httpServletRequest, SystemException e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常，请联系管理员处理";
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest, message, null, null);
	}

	/**
	 * OAuth API 业务异常
	 */
	@ExceptionHandler(OauthApiException.class)
	public Object handleOauthApiException(HttpServletRequest httpServletRequest, OauthApiException e) {
		String message = e.getMessage();
		ResponseProduceTypeEnum responseProduceTypeEnum = e.getResponseProduceTypeEnum();
		if (null == responseProduceTypeEnum) {
			responseProduceTypeEnum = ResponseProduceTypeEnum.JSON;
		}
		String pagePath = e.getPagePath();
		if (StringUtil.isBlank(pagePath)) {
			pagePath = "error";
		}
		return getResponseObject(e, HttpStatus.BAD_REQUEST, httpServletRequest, message, responseProduceTypeEnum, pagePath);
	}


	/**
	 * 其他异常
	 */
	@ExceptionHandler(Exception.class)
	public Object handleException(HttpServletRequest httpServletRequest, Exception e) {
		String message = e.getMessage();
		if (StringUtil.isBlank(message)) {
			message = "系统发生异常";
		} else {
			log.error(message);
			for (ExceptionDescriptionEnum exceptionDescription : ExceptionDescriptionEnum.values()) {
				if (message.contains(exceptionDescription.getKeyWord())) {
					message = exceptionDescription.getDescription();
					break;
				}
			}
		}
		return getResponseObject(e, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest, message, null, null);

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private Object getResponseObject(Exception e, HttpStatus httpStatus, HttpServletRequest httpServletRequest, String message, ResponseProduceTypeEnum responseProduceTypeEnum, String pagePath) {

		log.error("统一异常信息输出：<{}>", message);
		if (log.isDebugEnabled()) {
			log.error(ExceptionUtil.getStackTraceAsString(e));
		}

		if (StringUtil.isBlank(pagePath)) {
			pagePath = "error";
		}

		// 指定返回类型
		if (null != responseProduceTypeEnum) {
			if (responseProduceTypeEnum == ResponseProduceTypeEnum.JSON) {
				return returnJson(httpStatus, message);
			}
			if (responseProduceTypeEnum == ResponseProduceTypeEnum.HTML) {
				return returnHtml(httpServletRequest, message, pagePath);
			}
		}

		// 没有指定返回类型，根据 http 请求头进行判断返回什么类型
		String contentTypeHeader = httpServletRequest.getHeader("Content-Type");
		String acceptHeader = httpServletRequest.getHeader("Accept");
		String xRequestedWith = httpServletRequest.getHeader("X-Requested-With");
		boolean checkIsJson = (contentTypeHeader != null && contentTypeHeader.contains(JSON_TYPE)) || (acceptHeader != null && acceptHeader.contains(JSON_TYPE)) || X_TYPE.equalsIgnoreCase(xRequestedWith);
		if (checkIsJson) {
			return returnJson(httpStatus, message);
		} else {
			return returnHtml(httpServletRequest, message, pagePath);
		}
	}

	private ResponseEntity<ResponseErrorObject> returnJson(HttpStatus httpStatus, String message) {
		return R.failure(httpStatus, message);
	}

	private ModelAndView returnHtml(HttpServletRequest httpServletRequest, String message, String pagePath) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(GlobalVariable.DEFAULT_LOGIN_ERROR_KEY, message);
		modelAndView.addObject("url", httpServletRequest.getRequestURL());
		modelAndView.setViewName(pagePath);
		return modelAndView;
	}

	//=====================================私有方法  end=====================================
}
