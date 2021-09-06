package io.github.jinlongliao.easytask.common.utils;

import io.github.jinlongliao.easytask.common.util.ServiceLoadUtil;
import io.github.jinlongliao.easytask.common.utils.spi.Abs;
import io.github.jinlongliao.easytask.common.utils.spi.Imp4;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ServiceLoadUtilTest {
  @Test
  public void loadTest() {
    final List<Class<Abs>> classList = ServiceLoadUtil.loadClass(Abs.class);
    Assert.assertNotNull(classList);
    Assert.assertFalse(classList.contains(Imp4.class));

  }
}
