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

import java.util.Dictionary;
import java.util.Hashtable;

import javax.sql.CommonDataSource;
import javax.sql.DataSource;

import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ManualServices;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.everit.persistence.hsqldb.ecm.HsqlDataSourceComponentConstants;
import org.hsqldb.jdbc.JDBCDataSource;
import org.hsqldb.jdbc.pool.JDBCXADataSource;

/**
 * Configurable component that registers {@link JDBCXADataSource} as an OSGi service.
 */
@ExtendComponent
@Component(componentId = HsqlDataSourceComponentConstants.DATASOURCE_SERVICE_FACTORY_PID,
    configurationPolicy = ConfigurationPolicy.FACTORY, label = "Everit HyperSQL DataSource",
    description = "Configurable component that instantiates HsqlDataSource and registers it as an "
        + "OSGi service based on DataSource interface.")
@ManualServices(@ManualService({ DataSource.class, CommonDataSource.class }))
public class HsqlDataSourceComponent extends AbstractHsqlDatasourceComponent {

  /**
   * Instantiates a {@link JDBCXADataSource} and registers it as an OSGi service.
   */
  @Activate
  public void activate(final ComponentContext<HsqlDataSourceComponent> componentContext) {
    JDBCDataSource jdbcDataSource = null;
    jdbcDataSource = new JDBCDataSource();

    updatePropertiesAToD(jdbcDataSource);

    Dictionary<String, Object> serviceProperties =
        new Hashtable<>(componentContext.getProperties());
    serviceRegistration = componentContext.registerService(
        new String[] { DataSource.class.getName(), CommonDataSource.class.getName() },
        jdbcDataSource, serviceProperties);
  }

  /**
   * Unregisters the {@link JDBCXADataSource} OSGi service.
   */
  @Deactivate
  public void deactivate() {
    if (serviceRegistration != null) {
      serviceRegistration.unregister();
    }
  }

}
