package com.ysmull.easemall

import com.ysmull.easemall.biz.CartService
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Arrays
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList

@RunWith(SpringRunner::class)
@SpringBootTest
@EnableTransactionManagement
class EaseMallApplicationTests(
        internal var cartService: CartService,
        internal var testService: TestService,
        internal var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

) {
    
    /**
     * 测试多个线程并发执行
     *
     * @param action    执行内容
     * @param threadNum 线程数
     */
    @Throws(InterruptedException::class)
    fun concurrentTest(threadNum: Int, action: Runnable) {
        val threads = arrayOfNulls<Thread>(threadNum)
        val latch = CountDownLatch(threadNum)
        for (i in 0 until threadNum) {
            threads[i] = Thread {
                action.run()
                latch.countDown()
            }
        }
        Arrays.stream<Thread>(threads).parallel().forEach { it.start() }
        latch.await()
    }

    /**
     * 测试多个线程顺序执行
     *
     * @param action    执行内容
     * @param threadNum 线程数
     */
    @Throws(InterruptedException::class)
    fun serializableTest(action: Runnable, threadNum: Int) {
        val threads = ArrayList<Thread>()
        for (i in 0 until threadNum) {
            threads.add(Thread(action))
            threads[i].start()
            threads[i].join()
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun concurrentAddCart() {

        this.concurrentTest(100, Runnable {cartService.addCart(1, 101, 1) })
        assertEquals(1, cartService.getCart(1).size.toLong())
    }

    @Test
    @Throws(InterruptedException::class)
    fun keyHolderTest() {
        this.concurrentTest(50, Runnable {
            val keyHolder = GeneratedKeyHolder()
            val insertSql = "INSERT INTO ease_test(name) VALUES(:threadName)"
            val insertParams = MapSqlParameterSource()
            insertParams.addValue("threadName", Thread.currentThread().toString())
            namedParameterJdbcTemplate.update(insertSql, insertParams, keyHolder)

            val selectSql = "SELECT name FROM ease_test WHERE id=:id"
            val selectParams = MapSqlParameterSource()
            selectParams.addValue("id", keyHolder.key!!.toLong())
            val actual = namedParameterJdbcTemplate.queryForObject(selectSql, selectParams, String::class.java)
            assertEquals(Thread.currentThread().toString(), actual)
        })
    }


    @Test
    @Throws(InterruptedException::class)
    fun testTransaction() {
        concurrentTest(2, Runnable { testService.sqlTest() })
    }
}
