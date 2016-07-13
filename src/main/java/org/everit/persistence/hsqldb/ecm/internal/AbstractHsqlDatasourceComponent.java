/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.persistence.hsqldb.ecm.internal;

import org.everit.osgi.ecm.annotation.attribute.PasswordAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.persistence.hsqldb.ecm.HsqlDataSourceComponentConstants;
import org.hsqldb.jdbc.JDBCCommonDataSource;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

/**
 *
 * Abstract class for HSQL basic configuration.
 *
 */
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION, optional = true,
        label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
public abstract class AbstractHsqlDatasourceComponent {

  protected String password;

  protected ServiceRegistration<?> serviceRegistration;

  protected String url;

  protected String user;

  @PasswordAttribute(attributeId = HsqlDataSourceComponentConstants.ATTR_PASSWORD,
      priority = HsqlDataSourceAttributePriority.PASSWORD, label = "Password",
      description = "Password to use for login. When using "
          + "getConnection(String url, String user, String password) it's not required to set this "
          + "property as it is passed as parameter, but you will have to set it when using "
          + "getConnection(String url, Properties info) or JDBCDataSource.")
  public void setPassword(final String password) {
    this.password = password;
  }

  @StringAttribute(attributeId = HsqlDataSourceComponentConstants.ATTR_JDBC_URL,
      priority = HsqlDataSourceAttributePriority.JDBC_URL, label = "JDBC url",
      description = "The url of the database. Strats with jdbc:hsqldb:")
  public void setUrl(final String url) {
    this.url = url;
  }

  @StringAttribute(attributeId = HsqlDataSourceComponentConstants.ATTR_USER,
      priority = HsqlDataSourceAttributePriority.USER, label = "User",
      description = "User name to use for login. When using "
          + "getConnection(String url, String user, String password) it's not required to set "
          + "this property as it is passed as parameter, but you will have to set it when using "
          + "getConnection(String url, Properties info) or JtdsDataSource.")
  public void setUser(final String username) {
    user = username;
  }

  /**
   * Set the jDBCCommonDataSource configuration.
   *
   * @param jDBCCommonDataSource
   *          to set the configuration.
   */
  protected void updatePropertiesAToD(final JDBCCommonDataSource jDBCCommonDataSource) {
    jDBCCommonDataSource.setURL(url);
    jDBCCommonDataSource.setUser(user);
    jDBCCommonDataSource.setPassword(password);
  }
}
