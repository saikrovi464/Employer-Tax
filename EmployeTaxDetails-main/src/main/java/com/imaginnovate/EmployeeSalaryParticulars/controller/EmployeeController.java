package com.imaginnovate.EmployeeSalaryParticulars.controller;

import com.imaginnovate.EmployeeSalaryParticulars.entity.Employee;
import com.imaginnovate.EmployeeSalaryParticulars.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @PostMapping("/save")
    ResponseEntity createEmployee(@RequestBody @Validated Employee emp){
        return new ResponseEntity(empService.saveEmployee(emp), HttpStatus.CREATED);
    }

    @GetMapping("/calculateTax/{id}")
    ResponseEntity calculateEmployeeTax(@PathVariable Integer id){
        return new ResponseEntity(empService.calculateTotalTax(id),HttpStatus.OK);
    }

}
