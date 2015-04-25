/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/



package controllers;

// Import data base classes:
import models.Course;
import models.Event;
import models.Student;
import models.StuCourse;
import models.StuEvent;

// Play Framework dependencies:
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

// Import the view templates:
import views.html.*;

// Import ORM dependencies:
import java.util.List;
import com.avaje.ebean.*;



public class Application extends Controller {


    public static Result index() {
        return ok(index.render());
    }

	public static Result accommodation() {
        return ok(accommodation.render());
	}
	
	public static Result studentinfo() {
        return ok(studentinfo.render());
	}
	
	public static Result visitorsguide() {
        return ok(visitorsguide.render());
	}
	
	public static Result contactus() {
        return ok(contactus.render());
	}
	
	public static Result account() {
	
		//retrieve event:
		List<Event> currentEvent = Event.findEvent();
		List<StuCourse> myCourses = StuCourse.findStuCourse(1L);
		
        return ok(account.render(myCourses, currentEvent));
	}
	
	public static Result apply() {
        return ok(apply.render());
	}

	public static Result loginscreen() {
        return ok(loginscreen.render());
	}

	public static Result paymentgpg() {
        return ok(paymentgpg.render());
	}
	
	public static Result courses(){
	
		//retrieve list of courses: 
		List<Course> genList = Course.findGeneral(); //general english
		List<Course> examList = Course.findExamPrep(); //exam preparation
		List<Course> acYearList = Course.findAcYear(); //academic year
		
		
		//render the view, pass all Course list as parameters:
		return ok(courses.render(genList,examList,acYearList));
	}
	
	
	
}
