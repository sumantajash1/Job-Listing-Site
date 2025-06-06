package com.Sumanta.JobListing.controller;

import com.Sumanta.JobListing.DTO.CompanyLoginRequestBody;
import com.Sumanta.JobListing.Entity.Company;
import com.Sumanta.JobListing.Service.CompanyService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/Company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/SignUp")
    public ResponseEntity<String> SignUp(@RequestBody Company company, HttpServletResponse response) {
        System.out.println(company);
        //System.out.println("In the sign Up method of CompanyController");
        String serviceResponse = companyService.register(company);
        if(serviceResponse.equals("InvalGstNum")) {
            return ResponseEntity.ok("Invalid GST Number");
        }
        if(serviceResponse.equals("gstExists")) {
            return ResponseEntity.ok("gst number already exists");
        }
        if(serviceResponse.equals("contactNumberExists")) {
            return ResponseEntity.ok("contact number already exists");
        }
        if(serviceResponse.equals("emailExists")) {
            return ResponseEntity.ok("email already exists");
        }
        if(serviceResponse.equals("nameExists")) {
            return ResponseEntity.ok("name already exists");
        }
        String jwtToken = serviceResponse;
        response.setHeader("jwt", jwtToken);
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/SignIn")
    public ResponseEntity<String> SignIn(@RequestBody CompanyLoginRequestBody companyLoginRequestBody, HttpServletResponse response) {
        //System.out.println("Entered SignIn method of Company");
        String serviceResponse = companyService.Login(companyLoginRequestBody);
        if(serviceResponse.equals("InvalGstNum")) {
            return ResponseEntity.ok("Invalid GST Number");
        }
        if(serviceResponse.equals("NotFound")) {
            return ResponseEntity.ok("Company is not found, please register");
        }
        if(serviceResponse.equals("WrongPassword")) {
            return ResponseEntity.ok("Password is Wrong");
        }
        String jwtToken = serviceResponse;
        response.setHeader("jwt", jwtToken);
        return ResponseEntity.ok(jwtToken);
    }

}
