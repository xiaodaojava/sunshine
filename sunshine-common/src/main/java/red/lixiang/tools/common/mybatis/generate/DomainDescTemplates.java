package red.lixiang.tools.common.mybatis.generate;

/**
 * @author lixiang
 * @date 2020/4/28
 **/
public class DomainDescTemplates {
    public static final String DB_MODEL = """
            package [(${basePackage})].models.dos;
            import red.lixiang.tools.common.mybatis.model.SqlField;
            import java.io.Serializable;
            import java.util.Date;
                        
            /**
             * @author lixiang
             */
            public class [(${table.javaTableName})]DO implements Serializable{
                        
               [# th:each="field : ${table.fieldList}"]
               /** [(${field.remark})]  */
               @SqlField
               private [(${field.javaType})] [(${field.camelName})];  
               [/]        
               [# th:each="field : ${table.fieldList}"]
               public [(${field.javaType})] get[(${field.javaName})]() {
                    return this.[(${field.camelName})];
               }
                        
               public [(${table.javaTableName})]DO set[(${field.javaName})]([(${field.javaType})] [(${field.camelName})]) {
                    this.[(${field.camelName})] = [(${field.camelName})];
                    return this;
               }
               [/]
                        
               public static [(${table.javaTableName})]DO create(){
                   return new [(${table.javaTableName})]DO();
               }
                        
               public [(${table.javaTableName})]DO build(){
                   return this;
               }
                        
            }
            """;

    public static final String QC = """
            package [(${basePackage})].models.qc;
                        
            import red.lixiang.tools.common.mybatis.model.BaseQC;
            import red.lixiang.tools.common.mybatis.model.QC;
            import java.util.Date;
                        
            /**
             * @author lixiang
             */
            public class [(${table.javaTableName})]QC extends BaseQC {
                        
               [# th:each="field : ${table.fieldList}"]
               [# th:if="${field.queryFlag}"]
               /** [(${field.remark})]  */
               private [(${field.javaType})] [(${field.camelName})];
               [/]
               [/]
                        
                [# th:each="field : ${table.fieldList}"]
                [# th:if="${field.queryFlag}"]
                        
                public [(${field.javaType})] get[(${field.javaName})]() {
                    return this.[(${field.camelName})];
                }
                        
                public [(${table.javaTableName})]QC set[(${field.javaName})]([(${field.javaType})] [(${field.camelName})]) {
                    this.[(${field.camelName})] = [(${field.camelName})];
                    return this;
                }
                 [/]
                [/]
                        
                        
                public static [(${table.javaTableName})]QC create(){
                    return new [(${table.javaTableName})]QC();
                }
                        
                public [(${table.javaTableName})]QC build(){
                    return this;
                }
                        
            }

            """;

    public static final String JAVA_MAPPER = """
            package [(${basePackage})].dao.[(${domainName})];
                        
            import [(${basePackage})].models.dos.[(${table.javaTableName})]DO;
            import [(${basePackage})].models.qc.[(${table.javaTableName})]QC;
            import org.apache.ibatis.annotations.*;
            import org.springframework.stereotype.Repository;
                        
            import java.util.List;
                        
            /**
             * 本类只做简单的增删改查,复杂的用mapper自己手写,也可以写在Provider里面
             * @author 造飞机团队自动生成
             */
            @Mapper
            @Repository
            public interface [(${table.javaTableName})]Mapper {
                        
                /**
                 * 获取列表
                 * @param [(${table.javaCamelName})]QC
                 * @see [(${table.javaTableName})]Provider#list[(${table.javaTableName})]s([(${table.javaTableName})]QC)
                 * @return
                 */
                @SelectProvider(type = [(${table.javaTableName})]Provider.class)
                List<[(${table.javaTableName})]DO> list[(${table.javaTableName})]s([(${table.javaTableName})]QC [(${table.javaCamelName})]QC);
                        
                /**
                 * 获取数量
                 * @param [(${table.javaCamelName})]QC
                 * @see [(${table.javaTableName})]Provider#count[(${table.javaTableName})]s([(${table.javaTableName})]QC)
                 * @return
                 */
                @SelectProvider(type = [(${table.javaTableName})]Provider.class)
                long count[(${table.javaTableName})]s([(${table.javaTableName})]QC [(${table.javaCamelName})]QC);
                        
                /**
                 * 插入
                 * @param [(${table.javaCamelName})]
                 * @see [(${table.javaTableName})]Provider#insert[(${table.javaTableName})]([(${table.javaTableName})]DO)
                 * @return
                 */
                @InsertProvider(type = [(${table.javaTableName})]Provider.class)
                @SelectKey(keyColumn = "id",resultType = Long.class,before = false,keyProperty = "id",statement ="SELECT LAST_INSERT_ID()" )
                int insert[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]);
                        
                /**
                 * 更新,必须要有id
                 * @param [(${table.javaCamelName})]
                 * @see [(${table.javaTableName})]Provider#update[(${table.javaTableName})]([(${table.javaTableName})]DO)
                 * @return
                 */
                @UpdateProvider(type = [(${table.javaTableName})]Provider.class)
                int update[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]);
                        
                        
                /**
                * 删除,必须要有id
                * @param id
                * @see [(${table.javaTableName})]Provider#remove[(${table.javaTableName})]ById(long id)
                * @return
                */
                @UpdateProvider(type = [(${table.javaTableName})]Provider.class)
                int remove[(${table.javaTableName})]ById(long id);
            }
                        
            """;

