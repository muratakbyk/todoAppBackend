package com.example.todoapp.repo;

import com.example.todoapp.entities.Task;
import com.example.todoapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task>findAllByUser(User user);
}
