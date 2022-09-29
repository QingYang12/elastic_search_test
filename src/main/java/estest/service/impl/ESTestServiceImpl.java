package estest.service.impl;


import estest.dao.PersionsDao;
import estest.entity.Item;
import estest.entity.Persions;
import estest.service.ESTestService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(SpringRunner.class)

//@SpringBootTest(classes = ElasticApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
@Service("esTestServiceImpl")
public class ESTestServiceImpl implements ESTestService {
    //private static Logger logger=Logger.getLogger(ESTestServiceImpl.class);

    @Resource
    public PersionsDao persionsDao;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    //插入
    @Test
    public void initInsertData(){
        List<Persions> persionsAllList = persionsDao.selectall();
        System.out.println(0);
        List<IndexQuery> list = new ArrayList();
        for(Persions persions:persionsAllList){
            Item item=new Item(persions.getId(),persions.getName(),persions.getSex(),persions.getSect(),persions.getAge());
            list.add(new IndexQueryBuilder().withObject(item).build());

        }
        System.out.println(1);
        elasticsearchTemplate.bulkIndex(list);
        System.out.println(2);
    }
    //更新
    @Test
    public void updateData(){
        Map<String, Object> params = new HashMap<>();
        params.put("age", new Integer("21"));
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.doc(params);
        System.out.println(0);
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Item.class)
                .withId("001")
                .withUpdateRequest(updateRequest)
                .build();
        System.out.println(1);
        elasticsearchTemplate.update(updateQuery);
        System.out.println(2);
    }
}
