package cn.personal.seckill.service;

import cn.personal.seckill.dao.GoodsDao;
import cn.personal.seckill.entity.MiaoshaGoods;
import cn.personal.seckill.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-10
 * @Author ZhengTianle
 * Description:
 */
@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsDao goodsDao;

    public List<GoodsVO> listGoodsVO() {
        return goodsDao.listGoodsVO();
    }

    public GoodsVO getGoodsVOByGoodsId(long goodsId) {
        return goodsDao.getGoodsVOByGoodsId(goodsId);
    }

    /**
     * 减少库存
     */
    public void reduceStock(GoodsVO goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        goodsDao.reduceStock(g);
    }
}
