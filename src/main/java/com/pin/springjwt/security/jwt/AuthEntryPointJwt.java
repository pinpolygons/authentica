package com.pin.springjwt.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*Bây giờ chúng ta khởi tạo lớp AuthEntryPointtJwt để thực hiện giao thức AuthenticationEntryPoint. 
 * Sau đó chúng ta ghi đè phươg thức commence(). Phương thức này sẽ được triggerd bất kỳ lúc nào không 
 * chứng thực được người dùng yêu cầu một bảo mật tài nguyên HTTP và một AuthenticationException được
 * ném ra */

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.error("Unauthorized error: {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}
/*HttpServletResponse.SC_UNAUTHORIZED là mã lỗi trạng thái 401. Nó chỉ ra rằng yêu cầu đòi hỏi 
 * xác thực HTTP.*/
