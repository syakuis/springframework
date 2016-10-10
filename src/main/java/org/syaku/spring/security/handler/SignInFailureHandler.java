package org.syaku.spring.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.SuccessBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 로그인 실패할 경우 호출되는 헨들러
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 *
 */
public class SignInFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	public SignInFailureHandler() {}

	public SignInFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		if(RequestSnack.isAjax(request)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");

			SuccessBody success = new SuccessBody();

			success.setError(true);
			success.setMessage(exception.getMessage());

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(success);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}