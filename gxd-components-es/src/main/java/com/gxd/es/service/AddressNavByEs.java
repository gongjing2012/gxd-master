package com.gxd.es.service;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class AddressNavByEs {

    protected static final Logger logger = LoggerFactory.getLogger("AddressNavByEs");

    public HashSet<String>  search(String key) {

        ElasticTransportClient ElasticTransportClient = new ElasticTransportClient();
        long startime = System.currentTimeMillis();
        MultiSearchResponse SearchResponse = ElasticTransportClient.search(key);

        Map tmpMap = new HashMap<>();
        HashSet<String> idSet = new HashSet<String>();
        HashSet<String> resultSet = new HashSet();

        String addressId = null,street = null,fullName = null ,addressName = null;
        for (MultiSearchResponse.Item item : SearchResponse.getResponses()) {
            org.elasticsearch.action.search.SearchResponse response = item.getResponse();

            for (SearchHit hit : response.getHits().getHits()) {
                Map<String, Object> indexMap = new HashMap();
                indexMap = hit.getSourceAsMap();
                addressId = indexMap.get("ADDRESS_ID") == null ? "" : indexMap.get("ADDRESS_ID").toString();
                if (hit.getIndex().equals("address")) {
                    fullName = indexMap.get("FULL_NAME") == null ? "" : indexMap.get("FULL_NAME").toString();
                    resultSet.add("{ADDRESS_ID:" + addressId + ",FULL_NAME:" + fullName + "}");
                } else if (hit.getIndex().equals("street")) {
                    street = indexMap.get("STREET") == null ? "" : indexMap.get("STREET").toString();
                    tmpMap.put(addressId, street);
                    idSet.add(addressId);
                } else {
                    addressName = indexMap.get("ADDRESS_NAME") == null ? "" : indexMap.get("ADDRESS_NAME").toString();
                    tmpMap.put(addressId, addressName);
                    idSet.add(addressId);
                }

            }
        }

        SearchResponse responseById = ElasticTransportClient.termsSearch(idSet);

        for (SearchHit hit : responseById.getHits().getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            addressId = map.get("ADDRESS_ID") == null ? "" : map.get("ADDRESS_ID").toString();
            fullName = map.get("FULL_NAME") == null ? "" : map.get("FULL_NAME").toString();
            resultSet.add("{ADDRESS_ID:" + addressId+ ",FULL_NAME:" + fullName + "_" + tmpMap.get(map.get("ADDRESS_ID")) + "}");
        }

        System.out.println(resultSet);
        logger.debug("resultSet:"+ resultSet.toString());
        System.out.println("查询共消耗时间：" + (System.currentTimeMillis() - startime) + "ms");
        logger.debug("查询共消耗时间:"+ (System.currentTimeMillis() - startime) + "ms");
        return  resultSet;
    }


}
