/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/
package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.StudentCourseTransformer;
import util.StudentTransformer;
import views.html.account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static play.data.Form.form;

public class AccountController extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result myAccount() {
        String userName = session("studentUsername");
        Student student = Student.findByStudentUsername(userName);
        List<StuEvent> stuEvents = StuEvent.findByStuId(student.studentId);
        Set<Long> stuEventIds = new HashSet<Long>();
        if(stuEvents != null)
        {
            for(StuEvent stuEvent: stuEvents)
            {
                stuEventIds.add(stuEvent.eventID);
            }
        }
        List<Event> currentEvent = Event.findEvent();
        for(Event event : currentEvent)
        {
            if(stuEventIds.contains(event.eventId))
            {
                event.status = "BOOKED";
            }
        }
        Form<StudentCourseDto> form = form(StudentCourseDto.class);
        StudentCourseDto studentCourseForm = StudentTransformer.fillStudentToForm(student);
        form = form.fill(studentCourseForm);
        List<StuCourse> myCourses = StuCourse.findStuCourse(student.studentId);
        List<StuCourseDto> stuCourses = new ArrayList<StuCourseDto>(myCourses.size());
        StuCourseDto stuCourse = null;
        for (StuCourse course : myCourses) {
            stuCourse = StudentCourseTransformer.toStuCourseDto(course);
            stuCourse.setCourse(Course.findById(course.courseId));
            stuCourses.add(stuCourse);
        }
        return ok(account.render(stuCourses, currentEvent, form));
    }

    public static Result update() {
        Form<StudentCourseDto> studentCourseFormForm = Form.form(StudentCourseDto.class).bindFromRequest();
        if(studentCourseFormForm.hasErrors())
        {
            return badRequest();
        }
        Student student = StudentTransformer.toStudent(studentCourseFormForm.get());
        Ebean.update(student);
        return ok();
    }
}