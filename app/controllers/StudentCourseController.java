/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/

package controllers;

import com.avaje.ebean.Ebean;
import models.Course;
import models.StuCourse;
import models.Student;
import models.StudentCourseDto;
import org.apache.commons.lang3.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.StudentTransformer;
import views.html.apply;
import views.html.paymentgpg;

import java.util.Calendar;
import java.util.Date;

import static play.data.Form.form;

public class StudentCourseController extends Controller {

    public static Result saveStudentCourse() {
        Form<StudentCourseDto> studentCourseFormForm = Form.form(StudentCourseDto.class).bindFromRequest();
        if (studentCourseFormForm.hasErrors()) {
            return badRequest();
        }
        Student student = StudentTransformer.toStudent(studentCourseFormForm.get());
        String userName = session("studentUsername");
        if (StringUtils.isBlank(userName)) {
            Student existingStudent = Student.findByStudentUsername(student.studentUsername);
            if(existingStudent != null)
            {
                badRequest("Username already exists! Please login if you are an existing student");
            }
            student.studentId = null;
            Ebean.save(student);
            session().clear();
            session("studentUsername", student.studentUsername);
        } else {
            Ebean.update(student);
        }
        Course course = Course.findById(studentCourseFormForm.get().getCourseId());
        StuCourse stuCourse = new StuCourse();
        stuCourse.courseAccommodation = studentCourseFormForm.get().getAccomodationType();
        stuCourse.courseStartDate = studentCourseFormForm.get().getCourseStartDate();
        stuCourse.courseEndDate = addWeeks(course.courseLength, stuCourse.courseStartDate);
        stuCourse.studentId = student.studentId;
        stuCourse.courseStatus = "Saved for later";
        stuCourse.courseId = studentCourseFormForm.get().getCourseId();
        stuCourse.courseIsPaid = false;
        StuCourse existingCourse = StuCourse.findByStuIdCourseIdStatus(stuCourse.studentId, stuCourse.courseId, "Saved for later");
        if(existingCourse == null)
        {
            existingCourse = StuCourse.findByStuIdCourseIdStatus(stuCourse.studentId, stuCourse.courseId, "In Progress");
        }
        if(existingCourse != null && !existingCourse.courseStatus.equals("Completed"))
        {
            stuCourse.stuCourseId = existingCourse.stuCourseId;
            Ebean.update(stuCourse);
        }
        else
        {
            Ebean.save(stuCourse);
        }

        return ok();
    }

    public static Result applyStudentCourse() {
        Form<StudentCourseDto> studentCourseFormForm = Form.form(StudentCourseDto.class).bindFromRequest();
        if (studentCourseFormForm.hasErrors()) {
            return badRequest();
        }
        Student student = StudentTransformer.toStudent(studentCourseFormForm.get());
        String userName = session("studentUsername");
        if (StringUtils.isBlank(userName)) {
            Student existingStudent = Student.findByStudentUsername(student.studentUsername);
            if(existingStudent != null)
            {
                badRequest("Username already exists! Please login if you are an existing student");
            }
            student.studentId = null;
            Ebean.save(student);
            session().clear();
            session("studentUsername", student.studentUsername);
        } else {
            Ebean.update(student);
        }
        Course course = Course.findById(studentCourseFormForm.get().getCourseId());
        StuCourse stuCourse = new StuCourse();
        stuCourse.courseAccommodation = studentCourseFormForm.get().getAccomodationType();
        stuCourse.courseStartDate = studentCourseFormForm.get().getCourseStartDate();
        stuCourse.courseEndDate = addWeeks(course.courseLength, stuCourse.courseStartDate);
        stuCourse.studentId = student.studentId;
        stuCourse.courseStatus = "In Progress";
        stuCourse.courseId = studentCourseFormForm.get().getCourseId();
        stuCourse.courseIsPaid = true;
        StuCourse existingCourse = StuCourse.findByStuIdCourseIdStatus(stuCourse.studentId, stuCourse.courseId, "Saved for later");
        if(existingCourse == null)
        {
            existingCourse = StuCourse.findByStuIdCourseIdStatus(stuCourse.studentId, stuCourse.courseId, "In Progress");
        }
        if(existingCourse != null && !existingCourse.courseStatus.equals("Completed"))
        {
            stuCourse.stuCourseId = existingCourse.stuCourseId;
            Ebean.update(stuCourse);
        }
        else
        {
            Ebean.save(stuCourse);
        }

        return ok(paymentgpg.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result apply(Long courseId) {
        Course course = Course.findById(courseId);
        String userName = session("studentUsername");
        Form<StudentCourseDto> form = form(StudentCourseDto.class);
        if (StringUtils.isNotBlank(userName)) {
            Student student = Student.findByStudentUsername(userName);
            StudentCourseDto studentCourseForm = StudentTransformer.fillStudentToForm(student);
            studentCourseForm.setCourseId(courseId);
            form = form.fill(studentCourseForm);
        }
        return ok(apply.render(form, Course.findGeneral(), Course.findExamPrep(), Course.findAcYear()));
    }

    public static Result register() {
        String userName = session("studentUsername");
        Form<StudentCourseDto> form = form(StudentCourseDto.class);
        if (StringUtils.isNotBlank(userName)) {
            Student student = Student.findByStudentUsername(userName);
            StudentCourseDto studentCourseForm = StudentTransformer.fillStudentToForm(student);
            form = form.fill(studentCourseForm);
        }
        return ok(apply.render(form, Course.findGeneral(), Course.findExamPrep(), Course.findAcYear()));
    }

    private static Date addWeeks(int weeks, Date date)
    {
        long time = date.getTime();
        long week = 7 * 24 * 60 * 60 * 1000L;
        return new Date((time + (week * weeks)));
    }
}