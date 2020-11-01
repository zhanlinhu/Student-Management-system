package com.zqy.utils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.HandlerChain;
import java.util.Map;

@ControllerAdvice  //声明全局异常处理类
public class GlobalControllerAdvice {

    private final String ERROR = "error";

    @ExceptionHandler(value=PermissionException.class)
    public ModelAndView noPermission(PermissionException e){
        ModelAndView modelAndView = new ModelAndView(ERROR);
        modelAndView.addObject(ERROR,e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value=RuntimeException.class)
    @ResponseBody
    public Map<String,Object> runtimeException(RuntimeException e){
        e.printStackTrace();
        return MapControll.getInstance().error().getMap();
    }


}
