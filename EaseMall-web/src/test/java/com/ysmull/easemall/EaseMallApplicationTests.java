package com.ysmull.easemall;

import com.ysmull.easemall.biz.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class EaseMallApplicationTests {

    @Autowired
    CartService cartService;

    @Autowired
    TestService testService;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 测试多个线程并发执行
     *
     * @param action    执行内容
     * @param threadNum 线程数
     */
    public void concurrentTest(Runnable action, int threadNum) throws InterruptedException {
        Thread[] threads = new Thread[threadNum];
        final CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(() -> {
                action.run();
                latch.countDown();
            });
        }
        Arrays.stream(threads).parallel().forEach(Thread::start);
        latch.await();
    }

    /**
     * 测试多个线程顺序执行
     *
     * @param action    执行内容
     * @param threadNum 线程数
     */
    public void serializableTest(Runnable action, int threadNum) throws InterruptedException {
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(action);
            threads[i].start();
            threads[i].join();
        }
    }

    @Test
    public void concurrentAddCart() throws InterruptedException {

        this.concurrentTest(() -> cartService.addCart(1, 101, 1), 100);
        assertEquals(1, cartService.getCart(1).size());
    }

    @Test
    public void keyHolderTest() throws InterruptedException {
        this.concurrentTest(() -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String insertSql = "INSERT INTO ease_test(name) VALUES(:threadName)";
            MapSqlParameterSource insertParams = new MapSqlParameterSource();
            insertParams.addValue("threadName", Thread.currentThread().toString());
            namedParameterJdbcTemplate.update(insertSql, insertParams, keyHolder);

            String selectSql = "select name from ease_test where id=:id";
            MapSqlParameterSource selectParams = new MapSqlParameterSource();
            selectParams.addValue("id", keyHolder.getKey().longValue());
            String actual = namedParameterJdbcTemplate.queryForObject(selectSql, selectParams, String.class);
            assertEquals(Thread.currentThread().toString(), actual);
        }, 50);
    }


    @Test
    public void testTransaction() throws InterruptedException {
        concurrentTest(testService::sqlTest, 2);
    }
}
