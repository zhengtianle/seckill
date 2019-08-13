package cn.personal.seckill.service;

import cn.personal.seckill.entity.OrderInfo;
import cn.personal.seckill.entity.User;
import cn.personal.seckill.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-11
 * @Author ZhengTianle
 * Description:
 */
@Service
@RequiredArgsConstructor
public class SeckillService {
    private final GoodsService goodsService;
    private final OrderService orderService;

    @Transactional
    public OrderInfo miaosha(User user, GoodsVO goods) {
        // 减库存
        goodsService.reduceStock(goods);
        // 下订单，写入秒杀订单
        return orderService.createOrder(user, goods);
    }
}
