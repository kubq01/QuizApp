package com.example.QuizApp.data.Class;

import com.example.QuizApp.data.users.Student;
import com.example.QuizApp.data.users.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    private final ClassRepository repo;
    private final ClassToStudentRepository toStudentRepo;

    @Autowired
    public ClassService(ClassRepository repo, ClassToStudentRepository toStudentRepo)
    {
        this.repo = repo;
        this.toStudentRepo = toStudentRepo;
    }

    public List<Class> getClassesByTeacher(Teacher teacher){
        return repo.findAllByTeacher(teacher);
    }

    public List<ClassToStudentRelation> getClassesRelByClass(Class myClass){
        return toStudentRepo.findAllByMyClass(myClass);
    }

    public List<ClassToStudentRelation> getClassesRelByStudent(Student student){
        return toStudentRepo.findAllByStudent(student);
    }

    public List<Student> getStudentsByClass(Long classID)
    {
        List<ClassToStudentRelation> list = getClassesRelByClass(getClassByID(classID));

        List<Student> students = new ArrayList<>();
        for(ClassToStudentRelation rel: list)
        {
            students.add(rel.getStudent());
        }

        return students;
    }

    public void insertRel(ClassToStudentRelation relation)
    {
        toStudentRepo.save(relation);
    }

    public Class getClassByID(Long ID)
    {
        Optional<Class> myClass = repo.findById(ID);
        if(myClass.isPresent())
            return myClass.get();
        else
            return null;
    }

    public void insert(Class studentClass)
    {
        repo.save(studentClass);
    }

    public void insertClassRel(ClassToStudentRelation relation){
        toStudentRepo.save(relation);
    }

    public void deleteClass(Class entity)
    {
        toStudentRepo.deleteAllByMyClass(entity);
        repo.delete(entity);
    }


    public List<Class> showAll()
    {
        return repo.findAll();
    }

}
