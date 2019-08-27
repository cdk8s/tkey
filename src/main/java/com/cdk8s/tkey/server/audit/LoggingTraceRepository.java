package com.cdk8s.tkey.server.audit;

import com.cdk8s.tkey.server.constant.GlobalVariable;
import com.cdk8s.tkey.server.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LoggingTraceRepository implements HttpTraceRepository {

	private final HttpTraceRepository httpTraceRepository = new InMemoryHttpTraceRepository();

	//=====================================业务处理 start=====================================

	@Override
	public List<HttpTrace> findAll() {
		return httpTraceRepository.findAll();
	}

	@Override
	public void add(HttpTrace trace) {
		if (checkUri(trace)) {
			return;
		}
		printTimeTakenResult(trace);
		this.httpTraceRepository.add(trace);
	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private Boolean checkUri(HttpTrace trace) {
		HttpTrace.Request request = trace.getRequest();
		String requestURI = request.getUri().toString();

		List<String> ignoreSuffix = new ArrayList<>();
		ignoreSuffix.add(".js");
		ignoreSuffix.add(".css");
		ignoreSuffix.add(".jpg");
		ignoreSuffix.add(".jpeg");
		ignoreSuffix.add(".gif");
		ignoreSuffix.add(".png");
		ignoreSuffix.add(".bmp");
		ignoreSuffix.add(".swf");
		ignoreSuffix.add(".ico");
		ignoreSuffix.add(".woff");
		ignoreSuffix.add(".woff2");
		ignoreSuffix.add(".ttf");
		ignoreSuffix.add(".eot");
		ignoreSuffix.add(".txt");
		ignoreSuffix.add(".svg");

		CharSequence[] charSequences = ignoreSuffix.toArray(new CharSequence[0]);
		return StringUtil.endsWithAny(requestURI, charSequences);
	}

	private void printTimeTakenResult(HttpTrace trace) {
		Long timeTaken = trace.getTimeTaken();

		HttpTrace.Request request = trace.getRequest();
		String requestMethod = request.getMethod();
		Map<String, List<String>> requestHeaders = request.getHeaders();
		List<String> cookieList = requestHeaders.get("cookie");

		List<String> refererList = requestHeaders.get("referer");
		URI requestUri = request.getUri();


		HttpTrace.Response response = trace.getResponse();
		Map<String, List<String>> responseHeaders = response.getHeaders();
		int responseStatus = response.getStatus();


		if (log.isDebugEnabled()) {
			log.debug("----------------------HttpTrace requestUri={}", requestUri);
			log.debug("----------------------HttpTrace requestMethod={}, responseStatus={}", requestMethod, responseStatus);
			log.debug("----------------------HttpTrace cookieList={}", cookieList);
			log.debug("----------------------HttpTrace refererList={}", refererList);
			log.debug("----------------------HttpTrace responseHeaders={}", responseHeaders.toString());
		}

		if (timeTaken > GlobalVariable.NEED_OPTIMIZE_TIME_THRESHOLD) {
			log.info("----------------------HttpTrace requestUri={}----------------------", requestUri);
		}
		if (timeTaken > GlobalVariable.SERIOUS_PERFORMANCE_PROBLEMS_TIME_THRESHOLD) {
			log.error("----------------------HttpTrace 严重注意：该方法可能存在严重性能问题={}----------------------", timeTaken);
		} else if (timeTaken > GlobalVariable.GENERAL_PERFORMANCE_PROBLEMS_TIME_THRESHOLD) {
			log.warn("----------------------HttpTrace 注意：该方法可能存在一般性能问题={}----------------------", timeTaken);
		} else if (timeTaken > GlobalVariable.NEED_OPTIMIZE_TIME_THRESHOLD) {
			log.info("----------------------HttpTrace 提示：检查该方法是否有优化的空间={}----------------------", timeTaken);
		}
	}

	//=====================================私有方法  end=====================================


}
