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

import java.io.IOException;

import local.library.ws.soap.generic.client.example.HelloImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SoapInputTest {

	private static WsdlDocument wsdlDoc;
	private static WsdlDocument wsdlDocImpact;	
	
	@BeforeAll
	public static void setUp() throws Exception {
		HelloImpl implementor = new HelloImpl();
		
		ServerStarter.startSoapServer(9001, "helloWorld", implementor);
		wsdlDoc = new WsdlDocument("http://localhost:9001/helloWorld?wsdl");
		
		ServerStarter.startWebServer(9002);
		wsdlDocImpact = new WsdlDocument("http://localhost:9002/IMPACTAbbyyFre10OcrProxy.wsdl");
	}

	@Test
	public void testGetDocumentation() throws IOException {
		SoapInput input = new SoapInput("inputTextType", wsdlDocImpact, "ocrImageFileByUrl");
		assertEquals("Normal/Typewriter/Gothic/ToBeDetected", input.getDocumentation());
		
		input = new SoapInput("inputUrl", wsdlDocImpact, "ocrImageFileByUrl");
		assertEquals("URL reference to image file", input.getDocumentation());
	}
	
	@Test
	public void testValues() throws IOException {
		
		SoapInput input = new SoapInput("text", wsdlDoc, "sayHi");
		assertNotNull(input.getValues());

		input.setValue("Guenther");
		assertTrue(input.getValue().equals("Guenther"));
		
		input.addValue("Richard");
		assertTrue(input.getValues().size() == 2);
		
		input.clearValues();
		assertTrue(input.getValues().size() == 0);
	}

	@Test
	public void testGetDefaultValue() throws IOException {
		
		SoapInput input = new SoapInput("text", wsdlDoc, "sayHi");
		String defaultVal = input.getDefaultValue();
		assertTrue(defaultVal.equals("Bernhard"));
		
	}
	
	public void testIsBinary() throws IOException{
		
		SoapInput input = new SoapInput("bytes", wsdlDoc, "sayHi");
		assertTrue(input.isBinary());
		
	}
	
	@AfterAll
	public static void tearDown() throws Exception {
		ServerStarter.stopAll();
	}

}
