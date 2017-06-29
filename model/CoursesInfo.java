package vn.edu.topica.model;

/**
 * Created by ZtOg on 4/17/2017.
 */

public class CoursesInfo {
    String coursesName;
    String theoryTeacher;
    String praticeTeacher;

    public CoursesInfo() {
    }

    public CoursesInfo(String coursesName, String theoryTeacher, String praticeTeacher) {
        this.coursesName = coursesName;
        this.theoryTeacher = theoryTeacher;
        this.praticeTeacher = praticeTeacher;
    }

    public String getCoursesName() {
        return coursesName;
    }

    public void setCoursesName(String coursesName) {
        this.coursesName = coursesName;
    }

    public String getTheoryTeacher() {
        return theoryTeacher;
    }

    public void setTheoryTeacher(String theoryTeacher) {
        this.theoryTeacher = theoryTeacher;
    }

    public String getPraticeTeacher() {
        return praticeTeacher;
    }

    public void setPraticeTeacher(String praticeTeacher) {
        this.praticeTeacher = praticeTeacher;
    }

    @Override
    public String toString() {
        return (coursesName + "\n" + theoryTeacher + "\n" + praticeTeacher);
    }
}
