package com.graduation.project.risk.common.core.dal.mongdb;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class CustomerBigDecimalConverter {

    public enum BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {

        INSTANCE;

        private static String format = "#.##";

        BigDecimalToDoubleConverter() {
        }

        public Double convert(BigDecimal source) {
            return source == null ? null : source.doubleValue();
        }
    }

    public enum DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

        INSTANCE;

        private static String format = "#.##";

        DoubleToBigDecimalConverter() {
        }

        public BigDecimal convert(Double source) {
            return source == null ? null : new BigDecimal(source);
        }
    }

}
