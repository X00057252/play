/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/



package models;

import java.util.Date;

public class StuCourseDto {

    public Long stuCourseId;
    public Long studentId;
    public Long courseId;
    public Course course;
    public String courseStatus;
    public Date courseStartDate;
    public Date courseEndDate;
    public String courseLevel;
    public int courseAttendance;
    public String courseAccommodation;
    public Boolean courseIsPaid;

    public Long getStuCourseId() {
        return stuCourseId;
    }

    public void setStuCourseId(Long stuCourseId) {
        this.stuCourseId = stuCourseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public int getCourseAttendance() {
        return courseAttendance;
    }

    public void setCourseAttendance(int courseAttendance) {
        this.courseAttendance = courseAttendance;
    }

    public String getCourseAccommodation() {
        return courseAccommodation;
    }

    public void setCourseAccommodation(String courseAccommodation) {
        this.courseAccommodation = courseAccommodation;
    }

    public Boolean getCourseIsPaid() {
        return courseIsPaid;
    }

    public void setCourseIsPaid(Boolean courseIsPaid) {
        this.courseIsPaid = courseIsPaid;
    }
}
