package com.yjs.cloud.learning.learn.biz.lesson.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjs.cloud.learning.learn.biz.lesson.bean.LessonGetRequest;
import com.yjs.cloud.learning.learn.biz.lesson.bean.LessonListRequest;
import com.yjs.cloud.learning.learn.biz.lesson.bean.LessonResponse;
import com.yjs.cloud.learning.learn.biz.lesson.entity.Lesson;
import com.yjs.cloud.learning.learn.common.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author bill.lai
 * @since 2020-08-29
 */
public interface LessonMapper extends IBaseMapper<Lesson> {

    @Select({"<script>" ,
            "select distinct l.id, l.name, l.code, l.start_time, l.end_time, l.image, l.status, l.phrase, l.introduction, l.homework, l.homework_attachment, l.target_type, l.create_time, l.update_time " ,
            "from t_lesson l " ,
            "join t_lesson_category_relation lcr on lcr.lesson_id = l.id " ,
            "where 1 = 1 " ,
            "   <if test='req.memberId != null'>",
            "       and if(l.target_type = 'all', 1 = 1, " ,
            "           exists( " ,
            "               select ltm.lesson_id from t_lesson_target_member ltm where ltm.member_id = #{req.memberId} and ltm.lesson_id = l.id " ,
            "               <if test='req.departmentId != null'>",
            "               union " ,
            "               select ltd.lesson_id from t_lesson_target_department ltd where ltd.department_id = #{req.departmentId} and ltd.department_id = l.id " ,
            "               </if>" ,
            "           ) " ,
            "       )"  ,
            "   </if>" ,
            "   <if test='req.keyword != null and req.keyword != \"\"'>",
            "       and (" ,
            "           l.name like concat('%', #{req.keyword}, '%') " ,
            "       ) " ,
            "   </if>",
            "   <if test='req.status != null'>",
            "       and (" ,
            "           l.status = #{req.status} " ,
            "       ) " ,
            "   </if>",
            "   <if test='req.cid != null'>" ,
            "      and ( " ,
            "          lcr.category_id in (" ,
            "              select child_category_id from t_category_relation where father_category_id = #{req.cid}" ,
            "              union all " ,
            "              select id from t_category where id = #{req.cid} " ,
            "          ) " ,
            "      ) " ,
            "   </if>" ,
            "</script>"})
    Page<LessonResponse> list(Page<LessonResponse> page, @Param("req") LessonListRequest lessonListRequest);

    @Select({"<script>" ,
            "select distinct l.id, l.name, l.code, l.start_time, l.end_time, l.image, l.status, l.phrase, l.introduction, l.homework, l.homework_attachment, l.target_type, l.create_time, l.update_time " ,
            "from t_lesson l " ,
            "where l.id = #{req.id} " ,
            "   <if test='req.memberId != null'>",
            "       and if(l.target_type = 'all', 1 = 1, " ,
            "           exists( " ,
            "               select ltm.lesson_id from t_lesson_target_member ltm where ltm.member_id = #{req.memberId} and ltm.lesson_id = l.id " ,
            "               <if test='req.departmentId != null'>",
            "               union " ,
            "               select ltd.lesson_id from t_lesson_target_department ltd where ltd.department_id = #{req.departmentId} and ltd.department_id = l.id " ,
            "               </if>" ,
            "           ) " ,
            "       )"  ,
            "   </if>" ,
            "   <if test='req.status != null'>",
            "       and (" ,
            "           l.status = #{req.status} " ,
            "       ) " ,
            "   </if>",
            "</script>"})
    Lesson getById(@Param("req") LessonGetRequest lessonGetRequest);
}
