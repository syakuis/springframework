package org.syaku.spring.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import org.syaku.snack.HttpSnack;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.StatusCode;
import org.syaku.spring.http.SuccessBody;
import org.syaku.spring.security.session.SessionInformationSupport;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 중복로그인을 체크하는 필터. 이미 로그인한 사용자가 있는 경우 json 으로 메세지를 전달하고 작업을 중단한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class DuplicationLoginCheckFilter extends GenericFilterBean {
	private final static Logger logger = LoggerFactory.getLogger(DuplicationLoginCheckFilter.class);
	private final SessionRegistry sessionRegistry;
	private final UserDetailsService userDetailsService;
	private final String loginProcessingUrl;
	private String ignoreParameterName = "duplicationCheckSuccess";

	public DuplicationLoginCheckFilter(SessionRegistry sessionRegistry, UserDetailsService userDetailsService, String loginProcessingUrl) {
		this.sessionRegistry = sessionRegistry;
		this.userDetailsService = userDetailsService;
		this.loginProcessingUrl = loginProcessingUrl;
	}

	/**
	 * 중복로그인 검사를 생략하기 위한 파라메터
	 * 값이 있는 경우 중복로그인 검사을 생략한다.
	 *
	 * @param ignoreParameterName the ignore parameter name
	 */
	public void setIgnoreParameterName(String ignoreParameterName) {
		this.ignoreParameterName = ignoreParameterName;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = HttpSnack.getHttpServletRequest(req);
		HttpServletResponse response = HttpSnack.getHttpServletResponse(res);

		// post 방식에 로그인 요청이 아닌 경우 작업종료
		if (!new AntPathRequestMatcher(loginProcessingUrl, "POST").matches(request)) {
			chain.doFilter(req, res);
			return;
		}

		// ajax 즉 비동기 요청이 아닌 경우 오류발생.
		if(!RequestSnack.isAjax(request)) {
			throw new AuthenticationServiceException("Authentication only ajax supported: " + request.getHeader("X-Requested-With"));
		}

		String ignore = request.getParameter(ignoreParameterName);

		// 중복로그인검사를 생략할 경우 작업종료
		if (StringUtils.isNotEmpty(ignore)) {
			chain.doFilter(req, res);
			return;
		}

		logger.debug("Duplicate login check filter has been requested.");

		boolean error = false;

		try {
			String username = request.getParameter("user_id");
			String password = request.getParameter("password");

			UserDetails details = userDetailsService.loadUserByUsername(username);

			// 사용자 정보 일치여부
			if (details.getPassword().equals(password)) {

				// 이미 로그인 사용자 여부
				SessionInformationSupport sessionInformationSupport = new SessionInformationSupport(sessionRegistry);
				if (sessionInformationSupport.userExists(username)) {
					logger.debug("There is already a user logged in.");
					error = true;
				}
			}
		} catch (UsernameNotFoundException e) {
			// 없는 경우 로그인 처리.
			error = false;
		}

		if (!error) {
			chain.doFilter(req, res);
			return;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		SuccessBody success = new SuccessBody();
		success.setError(true);
		success.setStatusCode(StatusCode.DuplicationLogin);
		success.setMessage("이미 로그인한 사용자가 있습니다. 로그인하시겠습니까?");


		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(success);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();

	}

}

