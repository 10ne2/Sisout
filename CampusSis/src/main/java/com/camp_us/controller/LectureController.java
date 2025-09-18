package com.camp_us.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camp_us.dto.StuLecVO;
import com.camp_us.service.StuLecService;
@Controller
public class LectureController {
    private StuLecService stuLecService;
@Autowired
    public LectureController(StuLecService stuLecService) {
        this.stuLecService = stuLecService;
    }
}

