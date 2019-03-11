package com.ysmull.easemall.annotation

import com.ysmull.easemall.model.entity.User.ROLE
import kotlin.annotation.Retention
import kotlin.annotation.AnnotationRetention

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Privilege(val role: Array<ROLE> = [], val login: Boolean = false)
