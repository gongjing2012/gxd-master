package com.gxd.es.service;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
/**
 * @Author:gxd
 * @Description:
 * @Date: 15:19 2018/1/31
 * @Modified By:
 */


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;

public class ElasticTransportClient {

    public static TransportClient client =  ElasticTransportClient.initESClient();


    public static TransportClient  initESClient() throws NumberFormatException {

        TransportClient esclient= null;

        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "dataservercenteres").build();
            esclient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.13.3"), 9300));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return esclient;
    }

    public MultiSearchResponse search(String key) {


        SearchRequestBuilder srb1 =  ElasticTransportClient.client.prepareSearch("address")
                .setTypes("address")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchPhraseQuery("DESCRIPTION",key))
                        .should(QueryBuilders.matchPhraseQuery("FULL_NAME",key))
                        .mustNot(QueryBuilders.termQuery("ADDR_STATUS_CD",2))
                        .filter(QueryBuilders.rangeQuery("ADDR_TYPE_CD").from(5).to(7)))
                .setSize(2);
        SearchRequestBuilder srb2 =  ElasticTransportClient.client.prepareSearch("street")
                .setTypes("street")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("STREET",key))
                        .mustNot(QueryBuilders.termQuery("STATUS",2))
                        .filter(QueryBuilders.rangeQuery("ADDR_TYPE_CD").from(5).to(7)))
                .setSize(2);
        SearchRequestBuilder srb3 =  ElasticTransportClient.client.prepareSearch("addressalias")
                .setTypes("addressalias")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("ADDRESS_NAME",key))
                        .mustNot(QueryBuilders.termQuery("STATUS",2))
                        .filter(QueryBuilders.rangeQuery("ADDR_TYPE_CD").from(5).to(7)))
                .setSize(2);


        MultiSearchResponse response = ElasticTransportClient.client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .add(srb3)
                .get();

        return response;
    }

    public SearchResponse termsSearch(HashSet<String> addressidSet) {

        SearchResponse response = client.prepareSearch("address")
                .setTypes("address")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termsQuery("ADDRESS_ID",addressidSet))
                .setPostFilter(QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("ADDR_STATUS_CD",2)))
                .setExplain(true)
                .get();

        return response;
    }
}

