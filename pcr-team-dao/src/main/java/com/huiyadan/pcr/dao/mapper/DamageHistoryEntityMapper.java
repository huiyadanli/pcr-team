package com.huiyadan.pcr.dao.mapper;

import com.huiyadan.pcr.dao.model.DamageHistoryEntity;
import org.springframework.beans.factory.annotation.Value;
import tk.mybatis.mapper.common.Mapper;

public interface DamageHistoryEntityMapper extends Mapper<DamageHistoryEntity> {

    int insertFromDamageTable(@Value("stage") Integer stage);
}