package com.mjtoolbox.oss.timesheet;

import com.mjtoolbox.oss.payroll.Payroll;
import com.mjtoolbox.oss.teacher.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * [IMPORTANT]
 * findByxxx method where xxx must be a property of this entity (Timesheet).
 */
public interface TimesheetRepository extends PagingAndSortingRepository<Timesheet, Long> {
    List<Timesheet> findByPayroll(Payroll payroll);

    List<Timesheet> findByTeacher(Teacher teacher);
}
