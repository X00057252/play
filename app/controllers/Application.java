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
import org.apache.commons.lang3.StringUtils;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import play.data.*;
import static play.data.Form.*; 
import play.*;
import play.mvc.*;


// Import the view templates:
import util.StudentCourseTransformer;
import util.StudentTransformer;
import views.html.*;

// Import ORM dependencies:
import java.util.ArrayList;
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
	
	/*public static Result loginscreen() {
        return ok(loginscreen.render());
	} */

	public static Result paymentgpg() {
        String userName = session("studentUsername");
        Student student = Student.findByStudentUsername(userName);
        String courseId = form().bindFromRequest().get("courseId");
        Course course = Course.findById(Long.parseLong(courseId));
        StuCourse stuCourse = StuCourse.findByStuIdCourseIdStatus(student.studentId, Long.parseLong(courseId), "In Progress");
        return ok(paymentgpg.render(userName, course, stuCourse.courseAccommodation));
	}

	// -- Authentication
    
    public static class Login {
        
        public String studentUsername;
        public String studentPassword;

        public String getStudentUsername() {
            return studentUsername;
        }

        public void setStudentUsername(String studentUsername) {
            this.studentUsername = studentUsername;
        }

        public String getStudentPassword() {
            return studentPassword;
        }

        public void setStudentPassword(String studentPassword) {
            this.studentPassword = studentPassword;
        }

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
        String referer = flash().get("url");
        return ok(
            login.render(form(Login.class), referer)
        );
    }
	
	public static Result authenticate() {
        String referer = form().bindFromRequest().get("referer");
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm, referer));
		} else {
			session().clear();
			session("studentUsername", loginForm.get().studentUsername);
            if(StringUtils.isBlank(referer)) {
                return redirect(
                        routes.AccountController.myAccount()
                );
            }
            else
            {
                return redirect(referer);
            }
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
		return ok(courses.render(genList,examList,acYearList, session("studentUsername")));
	}
	
	
	
}

