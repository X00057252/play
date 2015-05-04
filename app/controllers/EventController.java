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

    /**
     * bookevent functionality
     * @param studentId
     * @param eventId
     * @return
     */
    public static Result bookEvent(Long studentId, Long eventId) {
        //get event by eventid from db
        Event event = Event.findById(eventId);
        //check if event free spaces is greater than 0, if yes create new student event and decrease free space by 1 and update event
        //otherwise we throw badrequest with response event has no room
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

    /**
     * unbook event
     * @param studentId
     * @param eventId
     * @return
     */
    public static Result unBookEvent(Long studentId, Long eventId) {
        //get event by eventid from db
        Event event = Event.findById(eventId);
        //get student event by studnet id and eventid from db
        StuEvent stuEvent = StuEvent.findByStuIdEventId(studentId, eventId);
        //increment freespace by 1 and  and uodate event
        event.eventCurrCapacity += 1;
        Ebean.createSqlUpdate("update event set event_curr_capacity = event_curr_capacity + 1 where event_id = " + eventId).execute();
        //delete the student event
        Ebean.delete(StuEvent.class, stuEvent.stuEventId);

        return ok("{\"freeSpace\" : " +event.eventCurrCapacity+" }");
    }
}