package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

	public static Result courses() {
        return ok(courses.render());
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
        return ok(account.render());
	}
	
	public static Result apply() {
        return ok(apply.render());
	}
	
}
