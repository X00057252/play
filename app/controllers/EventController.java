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
import java.util.List;

import static play.data.Form.form;

public class EventController extends Controller {

    public static Result bookEvent(Long studentId, Long eventId) {
        Event event = Event.findById(eventId);
        if(event.eventCurrCapacity > 0)
        {
            StuEvent stuEvent = new StuEvent();
            stuEvent.studentID = studentId;
            stuEvent.eventID = eventId;

            event.eventCurrCapacity = event.eventCurrCapacity - 1;
            Ebean.createSqlUpdate("update event set event_curr_capacity = event_curr_capacity - 1 where event_id = " + eventId).execute();
            Ebean.save(stuEvent);
        }
        else
        {
            return badRequest("Event has no room");
        }
        return ok("{\"freeSpace\" : " +event.eventCurrCapacity+" }");
    }

    public static Result unBookEvent(Long studentId, Long eventId) {
        Event event = Event.findById(eventId);
        StuEvent stuEvent = StuEvent.findByStuIdEventId(studentId, eventId);
        event.eventCurrCapacity += 1;
        Ebean.createSqlUpdate("update event set event_curr_capacity = event_curr_capacity + 1 where event_id = " + eventId).execute();
        Ebean.delete(StuEvent.class, stuEvent.stuEventId);

        return ok("{\"freeSpace\" : " +event.eventCurrCapacity+" }");
    }
}