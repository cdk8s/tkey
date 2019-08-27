package com.cdk8s.tkey.server.util.okhttp;

import com.cdk8s.tkey.server.util.ExceptionUtil;
import com.cdk8s.tkey.server.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
public class OkHttpService {

	@Autowired
	private OkHttpClient okHttpClient;

	//=====================================业务处理 start=====================================

	public OkHttpResponse get(String url) {
		Request request = new Request.Builder().url(url).build();
		return getResponse(request);
	}

	public OkHttpResponse get(String url, Map<String, String> queries, Map<String, String> headers) {
		StringBuilder fullUrl = new StringBuilder(url);
		if (queries != null && queries.keySet().size() > 0) {
			fullUrl.append("?");

			for (Map.Entry<String, String> entry : queries.entrySet()) {
				if (StringUtil.isNotBlank(entry.getValue()) && !StringUtil.equalsIgnoreCase(entry.getValue(), "null")) {
					fullUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
			}

			fullUrl.deleteCharAt(fullUrl.length() - 1);
		}

		Request.Builder builderRequest = new Request.Builder();

		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach(builderRequest::addHeader);
		}

		Request request = builderRequest.url(fullUrl.toString()).build();
		return getResponse(request);
	}

	public OkHttpResponse post(String url, Map<String, String> params, Map<String, String> headers) {
		FormBody.Builder builder = new FormBody.Builder();
		if (params != null && params.keySet().size() > 0) {
			params.forEach(builder::add);
		}

		Request.Builder builderRequest = new Request.Builder();
		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach(builderRequest::addHeader);
		}

		Request request = builderRequest.url(url).post(builder.build()).build();
		return getResponse(request);
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private Request buildPostRequestBody(String url, RequestBody requestBody, Map<String, String> headers) {
		Request.Builder builderRequest = new Request.Builder();
		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach(builderRequest::addHeader);
		}
		return builderRequest.url(url).post(requestBody).build();
	}

	private OkHttpResponse getResponse(Request request) {
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			OkHttpResponse okHttpResponse = new OkHttpResponse();
			okHttpResponse.setStatus(response.code());
			okHttpResponse.setResponse(response.body().string());
			return okHttpResponse;
		} catch (Exception e) {
			log.error("okhttp error = {}", ExceptionUtil.getStackTraceAsString(e));
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}


	//=====================================私有方法  end=====================================

}
