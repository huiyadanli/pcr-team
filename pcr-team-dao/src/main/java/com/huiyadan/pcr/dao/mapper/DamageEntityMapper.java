package com.huiyadan.pcr.dao.mapper;

import com.huiyadan.pcr.dao.model.DamageEntity;
import org.springframework.beans.factory.annotation.Value;
import tk.mybatis.mapper.common.Mapper;

public interface DamageEntityMapper extends Mapper<DamageEntity> {

    int truncate();

    int deleteByStage(@Value("stage") Integer stage);

    Integer sumDamage(DamageEntity entity);
}