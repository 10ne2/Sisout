package com.camp_us.mapper;

import org.apache.ibatis.annotations.Param;

import com.camp_us.dto.HomeworkSubmitVO;


public interface HomeworkSubmitMapper {
    void insertHomeworkSubmit(HomeworkSubmitVO submitVO);
    void deleteHomeworkSubmit(String hwNo);
    void updateSubmit(HomeworkSubmitVO submitVO);
    
    HomeworkSubmitVO selectSubmitByStuIdAndHwNo(
            @Param("stuId") String stuId,
            @Param("hwNo") int hwNo
        );
}