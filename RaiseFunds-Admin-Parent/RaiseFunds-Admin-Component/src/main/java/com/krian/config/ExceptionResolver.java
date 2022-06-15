package com.krian.config;

import com.google.gson.Gson;
import com.krian.constant.RaiseFundsConstant;
import com.krian.exception.LoginFailedException;
import com.krian.util.RaiseFundsUtil;
import com.krian.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 基于注解的异常映射
 *
 * @ControllerAdvice： 当前类是一个基于注解的异常处理类
 */
@ControllerAdvice
public class ExceptionResolver {

    /**
     * 登录失败异常处理映射
     *
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 空指针异常处理映射
     *
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    // @ExceptionHandler：将一个具体的异常类型和一个异常处理方法关联：
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.判断当前请求方式类型：
        boolean result = RaiseFundsUtil.judgeRequestType(request);
        // 2.如果判断结果是AJAX请求：
        if (result) {
            // 3.创建ResultEntity对象：
            ResultEntity<Object> entity = ResultEntity.failed(exception.getMessage());
            // 4.创建Gson对象：
            Gson gson = new Gson();
            // 5.将ResultEntity转换为JSON字符串：
            String json = gson.toJson(entity);
            // 6.将JSON返回响应给浏览器：
            response.getWriter().write(json);
            // 7.返回 null，不给 SpringMVC 提供 ModelAndView 对象：
            return null;
        } else {
            // 8.如果不是AJAX请求则返回ModelAndView对象：
            ModelAndView modelAndView = new ModelAndView();
            // 9.将Exception对象存入模型中：
            modelAndView.addObject("exception", exception);
            // 10.设置对应的视图名称：
            modelAndView.setViewName("system-error");
            // 11.返回视图：
            return modelAndView;
        }
    }

    /**
     * 核心异常处理方法
     *
     * @param exception SpringMVC 捕获到的异常对象
     * @param request   为了判断当前请求是“普通请求”还是“Ajax 请求” 需要传入原生 request 对象
     * @param response  为了能够将 JSON 字符串作为当前请求的响应数
     *                  据返回给浏览器
     * @param viewName  指定要前往的视图名称
     * @return ModelAndView
     * @throws IOException
     */

    private ModelAndView commonResolve(
            // 异常处理完成后要去的页面
            String viewName,
            // 实际捕获到的异常类型
            Exception exception,
            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {
        // 1.判断当前请求类型
        boolean judgeResult = RaiseFundsUtil.judgeRequestType(request);

        // 2.如果是Ajax请求
        if (judgeResult) {

            // 3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

            // 4.创建Gson对象
            Gson gson = new Gson();

            // 5.将ResultEntity对象转换为JSON字符串
            String json = gson.toJson(resultEntity);

            // 6.将JSON字符串作为响应体返回给浏览器
            response.getWriter().write(json);

            // 7.由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        // 8.如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        // 9.将Exception对象存入模型
        modelAndView.addObject(RaiseFundsConstant.ATTR_NAME_EXCEPTION, exception);

        // 10.设置对应的视图名称
        modelAndView.setViewName(viewName);

        // 11.返回modelAndView对象
        return modelAndView;
    }
}
