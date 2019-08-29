package com.mjtoolbox.oss.payroll;

import com.mjtoolbox.oss.teacher.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * [IMPORTANT]
 * findByxxx method where xxx must be a property of this entity. In this case, Payroll which defines @ManyToOne relationship.
 */
public interface PayrollRepository extends PagingAndSortingRepository<Payroll, Long> {
    List<Payroll> findByTeacher(Teacher teacher);
}
