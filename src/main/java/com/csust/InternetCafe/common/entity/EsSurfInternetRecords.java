package com.csust.InternetCafe.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author: 小凯神
 * @Date: 2019-03-06 16:09
 * @Description:
 */

@Data
@Builder
@Document(indexName = "es_graduation_project", type = "surf_internet_records")
public class EsSurfInternetRecords {

    private  Integer id;

    private  Integer computerId;

    private  Integer uid;

    private  Integer startTime;

    private  Integer endTime;

    private  Integer consumptionAmount;

    private  String cafeName;
}
