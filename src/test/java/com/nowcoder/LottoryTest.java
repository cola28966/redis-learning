package com.nowcoder;

import com.nowcoder.service.LottoryService;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class LottoryTest {

    public static void main(String[] args) {
        LottoryService lottoryService = new LottoryService();

        //初始化奖池
        lottoryService.clearLottryPool();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            lottoryService.addLottoryPool(String.valueOf(random.nextInt(10000)));
        }

        //开始抽奖
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入本轮抽奖人数");
            int n = scanner.nextInt();
            if(n <= 0 ) break;
            Set<String> set = lottoryService.getLottryUsers(n);
            if(set == null || set.size() == 0) {
                System.out.println("本轮未抽取任何用户!");
                continue;
            }
            System.out.println("本轮获奖名单：");
            for (String user : set) {
                System.out.print(user + "\t");
            }
            System.out.println();
        }
    }
}
