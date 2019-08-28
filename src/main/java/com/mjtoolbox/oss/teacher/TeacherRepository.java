package com.mjtoolbox.oss.teacher;

import com.mjtoolbox.oss.payroll.Payroll;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
}
