package cn.personal.seckill.exception;

import cn.personal.seckill.result.CodeMsg;
import cn.personal.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-10
 * @Author ZhengTianle
 * Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 拦截所有异常
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandle(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            CodeMsg codeMsg = exception.getCodeMsg();
            return Result.error(codeMsg);
        }
        if(e instanceof BindException) {//是绑定异常的情况
            //强转
            BindException exception = (BindException) e;
            //获取错误信息
            List<ObjectError> errors = exception.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {//不是绑定异常的情况
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
