package com.daishumovie.search.service;

import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestResult;
import io.searchbox.core.BulkResult;
import io.searchbox.core.DocumentResult;

import java.io.IOException;
import java.util.List;

/**
 * @author zhuruisong
 * @since 1.0
 */
public interface BaseSearch<E> {

    /**
     * 索引数据
     * @param e e
     * @return
     * @throws IOException
     */
    DocumentResult add(E e,String id,String index,String type) throws IOException;

    /**
     * 批量索引数据
     *
     * @throws IOException
     */
    BulkResult adds(List<BulkableAction> actions, String index, String type) throws IOException;

    /**
     * id删除
     * @param id
     * @return
     * @throws IOException
     */
    DocumentResult delete(Integer id,String index,String type) throws IOException;

    /**
     * 批量id删除
     * @param ids
     * @return
     * @throws IOException
     */
    JestResult deleteByIds(List<Integer> ids, String index, String type) throws IOException;





}
