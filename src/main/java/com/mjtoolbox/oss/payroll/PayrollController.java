package com.mjtoolbox.oss.payroll;

import com.mjtoolbox.oss.teacher.TeacherRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin()
@RestController
public class PayrollController {
    @Resource
    PayrollRepository payrollRepository;

    @Resource
    TeacherRepository teacherRepository;

    @GetMapping("/payrolls")
    public List<Payroll> retrieveAllPayrolls(){
        return StreamSupport.stream(payrollRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Retrieve payrolls for a teacher_id
    @GetMapping("/teachers/{teacher_id}/payrolls")
    public List<Payroll> retrieveAllPayrollsByTeacher(@PathVariable long teacher_id){
       return StreamSupport.stream(payrollRepository.findByTeacher(teacherRepository.findById(teacher_id).get()).spliterator(), false)
               .collect(Collectors.toList());
    }

    @GetMapping("/payrolls/{id}")
    public Payroll findById(@PathVariable long id){
        return payrollRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Payroll not found with ID: " + id));
    }

    // Create a payroll for a teacher_id
    @PostMapping("/teachers/{teacher_id}/payrolls")
    public Payroll createPayroll(@PathVariable long teacher_id, @Valid @RequestBody Payroll payroll){
        return teacherRepository.findById(teacher_id).map(teacher -> {
            payroll.setTeacher(teacher);
            // Otherwise, teacher_id will be empty in payroll
            payroll.setTeacher_id(teacher.getTeacher_id());
            return payrollRepository.save( payroll);
        }).orElseThrow(()-> new ResourceNotFoundException("Teacher ID " + teacher_id + " not found"));
    }

    // Update payroll by payroll_id
    @PutMapping("/payrolls/{id}")
    public Payroll updatePayroll(@PathVariable long id, @Valid @RequestBody Payroll payroll){
        Payroll payrollFromDB = payrollRepository.findById( id)
                .orElseThrow(()-> new ResourceNotFoundException("Payroll not found with ID: " + id));
        payrollFromDB.setHourly_rate(payroll.getHourly_rate());
        payrollFromDB.setPayment(payroll.getPayment());
        payrollFromDB.setPayment_date(payroll.getPayment_date());
        return payrollRepository.save(payrollFromDB);
    }

    @DeleteMapping("/payrolls/{id}")
    public void delete(@PathVariable long id){
        payrollRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with ID: " + id));
        payrollRepository.deleteById(id);
    }
}
