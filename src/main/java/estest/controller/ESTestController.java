package estest.controller;
import estest.service.ESTestSelectService;
import estest.service.ESTestService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * ES测试controller
 */
@RestController
public class ESTestController {
    //插入和更新
    @Resource
    private ESTestService esTestServiceImpl;
    //查询
    @Resource
    private ESTestSelectService esTestSelectServiceImpl;
    //初始化
    //插入
    @RequestMapping("/initInsertData")
    @ResponseBody
    public void initInsertData(){
        esTestServiceImpl.initInsertData();

    }
    //更新
    @RequestMapping("/updateData")
    @ResponseBody
    public void updateData(){
        esTestServiceImpl.updateData();

    }
    //--------------------------------//
    //查询
    @RequestMapping("/select_string_Data")
    @ResponseBody
    public void select_string_Data(){
        esTestSelectServiceImpl.select_string_Data();

    }
    //查询
    @RequestMapping("/select_term_Data")
    @ResponseBody
    public void select_term_Data(){
        esTestSelectServiceImpl.select_term_Data();

    }
    @RequestMapping("/select_range_Data")
    @ResponseBody
    public void select_range_Data(){
        esTestSelectServiceImpl.select_range_Data();

    }
    @RequestMapping("/select_wildcard_Data")
    @ResponseBody
    public void select_wildcard_Data(){
        esTestSelectServiceImpl.select_wildcard_Data();

    }
    @RequestMapping("/select_match_Data")
    @ResponseBody
    public void select_match_Data(){
        esTestSelectServiceImpl.select_match_Data();

    }
    @RequestMapping("/select_bool_Data")
    @ResponseBody
    public void select_bool_Data(){
        esTestSelectServiceImpl.select_bool_Data();

    }
    @RequestMapping("/select_max_Data")
    @ResponseBody
    public void select_max_Data(){
        esTestSelectServiceImpl.select_max_Data();

    }
    @RequestMapping("/select_cardinality_Data")
    @ResponseBody
    public void select_cardinality_Data(){
        esTestSelectServiceImpl.select_cardinality_Data();

    }
    @RequestMapping("/select_aggregations_Data")
    @ResponseBody
    public void select_aggregations_Data(){
        esTestSelectServiceImpl.select_aggregations_Data();

    }
    @RequestMapping("/select_aggregations_two_Data")
    @ResponseBody
    public void select_aggregations_two_Data(){
        esTestSelectServiceImpl.select_aggregations_two_Data();

    }



}
