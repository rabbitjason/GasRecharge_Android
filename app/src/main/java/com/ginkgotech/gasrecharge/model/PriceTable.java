package com.ginkgotech.gasrecharge.model;

import com.ginkgotech.gasrecharge.NetworkServer;

/**
 * Created by Administrator on 2016/11/6.
 */

public class PriceTable {

    private static volatile PriceTable instance;
    private TieredPricingItem [] priceTable = new TieredPricingItem[50];
    public void initTable() {
        for(int i=0; i<priceTable.length; i++) {
            priceTable[i] = new TieredPricingItem();
        }

        priceTable[0].userType = "0026";
        priceTable[0].sort = "2";
        priceTable[0].onePrice = 2.53;
        priceTable[0].oneLimit = 360;
        priceTable[0].twoPrice = 2.78;
        priceTable[0].twoLimit = 600;
        priceTable[0].threePrice = 3.54;
        priceTable[0].threeLimit = 99999999;

        priceTable[1].userType = "0027";
        priceTable[1].sort = "2";
        priceTable[1].onePrice = 3.493;
        priceTable[1].oneLimit = 99999999;
    }

    public static PriceTable getInstance() {
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (instance == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (NetworkServer.class) {
                //未初始化，则初始instance变量
                if (instance == null) {
                    instance = new PriceTable();
                }
            }
        }
        return instance;
    }

    public double getPrice(String userType, long ladderGas, long buyGasCount) {
        double payPrice = 0.0;
        long total = ladderGas + buyGasCount;
        for (int i = 0; i < priceTable.length; i++) {
            if (priceTable[i].sort.equals("2") && priceTable[i].userType.equals(userType)) {
                if (total < priceTable[i].oneLimit) {
                    payPrice = buyGasCount * priceTable[i].onePrice;
                    break;
                } else if (total < priceTable[i].twoLimit) {
                    if (priceTable[i].oneLimit > ladderGas) {
                        payPrice = (priceTable[i].oneLimit - ladderGas) * priceTable[i].onePrice;
                        payPrice += (total - priceTable[i].oneLimit) * priceTable[i].twoPrice;
                    } else {
                        payPrice = buyGasCount * priceTable[i].twoPrice;
                    }
                    break;
                } else if (total < priceTable[i].threeLimit) {
                    if (ladderGas < priceTable[i].oneLimit) {
                        payPrice = (priceTable[i].oneLimit - ladderGas) * priceTable[i].onePrice;
                        payPrice += (priceTable[i].twoLimit - priceTable[i].oneLimit) * priceTable[i].twoPrice;
                        payPrice += (total - priceTable[i].twoLimit) * priceTable[i].threePrice;
                    } else if (ladderGas < priceTable[i].twoLimit) {
                        payPrice = (priceTable[i].twoLimit - ladderGas) * priceTable[i].twoPrice;
                        payPrice += (total - priceTable[i].twoLimit) * priceTable[i].threePrice;
                    } else {
                        payPrice = buyGasCount * priceTable[i].threePrice;
                    }
                    break;
                }
            }
        }

        return payPrice;
    }

    public class TieredPricingItem {
        public String userType;
        public String sort;
        double onePrice;
        long oneLimit;
        double twoPrice;
        long twoLimit;
        double threePrice;
        long threeLimit;

        public TieredPricingItem() {

        }
    }
}
