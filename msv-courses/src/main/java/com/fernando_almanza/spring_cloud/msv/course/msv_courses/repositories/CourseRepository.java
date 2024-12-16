package com.fernando_almanza.spring_cloud.msv.course.msv_courses.repositories;

import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("DELETE FROM CourseUser cu WHERE cu.userId=?1")
    void deleteCourseUsersByUserId(Long userId);
}
