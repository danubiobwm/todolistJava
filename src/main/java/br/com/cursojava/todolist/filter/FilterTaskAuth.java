package br.com.cursojava.todolist.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cursojava.todolist.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{


  @Autowired
  private IUserRepository userRepository;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
        var authorization = request.getHeader("authorization");

        var authEncoded = authorization.substring("Basic".length()).trim();

       byte[] authDecode = Base64.getDecoder().decode(authEncoded);

       var authString = new String(authDecode);
        System.out.println("Authorization");
        System.out.println(authString);

        String[] credentials =  authString.split(":");
        String userName = credentials[0];
        String password = credentials[1];

        var user = this.userRepository.findByUserName(userName);

        if(user == null){
          response.sendError(401, "User not Authorized");
        } else {
         var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

         if(passwordVerify.verified){
            filterChain.doFilter(request, response);
          }else{
            response.sendError(401, "User not Authorized");
          }
        }
  }

}
