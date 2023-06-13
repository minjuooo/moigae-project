package com.moigae.application.core.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.moigae.application.core.config.PrimaryGeneratorProtoType.encodeTimestamp;
import static com.moigae.application.core.config.PrimaryGeneratorProtoType.generateRandomString;

@Component
public class PrimaryGenerator implements IdentifierGenerator {
    /**
     * 의미가 불분명한 매직 넘버를 상수로 선언하면 어떨까요? (홍정완) -> 최욱재
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return "c" + encodeTimestamp(System.currentTimeMillis()) + generateRandomString(10);
    }
}