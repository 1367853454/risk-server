package com.graduation.project.risk.common.core.dal.mongdb;

import com.graduation.project.risk.common.model.Money;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class MongodbMoneyConverter {

    public enum MoneyToBigDecimalConverter implements Converter<Money, BigDecimal> {

        INSTANCE;

        MoneyToBigDecimalConverter() {
        }

        public BigDecimal convert(Money source) {
            return source == null ? new BigDecimal(0) : source.getAmount();
        }
    }

    public enum BigDecimalToMoneyConverter implements Converter<BigDecimal, Money> {

        INSTANCE;

        BigDecimalToMoneyConverter() {
        }

        public Money convert(BigDecimal source) {
            return source == null ? new Money(0, 0) : new Money(source);
        }
    }

}
