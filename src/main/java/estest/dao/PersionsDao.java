package estest.dao;

import estest.entity.Persions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PersionsDao {
    int deleteByPrimaryKey(String id);

    int insert(Persions record);

    int insertSelective(Persions record);

    Persions selectByPrimaryKey(String id);
    List<Persions> selectall();
    int updateByPrimaryKeySelective(Persions record);

    int updateByPrimaryKey(Persions record);
}