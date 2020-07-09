package com.practice.dropWizard.rest.validation;

import java.util.HashSet;
import java.util.Set;

import com.practice.dropWizard.rest.representations.Employee;

public class EmployeeValidation {

	
	public static Set<String> validate(Employee e){
		
		Set<String> s = new HashSet<String>();
		
		if(e.getId()== null){
			s.add("Empid cannot be null");
		}
		else if(e.getFirstName().length()>=255)
		{
			s.add("First name should not be more than 255 character ");
		}
		else if(!(e.getEmail().contains("@")) || !(e.getEmail().contains(".com"))){
			s.add("Invalid Email id");
		}
		return s;
		
		
		
	}
	
}
