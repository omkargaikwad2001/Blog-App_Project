package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	    
	@Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
      //Authorization

      String requestToken = request.getHeader("Authorization");
      //Bearer 2352345235sdfrsfgsdfsdf
      System.out.println(requestToken);
      
      String username = null;
      String token = null;
      
      if (requestToken != null && requestToken.startsWith("Bearer")) {
          //looking good
          token = requestToken.substring(7);
          try {

              username = this.jwtTokenHelper.getUsernameFromToken(token);

          } catch (IllegalArgumentException e) {
//              logger.info("Illegal Argument while fetching the username !!");
//              e.printStackTrace();
        	  System.out.println("Illegal Argument while fetching the username !!");
          } catch (ExpiredJwtException e) {
//              logger.info("Given jwt token is expired !!");
//              e.printStackTrace();
              System.out.println("Given jwt token is expired !!");
          } catch (MalformedJwtException e) {
//              logger.info("Some changed has done in token !! Invalid Token");
//              e.printStackTrace();
              System.out.println("Some changed has done in token !! Invalid Token");
          } catch (Exception e) {
              e.printStackTrace();

          }


      } else {
          System.out.println("Jwt token does not begin with Bearer");
      }


      //
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


          //fetch user detail from username
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
          Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);
          if (validateToken) {

              //set the authentication
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authentication);


          } else {
//              logger.info("Validation fails !!");
        	  System.out.println("Validation fails !!");
          }


      }
      else {
    	  System.out.println("User is null or context is not null");
	}

      filterChain.doFilter(request, response);


  }
		
		
}
