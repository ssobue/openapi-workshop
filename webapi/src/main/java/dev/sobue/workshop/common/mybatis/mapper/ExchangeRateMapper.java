package dev.sobue.workshop.common.mybatis.mapper;

import dev.sobue.workshop.common.Currency;
import dev.sobue.workshop.common.ExchangeRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExchangeRateMapper {

  ExchangeRate selectByCurrency(@Param("currency") Currency currency);
}
