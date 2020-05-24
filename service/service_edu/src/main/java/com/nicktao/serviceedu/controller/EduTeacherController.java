package com.nicktao.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicktao.commonutils.R;
import com.nicktao.servicebase.exceptionhandler.NickTaoException;
import com.nicktao.serviceedu.entity.EduTeacher;
import com.nicktao.serviceedu.service.EduTeacherService;
import com.nicktao.serviceedu.vo.TeacherQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/serviceedu/eduteacher")
@Api(description = "讲师管理")
public class EduTeacherController {
//访问地址：http://localhost:8001/serviceedu/eduteacher/findAll
    @Autowired
    EduTeacherService teacherService;

    //1、查询讲师表所有数据
    @GetMapping("findAll")
    public R findAllTeacher(){
       try {

           int i = 10/0;
       }catch (Exception e){
            throw new NickTaoException(20001,"自定义异常");
       }
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }
    //2、逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R remove(@ApiParam(name = "id" ,value = "讲师id",required = true) @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 3、分页查询讲师
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation("分页查询讲师")
    // current:当前页码
    // limit:每页记录数
    public R pageQuery(@PathVariable @ApiParam(name = "current" ,value="当前页数", required = true)
                                   Long current ,@ApiParam(name = "limit",value = "每页记录数") @PathVariable Long limit){
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> record =  pageTeacher.getRecords();
        return R.ok().data("total",total).data("items",record);
    }

    // 4、按照条件分页查询
     @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation("按照条件分页查询讲师")
    public R pageTeacherCondition(@PathVariable long current ,@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery
     ){

        // 创建page对象
         Page<EduTeacher> pageTeacherCondition = new Page<>(current,limit);
         QueryWrapper queryWrapper = new QueryWrapper();
         // 多条件组合查询
         String name = teacherQuery.getName();
         Integer level = teacherQuery.getLevel();
         String begin = teacherQuery.getBegin();
         String end = teacherQuery.getEnd();
         if (!StringUtils.isEmpty(name))
         if (!StringUtils.isEmpty(level)){
             queryWrapper.eq("level",level);
         }
         if (!StringUtils.isEmpty(begin)){
             queryWrapper.ge("gmt_create",begin);
         }
         if (!StringUtils.isEmpty(end)){
             queryWrapper.le("gmt_create",end);
         }
         teacherService.page(pageTeacherCondition,queryWrapper);
         long total = pageTeacherCondition.getTotal();
         List<EduTeacher> records = pageTeacherCondition.getRecords();
         return R.ok().data("total",total).data("items",records);
     }
     // 5、添加讲师
     @ApiOperation("添加讲师")
     @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
         boolean save = teacherService.save(eduTeacher);
         if (save){
             return R.ok();
         }else {
             return R.error();
         }
     }
     //6、根据id查询讲师
    @ApiOperation("id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){

        EduTeacher byId = teacherService.getById(id);
        return R.ok().data("items",byId);
    }

    // 7、修改讲师
    @ApiOperation("修改讲师")
    @PostMapping("updataTeacher")
    public R updataTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.updateById(eduTeacher);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }

    }



}

