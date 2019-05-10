package com.csust.InternetCafe.common.mapper;

import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 小凯神
 * @Date: 2019-03-07 9:54
 * @Description:
 */
@Component
public interface EsSurfInternetRecordsRepository extends ElasticsearchRepository<EsSurfInternetRecords ,Long> {
}
