package com.daishumovie.dao.mapper.smallbronzeadmin;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * GenericMapper Mapper层泛型接口，定义基本的Mapper功能
 * 
 * @author
 * @since
 * @param <T>
 *            实体类
 * @param <K>
 *            主键类，如果要存入memcahed或者activemq就必须实现Serializable接口
 * 
 */
public interface GenericMapper<K, T> {
	/**
	 * 通过主键ID删除
	 * 
	 * @param key
	 * @return
	 */
	public int deleteByPrimaryKey(K key);

	/**
	 * 通过部分字段匹配删除，是物理删除；
	 * 
	 * @param t
	 * @return
	 */
	public int deleteByCondition(@Param("entity") T t);

	/**
	 * 插入数据集
	 * 
	 * @param t
	 * @return
	 */
	public int insert(T t);

	/**
	 * 遇到主键重复则更新相关字段
	 * 
	 * @param t
	 * @return
	 */
	public int insertOrUpdate(T t);

	/**
	 * 选择性插入数据集
	 * 
	 * @param t
	 * @return
	 */
	public int insertSelective(T t);

	/**
	 * 通过主键ID查询数据集
	 * 
	 * @param key
	 * @return
	 */
	public T selectByPrimaryKey(K key);

	/**
	 * 分页获取数据，
	 * 
	 * @param t
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param offset
	 * @param orderBy
	 *            按哪个字段排序，如 id time
	 * @param sequence
	 *            'asc' 'desc'
	 * @param ids
	 *            约束条件
	 * @return
	 */
	public List<T> selectByParam(@Param("entity") T t, @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                 @Param("start") Integer start, @Param("offset") Integer offset, @Param("orderBy") String orderBy,
                                 @Param("sequence") String sequence, @Param("ids") Collection ids);


	List<T> selectByEntity(@Param("entity")T entity);

	/**
	 * 查询数据条数
	 * 
	 * @param t
	 * @param startTime
	 * @param endTime
	 * @param ids
	 *            约束条件
	 * @return
	 */
	public int selectResultCountByParam(@Param("entity") T t, @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                        @Param("ids") Collection ids);



	/**
	 * 部分更新
	 * 
	 * @param t
	 * @return
	 */
	public int updateByPrimaryKeySelective(T t);

	/**
	 * 全部数据更新
	 * 
	 * @param t
	 * @return
	 */
	public int updateByPrimaryKey(T t);

	/**
	 * 批量插入
	 * 
	 * @param valueObjects
	 * @return
	 */
	public int batchInsert(List<T> valueObjects);

	/**
	 * 批量删除
	 * 
	 * @param valueObjects
	 * @return
	 */
	public int batchDelete(List<String> valueObjects);

	
}
