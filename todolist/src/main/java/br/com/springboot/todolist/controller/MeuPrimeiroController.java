package br.com.springboot.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller   //estrutura de retornar pagina e template
@RestController  //construir api e retornar um ret ( UMA ROTA)
@RequestMapping("/primeiraRota")
public class MeuPrimeiroController {

    /*
      Métodos de acesso ao http
     * GET - buscar uma informação
     * POST - adicionar uma informação
     * PUT - alterar um dado
     * DELETE - remover um dado
     * PACTH -alterar somente uma parte da info
     */
     
     //método (funcionalidade) de uma classe
    @GetMapping("/")
    public String primeiraMensagem(){
        return "Funcionou!!";
    }
    
}
