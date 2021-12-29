package com.xiqing.project.elasticsearch.config;

import com.xiqing.project.elasticsearch.ElasticsearchUtil;
import com.xiqing.project.elasticsearch.properties.ElasticsearchProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xiqing.project.elasticsearch.properties")
public class ElasticsearchConfig {

    @Autowired
    public ElasticsearchProperties properties;

    @Bean
    public ElasticsearchUtil client(ElasticsearchProperties properties){
        return new ElasticsearchUtil(properties);
    }

}
