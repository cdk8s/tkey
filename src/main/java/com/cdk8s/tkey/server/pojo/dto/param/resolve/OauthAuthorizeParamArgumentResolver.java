package com.cdk8s.tkey.server.pojo.dto.param.resolve;

import com.cdk8s.tkey.server.pojo.dto.param.OauthAuthorizeParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OauthAuthorizeParamArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(OauthAuthorizeParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
		OauthAuthorizeParam param = new OauthAuthorizeParam();
		param.setResponseType(nativeWebRequest.getParameter("response_type"));
		param.setClientId(nativeWebRequest.getParameter("client_id"));
		param.setRedirectUri(nativeWebRequest.getParameter("redirect_uri"));
		param.setState(nativeWebRequest.getParameter("state"));
		return param;
	}

}
