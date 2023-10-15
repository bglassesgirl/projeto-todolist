package br.com.springboot.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.todolist.util.Utils;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Chegou no controller" );

        // forçando a validação doFilter
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID)idUser);

        // validando  as data
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStarAt()) || currentDate.isAfter(taskModel.getEndAt()) ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de inicio / data de fim deve ser maior que a data atual");
        }

        if (taskModel.getStarAt().isAfter(taskModel.getEndAt()) ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de inicio deve ser menor que a data de termino");
        }

        var task = this.taskRepository.save(taskModel);
         return ResponseEntity.status(HttpStatus.OK).body(task);

    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
         var idUser = request.getAttribute("idUser");
         var tasks =  this.taskRepository.findByIdUser((UUID)idUser);
         return tasks;

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        var task = this.taskRepository.findById(id).orElse(null);

        //se a tarefa não existir retorna um tarefa não encootrada
        if (task == null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Tarefa não encontrado. ");
        }

        var idUser = request.getAttribute("idUser");

        //se ela existir mas o id do user que está tentando mudar ela for diferente
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("O usuário não tem permissão para acessar essa tarefa. ");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);

    }

    // @PutMapping("/{id}")
    // public TaskModel uptade(@RequestBody TaskModel taskModel,HttpServletRequest request, @PathVariable UUID id){
    //     var idUser = request.getAttribute("idUser");
    //     taskModel.setIdUser(id);
    //     taskModel.setId(id);
    //     return this.taskRepositoy.save(taskModel);
    // }

}
