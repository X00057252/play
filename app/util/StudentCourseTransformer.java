/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

   =========================================================== 
*/



package util;

import models.StuCourse;
import models.StuCourseDto;

public class StudentCourseTransformer {

    public static StuCourseDto toStuCourseDto(StuCourse stuCourse)
    {
        StuCourseDto dto = new StuCourseDto();
        dto.setCourseAccommodation(stuCourse.courseAccommodation);
        dto.setCourseAttendance(stuCourse.courseAttendance);
        dto.setCourseEndDate(stuCourse.courseEndDate);
        dto.setCourseIsPaid(stuCourse.courseIsPaid);
        dto.setCourseLevel(stuCourse.courseLevel);
        dto.setCourseStartDate(stuCourse.courseStartDate);
        dto.setCourseStatus(stuCourse.courseStatus);
        dto.setStudentId(stuCourse.studentId);
        dto.setStuCourseId(stuCourse.stuCourseId);
        dto.setCourseId(stuCourse.courseId);

        return dto;
    }
}
