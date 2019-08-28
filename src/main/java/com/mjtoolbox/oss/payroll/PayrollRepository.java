package com.mjtoolbox.oss.payroll;

import com.mjtoolbox.oss.teacher.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PayrollRepository extends PagingAndSortingRepository<Payroll, Long> {
    List<Payroll> findByTeacher(Teacher teacher);
}
