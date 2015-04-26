/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/



package controllers;

// Import data base classes:
import models.*;

// Play Framework dependencies:
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import play.data.*;
import static play.data.Form.*; 
import play.*;
import play.mvc.*;


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
	
	@Security.Authenticated(Secured.class)
	public static Result account() {
	
		//retrieve event:
		List<Event> currentEvent = Event.findEvent();
		List<StuCourse> myCourses = StuCourse.findStuCourse(1L);
		
        return ok(account.render(myCourses, currentEvent));
	}
	
	public static Result apply() {
        return ok(apply.render());
	}

	/*public static Result loginscreen() {
        return ok(loginscreen.render());
	} */

	public static Result paymentgpg() {
        return ok(paymentgpg.render());
	}
	

	// -- Authentication
    
    public static class Login {
        
        public String studentUsername;
        public String studentPassword;
        
        public String validate() {
            if(Student.authenticate(studentUsername, studentPassword) == null) {
                return "Invalid username or password";
            }
            return null;
        }
        
    }

    /**
     * Login page.
     */
    public static Result login() {
        return ok(
            login.render(form(Login.class))
        );
    }
	
	public static Result authenticate() {
    Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session().clear();
			session("studentUsername", loginForm.get().studentUsername);
			return redirect(
				routes.Application.account()
			);
		}
		
		}
	
	public static Result logout() {
    session().clear();
    flash("success", "You've been logged out");
    return redirect(routes.Application.index());
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

