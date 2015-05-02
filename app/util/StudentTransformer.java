/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/



package util;

import models.Student;
import models.StudentCourseDto;

public class StudentTransformer {

    public static Student toStudent(StudentCourseDto form) {
        Student student = new Student();
        student.studentCity = form.getCity();
        student.studentCountry = form.getCountry();
        student.studentDOB = form.getDateOfBirth();
        student.studentEmail = form.getEmail();
        student.studentFName = form.getFirstName();
        student.studentGender = form.getGender();
        student.studentLName = form.getLastName();
        student.studentNationality = form.getNationality();
        student.studentPassword = form.getPassword();
        student.studentPhone = form.getPhone();
        student.studentProvince = form.getProvince();
        student.studentStreet = form.getStreet();
        student.studentUsername = form.getUsername();
        student.studentId = form.getStudentId();

        return student;
    }

    public static StudentCourseDto fillStudentToForm(Student student) {
        StudentCourseDto form = new StudentCourseDto();
        form.setCity(student.studentCity);
        form.setCountry(student.studentCountry);
        form.setDateOfBirth(student.studentDOB);
        form.setEmail(student.studentEmail);
        form.setFirstName(student.studentFName);
        form.setGender(student.studentGender);
        form.setLastName(student.studentLName);
        form.setNationality(student.studentNationality);
        form.setPassword(student.studentPassword);
        form.setConfirmPassword(student.studentPassword);
        form.setPhone(student.studentPhone);
        form.setProvince(student.studentProvince);
        form.setStreet(student.studentStreet);
        form.setUsername(student.studentUsername);
        form.setStudentId(student.studentId);
        return form;
    }
}
