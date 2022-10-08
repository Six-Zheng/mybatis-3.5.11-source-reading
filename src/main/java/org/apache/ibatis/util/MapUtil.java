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
package org.apache.ibatis.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public class MapUtil {
  /**
   * A temporary workaround for Java 8 specific performance issue JDK-8161372 .<br>
   * This class should be removed once we drop Java 8 support.
   *
   * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8161372">https://bugs.openjdk.java.net/browse/JDK-8161372</a>
   */
  
  /**
   * 此工具包的出现是为了解决使用java.util.concurrent.ConcurrentHashMap中的computeIfAbsent方法时而出现的性能瓶颈问题，简单描述即
   * ConcurrentHashMap中的computeIfAbsent方法在寻找key对应的value时，则会进入由synchronized修饰的寻找value的代码块，而这个性能在
   * 高并发的情况下会导致严重的问题——线程阻塞，而由于ConcurrentHashMap是线程安全类，所以可以直接获取key对应的value，从而产生此工具类。
   */
  public static <K, V> V computeIfAbsent(Map<K, V> map, K key, Function<K, V> mappingFunction) {
    V value = map.get(key);
    if (value != null) {
      return value;
    }
    return map.computeIfAbsent(key, mappingFunction);
  }

  /**
   * Map.entry(key, value) alternative for Java 8.
   */
  
  /**
   * 创建一个与给定的key以及value相同的键值对，且key以及value都不可变。
   */
  public static <K, V> Entry<K, V> entry(K key, V value) {
    return new AbstractMap.SimpleImmutableEntry<>(key, value);
  }

  private MapUtil() {
    super();
  }
}
