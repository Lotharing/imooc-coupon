package com.imooc.coupon.advice;

import com.imooc.coupon.constans.SystemEnum;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author : LuTong.Zhao
 * @date : 19:40 2020/7/23
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * @ExceptionHandler对指定的异常进行拦截,异常处理器 对CouponException进行统一处理
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handlerCouponException(HttpServletRequest request, CouponException ex){
        CommonResponse<String> response = new CommonResponse<>(SystemEnum.SYS_ERROR.getCode(),"business error");
        response.setData(ex.getMessage());
        return response;
    }

}
