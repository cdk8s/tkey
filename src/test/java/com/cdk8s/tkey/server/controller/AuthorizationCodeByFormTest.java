package com.cdk8s.tkey.server.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 把 CLIENT_SECRET 和 CLIENT_SECRET 使用 Form 参数方式提交
 */
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthorizationCodeByFormTest {

	private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

	private static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(), MediaType.APPLICATION_JSON_UTF8.getSubtype(), UTF8_CHARSET);
	private static final MediaType TEXT_MEDIA_TYPE = new MediaType(MediaType.TEXT_HTML.getType(), MediaType.TEXT_HTML.getSubtype(), UTF8_CHARSET);
	private static final MediaType DEFAULT_FORM_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_FORM_URLENCODED.getType(), MediaType.APPLICATION_FORM_URLENCODED.getSubtype(), UTF8_CHARSET);

	private static final String ACCESS_TOKEN = "AT-102-uUCkO2NgITHWJSD16g89C9loMwCVSQqh";
	private static final String REFRESH_TOKEN = "RT-103-zIYUBA0ddql5cyYGEdpmPcRJH63hOVpQ";
	private static final String CODE = "OC-106-uUddPxoWCEa4NBO5GaVIRJOTZLlWbHNr";

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "123456";
	private static final String CODE_RESPONSE_TYPE = "code";
	private static final String CODE_GRANT_TYPE = "authorization_code";
	private static final String CLIENT_ID = "test_client_id_1";
	private static final String CLIENT_SECRET = "test_client_secret_1";
	private static final String REDIRECT_URI = "http://test1.cdk8s.com:9393/client-scribejava/codeCallback/aHR0cDovL3Rlc3QxLmNkazhzLmNvbTo5MzkzL2NsaWVudC1zY3JpYmVqYXZhL3VzZXI_aWQ9MTIzNDU2Jm5hbWU9Y2RrOHM";
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36";


	@Autowired
	private MockMvc mockMvc;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Test
	public void a_authorize() {
		RequestBuilder request = MockMvcRequestBuilders
			.get("/oauth/authorize")
			.param("response_type", CODE_RESPONSE_TYPE)
			.param("client_id", CLIENT_ID)
			.param("redirect_uri", REDIRECT_URI)
			.accept(TEXT_MEDIA_TYPE)
			.contentType(DEFAULT_FORM_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("login"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("oauthClient"));
	}

	@SneakyThrows
	@Test
	public void b_formLogin() {
		RequestBuilder request = MockMvcRequestBuilders
			.post("/oauth/authorize")
			.header("User-Agent", USER_AGENT)
			.param("response_type", CODE_RESPONSE_TYPE)
			.param("client_id", CLIENT_ID)
			.param("redirect_uri", REDIRECT_URI)
			.param("username", USERNAME)
			.param("password", PASSWORD)
			.accept(TEXT_MEDIA_TYPE)
			.contentType(DEFAULT_FORM_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://test1.cdk8s.com:9393/client-scribejava/**"));
	}

	@SneakyThrows
	@Test
	public void c_token() {
		RequestBuilder request = MockMvcRequestBuilders
			.post("/oauth/token")
			.param("grant_type", CODE_GRANT_TYPE)
			.param("client_id", CLIENT_ID)
			.param("client_secret", CLIENT_SECRET)
			.param("redirect_uri", REDIRECT_URI)
			.param("code", CODE)
			.accept(JSON_MEDIA_TYPE)
			.contentType(DEFAULT_FORM_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists());
	}

	@SneakyThrows
	@Test
	public void d_userinfo() {
		RequestBuilder request = MockMvcRequestBuilders
			.get("/oauth/userinfo")
			.param("access_token", ACCESS_TOKEN)
			.accept(JSON_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.user_attribute").exists());
	}

	@SneakyThrows
	@Test
	public void e_introspectByAccessToken() {
		RequestBuilder request = MockMvcRequestBuilders
			.post("/oauth/introspect")
			.param("client_id", CLIENT_ID)
			.param("client_secret", CLIENT_SECRET)
			.param("token_type_hint", "access_token")
			.param("token", ACCESS_TOKEN)
			.accept(JSON_MEDIA_TYPE)
			.contentType(DEFAULT_FORM_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.client_id").exists());
	}

	@SneakyThrows
	@Test
	public void f_introspectByRefreshToken() {
		RequestBuilder request = MockMvcRequestBuilders
			.post("/oauth/introspect")
			.param("client_id", CLIENT_ID)
			.param("client_secret", CLIENT_SECRET)
			.param("token_type_hint", "refresh_token")
			.param("token", REFRESH_TOKEN)
			.accept(JSON_MEDIA_TYPE)
			.contentType(DEFAULT_FORM_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.client_id").exists());
	}

	@SneakyThrows
	@Test
	public void g_logout() {
		RequestBuilder request = MockMvcRequestBuilders
			.get("/oauth/logout")
			.param("redirect_uri", "http://www.gitnavi.com")
			.accept(TEXT_MEDIA_TYPE);

		mockMvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://www.gitnavi.com**"));
	}


	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================
}
