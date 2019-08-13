package cn.personal.seckill.controller;

import cn.personal.seckill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-6
 * @Author ZhengTianle
 * Description:
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;


}
