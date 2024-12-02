package ar.edu.unq.apc.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import ar.edu.unq.apc.model.HttpException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomUsersDetailsService customUsersDetailsService;
	
	@Autowired 
	private JWTProvider jwtProvider;

	@Autowired
    @Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization"); 
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		try {
			String token = getTokenFromRequest(request);
			if (StringUtils.hasText(token) && this.jwtProvider.validateToken(token)) {
				String userEmail = this.jwtProvider.getUserNameFromJWT(token);
				UserDetails userDetails = this.customUsersDetailsService.loadUserByUsername(userEmail);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (HttpException e) {
			log.error("Spring Security Filter Chain Exception:", e);
			resolver.resolveException(request, response, null, e);
		}
        filterChain.doFilter(request, response);
	}

}
