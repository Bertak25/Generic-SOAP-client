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

import javax.xml.ws.Endpoint;

import local.library.ws.soap.generic.client.example.HelloImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SoapServiceTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

    private static Endpoint ep;

    private static void startService() {
        HelloImpl implementor = new HelloImpl();
        String address = "http://localhost:9000/helloWorld";
        ep = Endpoint.publish(address, implementor);
    }

    @Test
    public void test() throws IOException {
        startService();
        SoapService service = new SoapService("http://localhost:9000/helloWorld?wsdl");

        for (SoapOperation op : service.getOperations()) {
            System.out.println();
            System.out.println(op.getName());
            for (SoapInput in : op.getInputs()) {
                System.out.println(in.getName());
                System.out.println(in.getDefaultValue());
            }
        }

        ep.stop();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

}
