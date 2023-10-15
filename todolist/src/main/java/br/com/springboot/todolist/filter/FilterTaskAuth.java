package br.com.springboot.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.springboot.todolist.user.IUserRepository;
import br.com.springboot.todolist.user.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;
    private UserModel user;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                // aqui so cadastra uma tarefa caso o usuario e senha sejam validas

                var servletPath = request.getServletPath();

                if (servletPath.startsWith("/tasks/")) {
                        //pegar a autenticação (usuario e senha)

                        // a autoriza;áo vai ver codificada
                        var authorization = request.getHeader("Authorization");

                        //o user a senha vai ser separada do basic
                        var authEncoded = authorization.substring("Basic".length()).trim();

                        //juntar em um array e descodificar (user:senha)
                        byte[] authDecode = Base64.getDecoder().decode(authEncoded);
                        var authString = new String(authDecode);

                        //dividir o array em dois
                        // ["glassesgirl", "12345"]
                        //e atribuir uma variavel a cada subarray
                        String[] credentials = authString.split(":");
                        String username = credentials[0];
                        String password  = credentials[1];

                        //validar se o user existe
                        var user = this.userRepository.findByUsername(username);
                        if (user == null) {
                            response.sendError(401);
                        }else{
                            //se o user existe vai validar senha
                            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), this.user.getPassword() );
                            if (passwordVerify.verified) {
                                //segue viagem
                                // obriga a pegar o id sem precisar colocaar no request
                                request.setAttribute("idUser", this.user.getId());
                                filterChain.doFilter(request, response);
                            }else{
                                response.sendError(401);
                            }
                        }
                } else {
                     filterChain.doFilter(request, response);
                }




    }


}