    public static final String PROVIDER = """
            package [(${basePackage})].dao.[(${domainName})];
             
             import [(${basePackage})].models.dos.[(${table.javaTableName})]DO;
             import [(${basePackage})].models.qc.[(${table.javaTableName})]QC;
             import red.lixiang.tools.common.mybatis.MapperTools;
             import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
             import org.apache.ibatis.jdbc.SQL;
             
             /**
              * @author lixiang
              */
             public class [(${table.javaTableName})]Provider implements ProviderMethodResolver {
             
                 private static final String TABLE_FIELDS = MapperTools.getTableFieldName([(${table.javaTableName})]DO.class);
             
                 public String list[(${table.javaTableName})]s([(${table.javaTableName})]QC [(${table.javaCamelName})]QC){
                     SQL sql = new SQL() {{
                         SELECT(TABLE_FIELDS);
                         FROM("[(${table.tableName})]");
                     }};
                     MapperTools.richWhereSql(sql, [(${table.javaCamelName})]QC);
             
                     return sql.toString();
                 }
             
                 public String count[(${table.javaTableName})]s([(${table.javaTableName})]QC [(${table.javaCamelName})]QC){
                     SQL sql = new SQL() {{
                         SELECT("count(1)");
                         FROM("[(${table.tableName})]");
                     }};
                     MapperTools.richWhereSql(sql, [(${table.javaCamelName})]QC);
             
                     return sql.toString();
                 }
             
                 public String insert[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]){
                     SQL sql = new SQL() {{
                         INSERT_INTO("[(${table.tableName})]");
                     }};
                     MapperTools.richInsertSql(sql, [(${table.javaCamelName})]);
             
                     return sql.toString();
                 }
                 public String update[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]) {
                     SQL sql = new SQL() {{
                         UPDATE("[(${table.tableName})]");
             
                     }};
                     MapperTools.richUpdate(sql, [(${table.javaCamelName})]);
                     sql.WHERE("id = #{id}");
                     return sql.toString();
                 }
             
                 public String remove[(${table.javaTableName})]ById(long id){
                         SQL sql = new SQL() {{
                             UPDATE("[(${table.tableName})]");
                             SET("delete_flag = 1");
                         }};
                         sql.WHERE("id = #{id}");
                         return sql.toString();
                 }
             
             }
            """;

    public static final String CONTROLLER = """
            package [(${basePackage})].admin.controller;
                        
            import com.alibaba.fastjson.JSON;
            import red.lixiang.tools.base.BaseResponse;
            import red.lixiang.tools.base.PageData;
            import red.lixiang.tools.common.mybatis.model.Page;
            import red.lixiang.tools.common.mybatis.model.Sort;
            import [(${basePackage})].business.manager.[(${table.javaTableName})]Manager;
            import [(${basePackage})].models.dos.[(${table.javaTableName})]DO;
            import [(${basePackage})].models.qc.[(${table.javaTableName})]QC;
            import org.springframework.beans.factory.annotation.Autowired;
            import org.springframework.stereotype.Controller;
            import org.springframework.web.bind.annotation.GetMapping;
            import org.springframework.web.bind.annotation.PostMapping;
            import org.springframework.web.bind.annotation.ResponseBody;
                        
                        
            import java.util.Date;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
                        
            /**
             * @Author 造飞机团队
             **/
            @Controller
            public class [(${table.javaTableName})]Controller  {
                @Autowired
                private [(${table.javaTableName})]Manager [(${table.javaCamelName})]Manager;
                        
                @GetMapping("/[(${table.javaCamelName})]/query")
                @ResponseBody
                public BaseResponse<PageData<[(${table.javaTableName})]DO>> query[(${table.javaTableName})]([(${table.javaTableName})]QC qc, Page page ){
                        
                        
                    Long totalCount = [(${table.javaCamelName})]Manager.count[(${table.javaTableName})](qc);
                    qc.setPage(page);
                    List<[(${table.javaTableName})]DO> [(${table.javaCamelName})]s = [(${table.javaCamelName})]Manager.query[(${table.javaTableName})](qc);
                        
                    return  BaseResponse.assemblePageResponse([(${table.javaCamelName})]s,totalCount,page.getPageIndex(),page.getPageSize());
                }
                        
                @PostMapping("/[(${table.javaCamelName})]/save")
                @ResponseBody
                public BaseResponse<String> save[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]){
                    BaseResponse<String> baseResponse = new BaseResponse<>();
                    [(${table.javaCamelName})]Manager.save[(${table.javaTableName})]([(${table.javaCamelName})]);
                    baseResponse.setData("OK");
                    return  baseResponse;
                }

                @GetMapping("/[(${table.javaCamelName})]/get")
                @ResponseBody
                public BaseResponse<[(${table.javaTableName})]DO> get[(${table.javaTableName})]([(${table.javaTableName})]QC qc){
                    qc.setPage(Page.getOne());
                    List<[(${table.javaTableName})]DO> [(${table.javaCamelName})]s = [(${table.javaCamelName})]Manager.query[(${table.javaTableName})](qc);
                    if([(${table.javaCamelName})]s!=null && [(${table.javaCamelName})]s.size()>0){
                        return BaseResponse.success([(${table.javaCamelName})]s.get(0));
                    }else{
                        return BaseResponse.fail("no data info");
                    }
                }
                        
                @GetMapping("/[(${table.javaCamelName})]/remove")
                @ResponseBody
                public BaseResponse<String> remove(Long id){
                    BaseResponse<String> baseResponse = new BaseResponse<>();
                    [(${table.javaCamelName})]Manager.remove[(${table.javaTableName})]ById(id);
                    baseResponse.setData("OK");
                    return  baseResponse;
                }
                        
            }
            """;

