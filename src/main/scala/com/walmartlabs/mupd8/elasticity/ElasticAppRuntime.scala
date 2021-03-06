/**
 * Copyright 2011-2012 @WalmartLabs, a division of Wal-Mart Stores, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.walmartlabs.mupd8.elasticity

import com.walmartlabs.mupd8.HashRing
import com.walmartlabs.mupd8.MUCluster
import com.walmartlabs.mupd8.PerformerPacket
import com.walmartlabs.mupd8.AppStaticInfo
import com.walmartlabs.mupd8.AppRuntime
import com.walmartlabs.mupd8.messaging.AdvancedMessageHandler
import com.walmartlabs.mupd8.messaging.MessageHandler


class ElasticAppRuntime(appID: Int,
  poolsize: Int,
  override val app: AppStaticInfo,
  useNullPool: Boolean = false) extends AppRuntime(appID, poolsize, app, useNullPool) {

  /*
   COMMENT: Override the default message handler with an advanced implementation that supports exchange of messages for the 
   two-phase load balancing protocol. 
  */
  override def getMessageHandler(): MessageHandler = {
    new AdvancedMessageHandler(app, this)
  }

  /*
   COMMENT: Override the default MapUpdate pool,  with an advanced implementation. See ElasticMapUpdate pool for the overriden methods.
  */
  override def initMapUpdatePool(poolsize: Int, ring: HashRing, clusterFactory: (PerformerPacket => Unit) => MUCluster[PerformerPacket]): ElasticMapUpdatePool[PerformerPacket] = {
    new ElasticMapUpdatePool[PerformerPacket](this, poolsize, ring, clusterFactory)
  }

}
