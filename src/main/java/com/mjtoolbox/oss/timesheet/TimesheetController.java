package com.mjtoolbox.oss.timesheet;

import com.mjtoolbox.oss.payroll.Payroll;
import com.mjtoolbox.oss.payroll.PayrollRepository;
import com.mjtoolbox.oss.teacher.Teacher;
import com.mjtoolbox.oss.teacher.TeacherRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin()
@RestController
public class TimesheetController {

    @Resource
    PayrollRepository payrollRepository;

    @Resource
    TeacherRepository teacherRepository;

    @Resource
    TimesheetRepository timesheetRepository;


    @GetMapping("/timesheets")
    public List<Timesheet> retrieveAllPayrolls() {
        return StreamSupport.stream(timesheetRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Retrieve timesheets for a payroll
    @GetMapping("/payrolls/{payroll_id}/timesheets")
    public List<Timesheet> retrieveAllTimesheetsByPayroll(@PathVariable long payroll_id) {
        return StreamSupport.stream(timesheetRepository.findByPayroll(payrollRepository.findById(payroll_id).get()).spliterator(), false)
                .collect(Collectors.toList());
    }

    // Retrieve all timesheets for a teacher
    @GetMapping("/teachers/{teacher_id}/timesheets")
    public List<Timesheet> retrieveAllTimesheetsByTeacher(@PathVariable long teacher_id) {
        return StreamSupport.stream(timesheetRepository.findByTeacher(teacherRepository.findById(teacher_id).get()).spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/timesheets/{timesheet_id}")
    public Timesheet findById(@PathVariable long timesheet_id) {
        return timesheetRepository.findById(timesheet_id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found with ID: " + timesheet_id));
    }


    // Create Timesheet for a teacher. Payroll must exist.
    @PostMapping("/teachers/{teacher_id}/payrolls/{payroll_id}/timesheets")
    public Timesheet createTimesheet(@PathVariable long teacher_id, @PathVariable long payroll_id, @Valid @RequestBody Timesheet timesheet) {

        // Validate if teacher_id exist
        Teacher teacher = teacherRepository.findById(teacher_id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacher_id));

        // Validate if payroll_id exist
        Payroll payroll = payrollRepository.findById(payroll_id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with ID: " + payroll_id));

        // Then create a timesheet by setting teacher, payroll properties
        timesheet.setTeacher(teacher);
        timesheet.setTeacher_id(teacher.getTeacher_id());
        timesheet.setPayroll(payroll);
        timesheet.setPayroll_id(payroll.getPayroll_id());

        return timesheetRepository.save(timesheet);
    }

    // Update Timesheet
    @PutMapping("/timesheets/{timesheet_id}")
    public Timesheet updateTimesheet(@PathVariable long timesheet_id, @Valid @RequestBody Timesheet timesheet) {
        Timesheet timesheetFromDB = timesheetRepository.findById(timesheet_id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found with ID: " + timesheet_id));
        timesheetFromDB.setHours_worked(timesheet.getHours_worked());
        timesheetFromDB.setSubmitted_date(new Date());
        return timesheetRepository.save(timesheetFromDB);
    }


    @DeleteMapping("/timesheets/{timesheet_id}")
    public void delete(@PathVariable long timesheet_id) {
        teacherRepository.findById(timesheet_id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found with ID: " + timesheet_id));
        teacherRepository.deleteById(timesheet_id);
    }


}
