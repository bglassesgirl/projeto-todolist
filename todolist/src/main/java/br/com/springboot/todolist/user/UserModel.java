package br.com.springboot.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/*
 * usando o @Data ele define todos os getters e setters
 * - > isso é possivel por causa da dependencia do maven, lombok
 * => tbm é possivel definir getters e setters individuais para cada atributo so colocando @getters @setters
 */
@Data
@Entity(name = "users")
public class UserModel {
    /*
     * o uuid é uma sequencia de 5 que transforma o id mais seguro
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    private String name;
    private String password;

    /*
     * vai definir que tudo que for colocado vai ter local, data e hora
     */
    @CreationTimestamp
    private LocalDateTime createdAT;

    // estrutura datetime
    // yyyy-mm-ddTHH:mm:ss


}
