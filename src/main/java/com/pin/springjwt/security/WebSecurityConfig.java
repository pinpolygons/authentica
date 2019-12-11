package com.pin.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pin.springjwt.security.jwt.AuthEntryPointJwt;
import com.pin.springjwt.security.jwt.AuthTokenFilter;
import com.pin.springjwt.security.services.UserDetailsServiceImp;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
//		securedEnabled = true,
//		jsr250Enabled = true, 
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserDetailsServiceImp userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		// TODO Auto-generated method stub
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
/*Giải thích code 
 * -@EnableWebSecurity cho phép Spring để tìm và tự động thiết lập class đến Web Security toàn cục 
 * -@EnableGlobleMethodSecurity cung cấp bảo mật AOP trong các phương thức. Nó kích hoạt @PreAuthorize,
 * nó cũng hổ trợ JSR-250. Bạn có thể tìm nhiều các tham số hơn trong cấu hình trong Method Security Expressions
 * - Chúng ta ghi đè phương thức configure(HttpSecurity http) từ interface WebSecurityConfigurerAdapter
 *Nó nói Spring Security bằng cách nào chúng ta cấu hình CORS và CSRF, khi chúng ta muốn yêu cầu tất cả
 *các user được chứng thực hay không, bộ lọc này (AuthTokenFileter) và khi chúng ta muốn nó tới làm việc
 *(bộ lọc trước khi UsernamePassworkAuthenticationFilter), mà được chọn Exception Handler (AuthEntryPointJwt)
 *-Spring Security sẽ tải các chi tiết User để thực hiện xác thực và cấp phép. Vì thế nó có interface
 *UserDetailsService  mà chúng ta cần thực hiện 
 *-Việc thực hiện của UserDetailsService sẽ được sử dụng cho cấu hình DaoAuthenticationProvider bởi
 * phương thức AuthenticationManagerBuilder.userDetailsService()      
 * -Chúng ta cũng cần một PasswordEncoder cho DaoAuthenticationProvider. Nếu chúng ta không chỉ định nó
 * sẽ sữ dụng văn bản đơn giản
 * */
 