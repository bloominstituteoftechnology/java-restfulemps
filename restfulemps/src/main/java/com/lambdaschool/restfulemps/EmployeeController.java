package com.lambdaschool.restfulemps;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class EmployeeController
{
    private final EmployeeRepository emprepos;
    private final EmployeeResourceAssembler assembler;

    public EmployeeController(EmployeeRepository emprepos, EmployeeResourceAssembler assembler)
    {
        this.emprepos = emprepos;
        this.assembler = assembler;
    }

    // Post -> Create
    // Get -> Read
    // Put -> Update / Replace
    // Patch -> Update / Modify (collections)
    // Delete -> Delete

    // RequestMapping(method = RequestMethod.GET)
    @GetMapping("/employees")
    public Resources<Resource<Employee>> all()
    {
        List<Resource<Employee>> employees = emprepos.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @GetMapping("/employees/{id}") // /employees/4
    public Resource<Employee> findOne(@PathVariable Long id)
    {
        Employee foundEmp = emprepos.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toResource(foundEmp);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmp, @PathVariable Long id)
        throws URISyntaxException
    {
        Employee updatedEmployee = emprepos.findById(id)
                .map(employee ->
                {
                    employee.setFname(newEmp.getFname());
                    employee.setLname(newEmp.getLname());
                    employee.setSalary(newEmp.getSalary());
                    employee.setHas401k(newEmp.isHas401k());
                    return emprepos.save(employee);
                })
                .orElseGet(() ->
                {
                    newEmp.setId(id);
                    return emprepos.save(newEmp);
                });

        Resource<Employee> resource = assembler.toResource(updatedEmployee);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id)
    {
        emprepos.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
