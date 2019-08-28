package com.mjtoolbox.oss.teacher;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin()
@RestController
public class TeacherController {
    @Resource
    TeacherRepository teacherRepository;

    @GetMapping("/teachers")
    public List<Teacher> retrieveAllTeachers(){
        return StreamSupport.stream(teacherRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/teachers/{id}")
    public Teacher findById(@PathVariable long id){
        return teacherRepository.findById( id)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with ID: " + id));
    }

    @PostMapping("/teachers")
    public Teacher createTeacher(@Valid @RequestBody Teacher teacher){
        return teacherRepository.save( teacher);
    }

    @PutMapping("/teachers/{id}")
    public Teacher updateTeacher( @PathVariable long id, @Valid @RequestBody Teacher teacher){
        Teacher teacherFromDB = teacherRepository.findById( id)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with ID: " + id));
        teacherFromDB.setCell_phone(teacher.getCell_phone());
        teacherFromDB.setEmail(teacher.getEmail());
        teacherFromDB.setHome_phone(teacher.getHome_phone());
        teacherFromDB.setAddress(teacher.getAddress());
        teacherFromDB.setCity(teacher.getCity());
        teacherFromDB.setProvince(teacher.getProvince());
        teacherFromDB.setPostal_code(teacher.getPostal_code());
        teacherFromDB.setSubjects(teacher.getSubjects());
        teacherFromDB.setLevel(teacher.getLevel());
        teacherFromDB.setSubjects(teacher.getSubjects());
        return teacherRepository.save(teacherFromDB);
    }

    @DeleteMapping("/teachers/{id}")
    public void delete(@PathVariable long id){
        teacherRepository.findById( id)
                .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with ID: " + id));
        teacherRepository.deleteById( id);
    }
}
