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

/**
 * @author Clinton Begin
 */

/**
 * 错误上下文
 */
public class ErrorContext {

  /**
   * 初始化系统换行符
   */
  private static final String LINE_SEPARATOR = System.lineSeparator();
  
  /**
   * 初始化为每一个线程的ThreadLocal的值
   */
  private static final ThreadLocal<ErrorContext> LOCAL = ThreadLocal.withInitial(ErrorContext::new);
  
  /**
   * 当前存储的ErrorContext对象（一般为存储前一个ErrorContext对象）
   */
  private ErrorContext stored;
  
  /**
   * 资源文件名称
   */
  private String resource;
  
  /**
   * 进行操作的操作信息
   */
  private String activity;
  
  /**
   * 进行操作的对象名称
   */
  private String object;
  
  /**
   * 异常概览信息
   */
  private String message;
  
  /**
   * 异常SQL语句
   */
  private String sql;
  
  /**
   * 异常日志
   */
  private Throwable cause;

  private ErrorContext() {
  }

  /**
   * 获取当前的线程的ThreadLocal的值
   */
  public static ErrorContext instance() {
    return LOCAL.get();
  }

  /**
   * 存储新的ErrorContext对象
   * 其使用仅出现在org.apache.ibatis.executor.statement.BaseStatementHandler中的generateKeys方法中，主要是为了防止此方法中的
   * processBefore方法污染异常信息。
   */
  public ErrorContext store() {
    ErrorContext newContext = new ErrorContext();
    newContext.stored = this; // 存储当前的ErrorContext对象，方便恢复
    LOCAL.set(newContext);
    return LOCAL.get();
  }

  /**
   * 恢复到前一个ErrorContext对象
   * 其使用仅出现在org.apache.ibatis.executor.statement.BaseStatementHandler中的generateKeys方法中，主要是为了防止此方法中的
   * processBefore方法污染异常信息。
   */
  public ErrorContext recall() {
    if (stored != null) {
      LOCAL.set(stored);
      stored = null;
    }
    return LOCAL.get();
  }

  /**
   * 使用建造者模式进行对ErrorContext属性的赋值
   */
  public ErrorContext resource(String resource) {
    this.resource = resource;
    return this;
  }

  public ErrorContext activity(String activity) {
    this.activity = activity;
    return this;
  }

  public ErrorContext object(String object) {
    this.object = object;
    return this;
  }

  public ErrorContext message(String message) {
    this.message = message;
    return this;
  }

  public ErrorContext sql(String sql) {
    this.sql = sql;
    return this;
  }

  public ErrorContext cause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  /**
   * 重置ErrorContext
   */
  public ErrorContext reset() {
    resource = null;
    activity = null;
    object = null;
    message = null;
    sql = null;
    cause = null;
    LOCAL.remove();
    return this;
  }

  /**
   * 组装详细的错误上下文信息
   */
  @Override
  public String toString() {
    StringBuilder description = new StringBuilder();

    // message
    if (this.message != null) {
      description.append(LINE_SEPARATOR);
      description.append("### ");
      description.append(this.message);
    }

    // resource
    if (resource != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error may exist in ");
      description.append(resource);
    }

    // object
    if (object != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error may involve ");
      description.append(object);
    }

    // activity
    if (activity != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error occurred while ");
      description.append(activity);
    }

    // sql
    if (sql != null) {
      description.append(LINE_SEPARATOR);
      description.append("### SQL: ");
      description.append(sql.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').trim());
    }

    // cause
    if (cause != null) {
      description.append(LINE_SEPARATOR);
      description.append("### Cause: ");
      description.append(cause.toString());
    }

    return description.toString();
  }

}
