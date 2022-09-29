package estest.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * persions
 * @author 
 */
@Data
public class Persions implements Serializable {
    private String id;

    private String name;

    private String sex;

    private String sect;

    private Integer age;

    private static final long serialVersionUID = 1L;
}