    public static final String MANAGER = """
            package [(${basePackage})].business.manager;
                                 
            import [(${basePackage})].models.dos.[(${table.javaTableName})]DO;
            import [(${basePackage})].models.qc.[(${table.javaTableName})]QC;
                        
            import red.lixiang.tools.common.mybatis.model.Page;
            import red.lixiang.tools.common.mybatis.model.Sort;
            import java.util.List;
                               
            public interface [(${table.javaTableName})]Manager {
                        
                public [(${table.javaTableName})]DO get[(${table.javaTableName})]ById (Long id);
                                        
                public List<[(${table.javaTableName})]DO> query[(${table.javaTableName})]([(${table.javaTableName})]QC qc);
                              
                public Long count[(${table.javaTableName})]([(${table.javaTableName})]QC [(${table.javaCamelName})]);
                        
                public [(${table.javaTableName})]DO save[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]);
                        
                public int remove[(${table.javaTableName})]ById(Long id);
                             
            }
                        
            """;

    public static final String MANAGER_IMPL = """
            package [(${basePackage})].business.manager.impl.[(${domainName})];
                        
            import [(${basePackage})].models.dos.[(${table.javaTableName})]DO;
            import [(${basePackage})].models.qc.[(${table.javaTableName})]QC;
            import red.lixiang.tools.jdk.ListTools;
            import [(${basePackage})].business.manager.[(${table.javaTableName})]Manager;
            import [(${basePackage})].dao.[(${domainName})].[(${table.javaTableName})]Mapper;
            import red.lixiang.tools.common.mybatis.model.Page;
            import red.lixiang.tools.common.mybatis.model.Sort;
            import org.springframework.beans.BeanUtils;
            import org.springframework.beans.factory.annotation.Autowired;
            import org.springframework.stereotype.Component;
                        
            import java.util.Date;
            import java.util.List;
                        
            @Component
            public class [(${table.javaTableName})]ManagerImpl implements [(${table.javaTableName})]Manager{
                        
                @Autowired
                private [(${table.javaTableName})]Mapper [(${table.javaCamelName})]Mapper;
              
                @Override
                public [(${table.javaTableName})]DO get[(${table.javaTableName})]ById(Long id) {
                    [(${table.javaTableName})]QC qc = new [(${table.javaTableName})]QC();
                    qc.setId(id);
                    qc.setPage(Page.getOne());
                    List<[(${table.javaTableName})]DO> [(${table.javaCamelName})]DOS = query[(${table.javaTableName})](qc);
                    return ListTools.getOne([(${table.javaCamelName})]DOS);
                }
               
                        
                @Override
                public List<[(${table.javaTableName})]DO> query[(${table.javaTableName})]([(${table.javaTableName})]QC qc){
                        
                    List<[(${table.javaTableName})]DO> [(${table.javaCamelName})]s = [(${table.javaCamelName})]Mapper.list[(${table.javaTableName})]s(qc);
                    return [(${table.javaCamelName})]s;
                }
              
                        
                @Override
                public Long count[(${table.javaTableName})]([(${table.javaTableName})]QC qc){
                        
                    Long count = [(${table.javaCamelName})]Mapper.count[(${table.javaTableName})]s(qc);
                    return count;
                }
                        
                @Override
                public [(${table.javaTableName})]DO save[(${table.javaTableName})]([(${table.javaTableName})]DO [(${table.javaCamelName})]){
                        
                    if([(${table.javaCamelName})].getId()!=null){
                         [(${table.javaCamelName})]Mapper.update[(${table.javaTableName})]([(${table.javaCamelName})]);
                    }else {
                        [(${table.javaCamelName})].setCreateTime(new Date());
                         [(${table.javaCamelName})]Mapper.insert[(${table.javaTableName})]([(${table.javaCamelName})]);
                    }
                    return [(${table.javaCamelName})];
                        
                }
                        
                @Override
                public int remove[(${table.javaTableName})]ById(Long id){
                        
                    return [(${table.javaCamelName})]Mapper.remove[(${table.javaTableName})]ById(id);
                        
                }
                    
            }
                        
            """;

