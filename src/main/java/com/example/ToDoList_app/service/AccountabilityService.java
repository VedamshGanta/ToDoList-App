package com.example.ToDoList_app.service;

import com.example.ToDoList_app.entity.Accountability;
import com.example.ToDoList_app.entity.User;
import com.example.ToDoList_app.repository.AccountabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountabilityService {

    @Autowired
    private AccountabilityRepository accountabilityRepository;

    public List<Accountability> getAccountableUsers(User user) {
        return accountabilityRepository.findByAccountableTo(user);
    }

    public void addAccountability(User user, User accountableTo) {
        Optional<Accountability> existingAccountability = accountabilityRepository.findByUserAndAccountableTo(user, accountableTo);
        if (existingAccountability.isEmpty()) {
            Accountability accountability = new Accountability(user, accountableTo);
            accountabilityRepository.save(accountability);
        } else {
            throw new IllegalArgumentException("Accountability relationship already exists.");
        }
    }

    public void removeAccountability(User user, User accountableTo) {
        List<Accountability> accountabilities = accountabilityRepository.findByAccountableTo(accountableTo);
        accountabilities.stream()
                .filter(a -> a.getUser().equals(user))
                .findFirst()
                .ifPresent(accountabilityRepository::delete);
    }
}
