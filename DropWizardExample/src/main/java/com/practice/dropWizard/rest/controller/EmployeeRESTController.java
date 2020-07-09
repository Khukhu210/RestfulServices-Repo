package com.practice.dropWizard.rest.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.practice.dropWizard.rest.EmployeeService;
import com.practice.dropWizard.rest.representations.Employee;
import com.practice.dropWizard.rest.validation.EmployeeValidation;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeRESTController {
	

	 
    private final Validator validator;
 
    public EmployeeRESTController(Validator validator) {
        this.validator = validator;
    }
 
    @GET
    public Response getEmployees() {
        return Response.ok(EmployeeService.getEmployees()).build();
    }
 
    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeService.getEmployee(id);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }
 
    @POST
    public Response createEmployee(Employee employee) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Employee e = EmployeeService.getEmployee(employee.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Employee> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e == null) {
            EmployeeService.createEmployee(employee.getId(), employee);
        	return Response.ok(employee).build();
        } else
        	return Response.status(Status.BAD_REQUEST).entity("Employee Already Present").build();
    }
 
    @PUT
    @Path("/{id}")
    public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
    	//Set<String> violations = EmployeeValidation.validate(employee);
        Employee e = EmployeeService.getEmployee(employee.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Employee> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            employee.setId(id);
            EmployeeService.updateEmployee(id, employee);
            return Response.ok(employee).build();
        } else
        	return Response.status(Status.BAD_REQUEST).entity("Employee Not Present").build();
    }
 
    @DELETE
    @Path("/{id}")
    public Response removeEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeService.getEmployee(id);
        if (employee != null) {
            EmployeeService.removeEmployee(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

}
