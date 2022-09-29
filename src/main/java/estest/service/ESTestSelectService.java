package estest.service;


/**
 * 客户收养宠物记录表(AdoptRecordHistory)表服务接口
 *
 * @author makejava
 * @since 2021-04-03 19:17:23
 */

public interface ESTestSelectService {

    void select_string_Data();

    void select_term_Data();

    void select_range_Data();

    void select_wildcard_Data();

    void select_match_Data();

    void select_bool_Data();

    void select_cardinality_Data();

    void select_max_Data();

    void select_aggregations_Data();

    void select_aggregations_two_Data();


}
