package com.example.demo.service;

import com.example.demo.model.enums.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesService {

    public List<String> getAllRoles() {
        ArrayList<String> roles = new ArrayList<String>();
        for (Role rol : Role.values()) {
            roles.add(rol.toString());
        }
        return roles;
    }
}
