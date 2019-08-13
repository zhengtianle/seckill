package cn.personal.seckill.service;

import cn.personal.seckill.dao.OrderDao;
import cn.personal.seckill.entity.MiaoshaOrder;
import cn.personal.seckill.entity.OrderInfo;
import cn.personal.seckill.entity.User;
import cn.personal.seckill.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-11
 * @Author ZhengTianle
 * Description:
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    /**
     * 创建订单
     */
    @Transactional
    public OrderInfo createOrder(User user, GoodsVO goods) {
        // 插入 order
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setUserId(user.getId());
        orderInfo.setCreateDate(new Date());
        // 假地址
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        // 假渠道
        orderInfo.setOrderChannel(1);
        // 未付款
        orderInfo.setOrderStatus(1);
        long orderId = orderDao.insert(orderInfo);

        // 插入 miaosha_order
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        return orderInfo;
    }
}
