/*
 *    Copyright 2009-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * @author Clinton Begin
 */

/**
 * 执行器接口
 */
public interface Executor {

  /**
   * 结果处理器
   */
  ResultHandler NO_RESULT_HANDLER = null;

  /**
   * 更新数据（包括对数据库进行新增/删除/修改数据的操作）
   */
  int update(MappedStatement ms, Object parameter) throws SQLException;

  /**
   * 查询数据
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  /**
   * 查询数据
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  /**
   * 查询游标
   */
  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  /**
   * 刷新批处理语句
   */
  List<BatchResult> flushStatements() throws SQLException;

  /**
   * 提交事务
   */
  void commit(boolean required) throws SQLException;
  
  /**
   * 回滚事务
   */
  void rollback(boolean required) throws SQLException;

  /**
   * 创建缓存键
   */
  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  /**
   * 判断缓存中是否所执行语句的结果
   */
  boolean isCached(MappedStatement ms, CacheKey key);

  /**
   * 清除本地缓存
   */
  void clearLocalCache();

  /**
   * 延迟加载
   */
  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  /**
   * 获取事务
   */
  Transaction getTransaction();

  /**
   * 关闭执行器
   */
  void close(boolean forceRollback);

  /**
   * 执行器关闭状态
   */
  boolean isClosed();

  /**
   * 设置执行器包装器
   */
  void setExecutorWrapper(Executor executor);

}
