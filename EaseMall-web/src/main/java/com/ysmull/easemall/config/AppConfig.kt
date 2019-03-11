package com.ysmull.easemall.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.annotation.EnableTransactionManagement

import java.util.concurrent.Executor

/**
 * @author maoyusu
 */
@Configuration
@EnableAsync
@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
class AppConfig : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 7
        executor.maxPoolSize = 42
        executor.setQueueCapacity(11)
        executor.setThreadNamePrefix("@Async-")
        executor.initialize()
        return executor
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? {
        return null
    }
}
