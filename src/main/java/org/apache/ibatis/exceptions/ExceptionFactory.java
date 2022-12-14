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
package org.apache.ibatis.exceptions;

import org.apache.ibatis.executor.ErrorContext;

/**
 * @author Clinton Begin
 */

/**
 * 异常工厂
 */
public class ExceptionFactory {

  private ExceptionFactory() {
    // Prevent Instantiation
  }

  /**
   * 异常包装方法
   * 此异常包装方法主要通过包装由org.apache.ibatis.executor下所提供的ErrorContext暴露更多关于此异常的信息，并将其返回，常与异常日志的输出搭配。
   * 除此之外，此异常包装方法还负责承担起将受检异常转换为非受检异常的作用，具体案例可参考org.apache.ibatis.session.SqlSessionFactoryBuilder中
   * 的SqlSessionFactory方法。
   */
  public static RuntimeException wrapException(String message, Exception e) {
    return new PersistenceException(ErrorContext.instance().message(message).cause(e).toString(), e);
  }
}
