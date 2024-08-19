package com.imaginnovate.EmployeeSalaryParticulars.service;

import com.imaginnovate.EmployeeSalaryParticulars.dto.EmployeeTaxDetails;
import com.imaginnovate.EmployeeSalaryParticulars.entity.Employee;
import com.imaginnovate.EmployeeSalaryParticulars.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String saveEmployee(Employee emp) {
        if (employeeRepository.save(emp) != null) {
            return "Employee data saved successfully";
        } else {
            return "Employee data is not saved";
        }
    }

    public EmployeeTaxDetails calculateTotalTax(Integer id) {
        Employee employee = null;
        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent()) {
            employee = emp.get();
        }

        Integer annualSalary = calculateAnnualSalary(employee.getDateOfJoin(), employee.getSalary());

        EmployeeTaxDetails employeeTaxDetails = EmployeeTaxDetails.builder()
                .employeeCode(employee.getEmployeeID())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .annualSalary(annualSalary)
                .tax(calculateTax(annualSalary))
                .cessTax(calculateCessTax(annualSalary))
                .build();

        return employeeTaxDetails;
    }

    private Integer calculateAnnualSalary(String dateOfJoin, Integer monthlySalary) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date joiningDate = null;
        try {
            joiningDate = sdf.parse(dateOfJoin);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int months = (int) (currentDate.getTime() - joiningDate.getTime()) / (10 * 60 * 60 * 24 * 30);
        return months * monthlySalary;
    }

    private Integer calculateTax(Integer annualSalary) {
        int tax = 0;
        if (annualSalary <= 250000) {
            tax = 0;
        } else if (annualSalary <= 500000) {
            tax = ((annualSalary - 250000) * 5) / 100;
        } else if (annualSalary <= 1000000) {
            tax = (250000 * 5) / 100 + ((annualSalary - 500000) * 10) / 100;
        } else {
            tax = (250000 * 5) / 100 + ((annualSalary - 500000) * 10) / 100 + ((annualSalary - 1000000) * 20) / 100;
        }
        return tax;
    }

    private Integer calculateCessTax(Integer annualSalary) {
        int cessTax = 0;
        if (annualSalary >= 2800000) {
            cessTax = ((annualSalary - 2800000) * 2) / 100;
        }
        return cessTax;
    }
}
