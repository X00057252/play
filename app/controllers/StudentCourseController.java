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

    /**
     * saveForLater functionality
     * @return
     */
    public static Result saveStudentCourse() {
        //Get the form fields into the form object
        Form<StudentCourseDto> studentCourseFormForm = Form.form(StudentCourseDto.class).bindFromRequest();
        if (studentCourseFormForm.hasErrors()) {
            return badRequest();
        }
        //Prepare the student object from the form object
        Student student = StudentTransformer.toStudent(studentCourseFormForm.get());
        String userName = session("studentUsername");
        //check if username is in session if not create new student, otherwise update student
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
        //create student course object from the form object
        StuCourse stuCourse = new StuCourse();
        stuCourse.courseAccommodation = studentCourseFormForm.get().getAccomodationType();
        stuCourse.courseStartDate = studentCourseFormForm.get().getCourseStartDate();
        stuCourse.courseEndDate = addWeeks(course.courseLength, stuCourse.courseStartDate);
        stuCourse.studentId = student.studentId;
        stuCourse.courseStatus = "Saved for later";
        stuCourse.courseId = studentCourseFormForm.get().getCourseId();
        stuCourse.courseIsPaid = false;
        /*
         * Check if this course is already associated with student in 'Save for later' or 'In progress' status
         * if not then create new student course otherwise update it..
         */
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

    /**
     * Apply course functionality
     * @return
     */
    public static Result applyStudentCourse() {
        //Get the form fields into the form object
        Form<StudentCourseDto> studentCourseFormForm = Form.form(StudentCourseDto.class).bindFromRequest();
        if (studentCourseFormForm.hasErrors()) {
            return badRequest();
        }
        Student student = StudentTransformer.toStudent(studentCourseFormForm.get());
        String userName = session("studentUsername");
        //chceck if username is in session if not create new student, otherwise update student
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
        //create student course object from the form object
        Course course = Course.findById(studentCourseFormForm.get().getCourseId());
        StuCourse stuCourse = new StuCourse();
        stuCourse.courseAccommodation = studentCourseFormForm.get().getAccomodationType();
        stuCourse.courseStartDate = studentCourseFormForm.get().getCourseStartDate();
        stuCourse.courseEndDate = addWeeks(course.courseLength, stuCourse.courseStartDate);
        stuCourse.studentId = student.studentId;
        stuCourse.courseStatus = "In Progress";
        stuCourse.courseId = studentCourseFormForm.get().getCourseId();
        stuCourse.courseIsPaid = true;
        /*
         * Check if this course is already associated with student in 'Save for later' or 'In progress' status
         * if not then create new student course otherwise update it..
         */
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

        return ok(paymentgpg.render(userName, course, stuCourse.courseAccommodation));
    }

    /**
     * delete course application functionality
     * @param courseId
     * @return
     */
    public static Result deleteApplication(Long courseId) {
        //get logged in student from the db
        Student student = Student.findByStudentUsername(session("studentUsername"));
        //get the student course by courseid student id and status
        StuCourse stuCourse = StuCourse.findByStuIdCourseIdStatus(student.studentId, courseId, "Saved for later");
        //delete the student course
        Ebean.delete(StuCourse.class, stuCourse.stuCourseId);
        return redirect(
                routes.AccountController.myAccount()
        );
    }

    /**
     * apply course form functionality
     * @param courseId
     * @return
     */
    @Security.Authenticated(Secured.class)
    public static Result apply(Long courseId) {
        //Find the course by id from db
        Course course = Course.findById(courseId);
        String userName = session("studentUsername");
        Form<StudentCourseDto> form = form(StudentCourseDto.class);
        //check if user is logged in, if yes then fill form with student details to be rendered in UI
        if (StringUtils.isNotBlank(userName)) {
            Student student = Student.findByStudentUsername(userName);
            StudentCourseDto studentCourseForm = StudentTransformer.fillStudentToForm(student);
            studentCourseForm.setCourseId(courseId);
            form = form.fill(studentCourseForm);
        }

        //render apply registration form
        return ok(apply.render(form, Course.findGeneral(), Course.findExamPrep(), Course.findAcYear()));
    }

    /**
     * student registration form functionality
     * @return
     */
    public static Result register() {
        //referer url for getting the courseid, this contains the url from which we got to this registration page
        String referer = form().bindFromRequest().get("referer");
        Long courseId = null;
        if(StringUtils.isNotBlank(referer) && referer.contains("/"))
        {
            courseId = Long.parseLong(referer.substring(referer.lastIndexOf("/") + 1));
        }
        String userName = session("studentUsername");
        Form<StudentCourseDto> form = form(StudentCourseDto.class);
        StudentCourseDto studentCourseForm = new StudentCourseDto();
        //check user uis logged in, if yes then fill form with student details to be rendered in UI
        if (StringUtils.isNotBlank(userName)) {
            Student student = Student.findByStudentUsername(userName);
            studentCourseForm = StudentTransformer.fillStudentToForm(student);
        }
        studentCourseForm.setCourseId(courseId);
        form = form.fill(studentCourseForm);
        //render apply registration form
        return ok(apply.render(form, Course.findGeneral(), Course.findExamPrep(), Course.findAcYear()));
    }

    private static Date addWeeks(int weeks, Date date)
    {
        long time = date.getTime();
        long week = 7 * 24 * 60 * 60 * 1000L;
        return new Date((time + (week * weeks)));
    }
}