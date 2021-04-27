package com.nixsolutions.ppp.controller;

import com.nixsolutions.ppp.model.Role;
import com.nixsolutions.ppp.model.User;
import com.nixsolutions.ppp.service.RoleService;
import com.nixsolutions.ppp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8008")
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private static Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        List<User> list = userService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (user.getRoles() != null) {
            Role admin = roleService.findByName("admin");
            Set<Role> userRoles = user.getRoles();
            if (!userRoles.contains(admin)) {
                Set<User> usersAdmin = admin.getUsers();
                usersAdmin.remove(user);
                admin.setUsers(usersAdmin);
                roleService.update(admin);
            }
        } else {
            user.setRoles(userService.findById(id).getRoles());
        }
        user.setId(id);
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}