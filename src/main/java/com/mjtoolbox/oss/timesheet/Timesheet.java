package com.mjtoolbox.oss.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjtoolbox.oss.payroll.Payroll;
import com.mjtoolbox.oss.teacher.Teacher;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Timesheet will be submitted weekly (Starting Sunday)
 */
@Entity
@Data
@Table(name = "timesheet", schema = "public")
public class Timesheet implements Serializable {

    @Id
    @Column(name = "timesheet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long timesheet_id;

    // @JsonIgnore will not fetch Payroll object in response
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payroll_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Payroll payroll;

    @Column(name = "payroll_id", insertable = false, updatable = false)
    private long payroll_id;

    // @JsonIgnore will not fetch Teacher object in response
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Teacher teacher;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private long teacher_id;

    @Column(name = "ts_start")
    private Date ts_start;

    @Column(name = "ts_end")
    private Date ts_end;

    @Column(name = "hours_worked")
    private long hours_worked;

    @Column(name = "submitted_date")
    private Date submitted_date;

    @CreationTimestamp
    @Column(name = "last_update")
    @Setter(AccessLevel.NONE)
    private Date last_update;


}
