package estest.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

// 关联的索引是item，类型是_doc，直接使用而不创建索引
@Document(indexName = "app_test",type = "persions", createIndex = false)//,type = "_doc"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Field(type=FieldType.Keyword)
    private String id;
    // title使用ik进行分词
    //@Field(type = FieldType.Text,analyzer = "ik_max_word")
    @Field(type=FieldType.Keyword)
    private String name;
    @Field(type=FieldType.Keyword)
    private String sex;
    @Field(type=FieldType.Keyword)
    private String sect;
    @Field(type=FieldType.Integer)
    private Integer age;


}
