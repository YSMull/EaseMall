package com.ysmull.easeshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Service
public class TestService {

    @Autowired
    TestDao testDao;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void sqlTest() {
        testDao.insert();
        long id = testDao.selectId();
        String actualName = testDao.selectName(id);
        assertEquals(Thread.currentThread().toString(), actualName);
    }
}
