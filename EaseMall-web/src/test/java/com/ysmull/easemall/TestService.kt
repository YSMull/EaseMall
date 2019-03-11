package com.ysmull.easemall

import org.junit.Assert.assertEquals
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class TestService(
        internal var testDao: TestDao
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun sqlTest() {
        testDao.insert()
        val id = testDao.selectId()
        val actualName = testDao.selectName(id)
        assertEquals(Thread.currentThread().toString(), actualName)
    }
}
