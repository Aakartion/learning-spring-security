package edu.miu.cs489.securitydemo.secured;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    public String admin(){
        System.out.println("ADMIN here");
        return "Admin: secured end point";
    }
}
