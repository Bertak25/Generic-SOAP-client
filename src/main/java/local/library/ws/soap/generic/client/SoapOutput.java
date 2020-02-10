/*
	
	Copyright 2011 The IMPACT Project
	
	@author Dennis Neumann

	Licensed under the Apache License, Version 2.0 (the "License"); 
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
 
  		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

*/
package local.library.ws.soap.generic.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class SoapOutput {

    private Logger logger = LogManager.getLogger(SoapOutput.class);
    private String response;

    private String name;

    private String value = "";

    public SoapOutput(String name, String response) {
        this.name = name;
        this.response = response;
        setValue();
    }

    private void setValue() {
        try {
            SAXBuilder parser = new SAXBuilder();
            Document jdomDoc = parser.build(new ByteArrayInputStream(response.getBytes()));
            String expression = "//*[local-name()='" + name + "'][1]/text()";

            XPathExpression<Content> xpath = XPathFactory.instance().compile(expression, Filters.content());
            Content c = xpath.evaluateFirst(jdomDoc);
            if (c != null)
                value = c.getValue();

//			XPath xp = XPath.newInstance(expression);
//			Text t = (Text) xp.selectSingleNode(jdomDoc);
//			if (t != null)
//				value = t.getText();

        } catch (JDOMException | IOException e) {
            logger.error("Could not set value for output " + name, e);
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
