package com.csust.InternetCafe.common.service;

import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsRepository;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-10 12:31
 * @Description:
 */
@Service
public class EsSurfInernetRecordsImpl implements EsSurfInternetRecordsService {

    @Resource
    private EsSurfInternetRecordsRepository esSurfInternetRecordsRepository;

    @Override
    public List<EsSurfInternetRecords> getByName(String cafeName) {
        List<EsSurfInternetRecords> esSurfInternetRecordsList = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("cafeName" ,cafeName);
        Iterable<EsSurfInternetRecords> iterable = esSurfInternetRecordsRepository.search(matchQueryBuilder);
        iterable.forEach(e -> esSurfInternetRecordsList.add(e));
        return esSurfInternetRecordsList;
    }

    @Override
    public void save(EsSurfInternetRecords esSurfInternetRecords) {
        esSurfInternetRecordsRepository.save(esSurfInternetRecords);
    }
}
