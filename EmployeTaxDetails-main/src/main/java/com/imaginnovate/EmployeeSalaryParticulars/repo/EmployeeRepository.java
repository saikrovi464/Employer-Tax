package com.imaginnovate.EmployeeSalaryParticulars.repo;

import com.imaginnovate.EmployeeSalaryParticulars.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
