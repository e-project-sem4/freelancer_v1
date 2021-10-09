package com.freelancer.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.freelancer.exception.CustomException;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	private static final String AUTHORIZATION = "Authorization";

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
//    String token = jwtTokenProvider.resolveToken(httpServletRequest);
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
		httpServletResponse.addHeader("Access-Control-Expose-Headers", "xsrf-token");
		if ("OPTIONS".equals(httpServletRequest.getMethod())) {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		} else {
			String token = httpServletRequest.getHeader(AUTHORIZATION);
			try {
				if (token != null && jwtTokenProvider.validateToken(token)) {
					Authentication auth = jwtTokenProvider.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (CustomException ex) {
				SecurityContextHolder.clearContext();
				httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
				return;
			}
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}

	}

}
