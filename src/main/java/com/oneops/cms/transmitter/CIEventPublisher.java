/*******************************************************************************
 *  
 *   Copyright 2015 Walmart, Inc.
 *  
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *  
 *       http://www.apache.org/licenses/LICENSE-2.0
 *  
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  
 *******************************************************************************/
package com.oneops.cms.transmitter;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.oneops.cms.transmitter.domain.CMSEvent;

public class CIEventPublisher extends MessagePublisher {

    private final String searchQueue = "search.stream";

    private MessageProducer producer = null;

	@Override
	protected String getDestinationName() {
		return searchQueue;
	}

	@Override
	protected String getDestinationType() {
		return "queue";
	}

	@Override
	protected Destination createDestination(Session session) throws JMSException {
		return session.createQueue(searchQueue);
	}

	@Override
	protected void createProducers(Session session, Destination destination) throws JMSException {
		producer = session.createProducer(destination);
		setProducerProperties(producer);
	}

	@Override
	protected MessageProducer getProducer(CMSEvent event) {
		return producer;
	}

	@Override
	protected void closeProducers() throws JMSException {
		producer.close();
	}

}