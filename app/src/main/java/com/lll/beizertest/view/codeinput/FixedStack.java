package com.lll.beizertest.view.codeinput;

import java.util.Stack;

/**
 * @author
 */
public class FixedStack<T> extends Stack<T> {

  int maxSize = 0;

  @Override public T push(T object) {
    if (maxSize > size()) {//当超出时不会再push
      return super.push(object);
    }

    return object;
  }

  public int getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
  }
}
