package com.employeeapi.employeeproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employeeapi.employeeproject.entity.Employee;
import com.employeeapi.employeeproject.repo.EmployeeRepository;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	
	HashMap<Integer, Employee> cache = new HashMap<Integer, Employee>();
	
	@GetMapping("/msg")
	public String getMessage() {
		return "Hello World";
	}
	// CRUD --> EMPLOYEE
	// Cache --> retrieve --> First interaction cache
	// cache --> DB cache
	// create Employee
	
	// method type // POST --> Create
	
	// This is the create api for employee
	@PostMapping("/createEmp")
	public Employee createEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
		cache.put(employee.employeeId ,employee);
		// DB call
		return cache.get(employee.employeeId);
	}
	
	// This is the get api for employee
	@GetMapping("/getEmployee/{empId}")
	public Employee getEmployee(@PathVariable int empId) {
		// only check in cache
		// DB call // lightweight // performance
		return employeeRepository.findById(empId).get();
		//return cache.get(empId);
	}
	
	// This is the update api for employee
	@PutMapping("/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee employee) {
		Employee oldEmp = cache.get(employee.employeeId);
		if(oldEmp != null) {
			oldEmp.setEmployeeName(employee.getEmployeeName());
			oldEmp.setEmployeeAddress(employee.getEmployeeAddress());
			oldEmp.setEmployeeSalary(employee.getEmployeeSalary());
			cache.put(oldEmp.getEmployeeId(), oldEmp);
		}
				
		Employee tempEmp = employeeRepository.findById(employee.employeeId).get();
		tempEmp.setEmployeeName(employee.getEmployeeName());
		tempEmp.setEmployeeAddress(employee.getEmployeeAddress());
		tempEmp.setEmployeeSalary(employee.getEmployeeSalary());
		employeeRepository.save(tempEmp);		
		
		//return cache.get(employee.getEmployeeId());
		return employeeRepository.save(tempEmp);
	}
	
//	@DeleteMapping("/deleteEmployee/{empId}")
//	public Employee deleteEmployee(@PathVariable int empId) {
//		return cache.remove(empId);
//	}

	// This is the delete api for employee
	@DeleteMapping("/deleteEmployee/{empId}")
	public String deleteEmployee(@PathVariable int empId) {
		Optional<Employee> employee = employeeRepository.findById(empId);
		if(employee!=null){
			employeeRepository.deleteById(empId);
			return "Deleted Successfully...";
		}
		else {
			return "Employee not found";
		}
	}
	
	// This is the getAll api for employee
	@GetMapping("/getAllEmployee")
	public List<Employee> getAllEmployee(){
		List<Employee> employeeList = new ArrayList<>();
		employeeList = cache.values().stream().collect(Collectors.toList());
		
		//FiftyKEmpList>50000
		List<Employee> employeeList1 = employeeList.stream()
				.filter(employee -> employee.getEmployeeSalary() > 50000).collect(Collectors.toList());
		
		// in java 6 Same Code // enhanced for loop
//		List<Employee> empList = new ArrayList<>();
//		for(Employee employee: employeeList) { 
//			if(employee.getEmployeeSalary() > 50000) {
//				empList.add(employee); 
//			}
//		}
		
		return employeeList1;
	}


	
}
