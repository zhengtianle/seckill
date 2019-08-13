package cn.personal.seckill.exception;

import cn.personal.seckill.result.CodeMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-10
 * @Author ZhengTianle
 * Description:
 */
@AllArgsConstructor
@Getter
public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;
}
