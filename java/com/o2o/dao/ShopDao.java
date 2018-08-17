package com.o2o.dao;

import com.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     *
     * @param shopCondition
     * @param rowIndex  page start from
     * @param pageSize numbers of returned shops
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);


    int queryShopCount(@Param("shopCondition") Shop shopCondition);
    /**
     * Insert new shop
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * Update the shop information
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    Shop queryByShopId(long shopId);
}
