package be.vdab.services;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
public @interface ModifyingTransactionalServiceMethod 
{}
