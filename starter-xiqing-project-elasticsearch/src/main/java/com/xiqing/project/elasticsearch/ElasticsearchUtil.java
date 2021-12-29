package com.xiqing.project.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldAndFormat;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.xiqing.project.elasticsearch.properties.ElasticsearchProperties;
import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class ElasticsearchUtil {

    @Autowired
    public  ElasticsearchProperties elasticsearchProperties;

    public  ElasticsearchClient client;

    public ElasticsearchUtil(ElasticsearchProperties properties){
        this.elasticsearchProperties = properties;
        this.initializerESClient();

    }
    public void initializerESClient(){
        RestClient restClient = RestClient.builder(
                new HttpHost(elasticsearchProperties.getHostname(), elasticsearchProperties.getPort())).build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        this.client = new ElasticsearchClient(transport);
    }

    public ElasticsearchClient getClient() {
        return client;
    }


    /**
     * 创建索引
     * @param index: 索引名称
	 * @param aliases: 别名
     * @author : XI.QING
     * @date : 2021/12/28
     */
    public SimpleResponseBean createIndex(String index, String aliases){
        try {
            client.indices().create(c -> c
                    .index(index)
                    .aliases(aliases, a -> a
                            .isWriteIndex(true)
                    )
            );
            return SimpleResponseBean.SUCCESS("索引创建成功");
        }catch (Exception e){
            return SimpleResponseBean.FAILED("索引创建失败，失败原因："+e.getMessage());
        }

    }

    /**
     * 创建索引数据文档
     * @param index: 索引名称
     * @author : XI.QING
     * @date : 2021/12/29
     */
    public <T> SimpleResponseBean createDocument(String index, Object o){
        try{
            if(o != null){
                String objectStr = o.toString();
                if(objectStr.length() > 0){
                    int startIndex = objectStr.indexOf("(");
                    int endIndex = objectStr.lastIndexOf(")");
                    String str = objectStr.substring(startIndex+1, endIndex);
                    String[] strs = str.split(",");
                    Map<String, String> map = new HashMap<>();
                    Map<String, String> idMap = new HashMap<>(1);
                    for (String s:
                            strs) {
                        String[] r = s.split("=");
                        map.put(r[0], r[1]);
                        if("id".equals(r[0])){
                            idMap.put("id",r[1]);
                        }
                    }
                    CreateRequest dataStreamResponse = CreateRequest.of(e -> e
                            .index(index)
                            .id(idMap.get("id"))
                            .type("_doc")
                            .document(map));
                    client.create(dataStreamResponse);
                }
            }
        }catch (Exception e){
            return SimpleResponseBean.FAILED("创建文档失败，失败原因："+e.getMessage());
        }
        return SimpleResponseBean.SUCCESS();
    }


    /**
     * 创建索引数据文档
     * @param index: 索引名称
	 * @param list: 数据集合
     * @author : XI.QING
     * @date : 2021/12/29
     */
    public <T> SimpleResponseBean createDocument(String index, List<T> list){
        try{
            if(list != null && list.size() > 0){
                for (int i = 0; i < list.size(); i++) {
                    T object = (T)list.get(i);
                    String objectStr = object.toString();
                    if(objectStr.length() > 0){
                        int startIndex = objectStr.indexOf("(");
                        int endIndex = objectStr.lastIndexOf(")");
                        String str = objectStr.substring(startIndex+1, endIndex);
                        String[] strs = str.split(",");
                        Map<String, String> map = new HashMap<>();
                        Map<String, String> idMap = new HashMap<>(1);

                        for (String s:
                                strs) {
                            String[] r = s.split("=");
                            map.put(r[0], r[1]);
                            if("id".equals(r[0])){
                                idMap.put("id",r[1]);
                            }
                        }
                        CreateRequest dataStreamResponse = CreateRequest.of(e -> e
                                .index(index)
                                .id(idMap.get("id"))
                                .type("_doc")
                                .document(map));
                        client.create(dataStreamResponse);
                    }
                }

            }
        }catch (Exception e){
            return SimpleResponseBean.FAILED("创建文档失败，失败原因："+e.getMessage());
        }
        return SimpleResponseBean.SUCCESS();
    }

    /**
     * 查询索引
     * @param indexList: 查询索引的名称
     * @param clazz: 返回结果的类型
     * @author : XI.QING
     * @date : 2021/12/29
     */
    public <T> DataResponseBean search(List<String> indexList, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(indexList)
            );
            SearchResponse<T> response = client.search(searchRequest, clazz);
            if(response.hits() != null){
                List<Hit<T>> list = response.hits().hits();
                for (Hit<T> hit:
                        list) {
                    T t = (T)hit.source();
                    resultList.add(t);
                }
            }
        }catch (Exception e){
            return DataResponseBean.FAILED("查询失败， 失败原因："+e.getMessage());
        }
        return DataResponseBean.SUCCESS(resultList);
    }

    /**
     * 查询索引
     * @param indexList: 查询索引的名称
     * @param keyword: 查询关键字(暂时无效，原因不明)
	 * @param clazz: 返回结果的类型
     * @author : XI.QING 
     * @date : 2021/12/29       
     */
    public <T> DataResponseBean search(List<String> indexList, String keyword, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(indexList)
                    .fields(f -> f.field(keyword))
            );
            SearchResponse<T> response = client.search(searchRequest, clazz);
            if(response.hits() != null){
                List<Hit<T>> list = response.hits().hits();
                for (Hit<T> hit:
                        list) {
                    T t = (T)hit.source();
                    resultList.add(t);
                }
            }
        }catch (Exception e){
            return DataResponseBean.FAILED("查询失败， 失败原因："+e.getMessage());
        }
        return DataResponseBean.SUCCESS(resultList);
    }
    
    /**
     * 删除索引
     * @param index: 索引名称
     * @author : XI.QING 
     * @date : 2021/12/29       
     */
    public SimpleResponseBean deleteIndex(String index){
        try {
            client.delete(c -> c
                    .index(index)
            );
            return SimpleResponseBean.SUCCESS("索引删除成功");
        }catch (Exception e){
            return SimpleResponseBean.FAILED("索引删除失败，失败原因："+e.getMessage());
        }

    }

    /**
     * 删除索引
     * @param index: 索引名称
	 * @param id: ID
     * @author : XI.QING
     * @date : 2021/12/29
     */
    public SimpleResponseBean deleteIndex(String index, String id){
        try {
            client.delete(c -> c
                    .index(index)
                    .id(id)
            );
            return SimpleResponseBean.SUCCESS("索引删除成功");
        }catch (Exception e){
            return SimpleResponseBean.FAILED("索引删除失败，失败原因："+e.getMessage());
        }

    }


}
