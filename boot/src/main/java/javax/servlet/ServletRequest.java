
package javax.servlet;

import java.util.Enumeration;

@Deprecated
public interface ServletRequest {
  String getParameter(String name);

  Enumeration<String> getParameterNames();
}