    public static final String XML_MAPPER = """
            <?xml version="1.0" encoding="UTF-8" ?>  
            <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
                      
            <mapper namespace="${basePackage}.dao.${table.javaTableName}DAO">
              	
            	<!--The user defined SQL!-->
              	
              	
              	<!--The user defined SQL!-->
                      
              	<resultMap id="BaseResultMap" type="${basePackage}.model.${table.javaTableName}" >
                        <id column="id" property="id" jdbcType="BIGINT" />
                        <% for(column in table.fieldList) { %>
                           <result column="${column.name}" property="${column.camelName}" jdbcType="${column.jdbcType}" />
                        <% } %>
                </resultMap>
                      
                <sql id="queryCondition" >
                    <where>
                    <% for(column in table.fieldList) { %>
                        <if test="${column.camelName} != null ">
                            and ${column.name} = #{${column.camelName}}
                        </if>
                    <% } %>
                    </where>
                </sql>
                      
                <sql id="Base_Column_List">
                    <trim prefix="" prefixOverrides=",">
                    <% for(column in table.fieldList) { %>
                      , ${column.name}
                    <% } %>
                    </trim>
                </sql>
                      
                      
              	
                <insert id="insert${table.javaTableName}" parameterType="${basePackage}.model.${table.javaTableName}">
                    insert into ${table.tableName}
                    <trim prefix="(" suffix=")" suffixOverrides="," >
                    <% for(column in table.fieldList) { %>
                    <if test="${column.camelName} != null" >
                        ${column.name},
                    </if>
                    <% } %>
                    </trim>
                    <trim prefix="values (" suffix=")" suffixOverrides="," >
                    <% for(column in table.fieldList) { %>
                    <if test="${column.camelName} != null" >
                        #{${column.camelName},jdbcType=${column.jdbcType}},
                    </if>
                    <% } %>
                    </trim>
                </insert>
                      
                <update id="update${table.javaTableName}" parameterType="${basePackage}.model.${table.javaTableName}">
                    UPDATE ${table.tableName}
                    <trim prefix="set" suffixOverrides="," >
                      
                    <% for(column in table.fieldList) { %>
                    <if test="${column.camelName} != null" >
                        ${column.name} = #{${column.camelName}},
                    </if>
                    <% } %>
                    </trim>
                    <include refid="queryCondition"/>
                </update>
                      
                <select id="list${table.javaTableName}" parameterType="${basePackage}.model.qc.${table.javaTableName}QC" resultMap="BaseResultMap">
                    select <include refid="Base_Column_List" />
                    from ${table.tableName} <include refid="queryCondition" />
                    <if test="sortBy != null and sortType != null" >
                        order by #{sortBy} #{sortType}
                    </if>
                    <if test="page != null and page.startIndex != null and page.pageSize != null">
                        <![CDATA[ limit #{page.startIndex},#{page.pageSize} ]]>
                    </if>
                </select>
                      
                <select id="count${table.javaTableName}" parameterType="${basePackage}.model.qc.${table.javaTableName}QC" resultType="java.lang.Long">
                    SELECT count(*) from ${table.tableName}
                    <include refid="queryCondition"></include>
                </select>
                      
                      
                <insert id="insertBatch" >
                 	insert into ${table.tableName}
                 	(
                        <trim prefix="" prefixOverrides=",">
                        	<% for(column in table.fieldList) { %>,${column.name}<% } %>
                        </trim>
                    ) values
                	<foreach collection="list" item="item" index="index" separator=","> 
            	    (
                        <trim prefix="" prefixOverrides=",">
                            <% for(column in table.fieldList) { %> ,#{item.${column.camelName}}<% } %>
                        </trim>
            	     )
                	</foreach> 
                </insert>
                      
                <delete id="delete${table.javaTableName}ById" parameterType="java.lang.Long">
                        delete from ${table.tableName} where id = #{id}
                    </delete>
                      
            </mapper>
            """;


}
