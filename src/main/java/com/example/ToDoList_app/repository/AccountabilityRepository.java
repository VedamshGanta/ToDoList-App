package com.example.ToDoList_app.repository;

import com.example.ToDoList_app.entity.Accountability;
import com.example.ToDoList_app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.List;

public interface AccountabilityRepository extends CrudRepository<Accountability, Long> {
    List<Accountability> findByUser(User user);
    List<Accountability> findByAccountableTo(User accountableTo);
    Optional<Accountability> findByUserAndAccountableTo(User user, User accountableTo);
}
