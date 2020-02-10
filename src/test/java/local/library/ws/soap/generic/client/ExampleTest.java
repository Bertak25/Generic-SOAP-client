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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import local.library.ws.soap.generic.client.example.HelloImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    @BeforeEach
    public void setUp() throws Exception {
        // start the web service
        HelloImpl implementor = new HelloImpl();
        ServerStarter.startSoapServer(9001, "helloWorld", implementor);

        ServerStarter.startWebServer(9002);
    }

    @Disabled
    @Test
    public void testExample() throws IOException {

        // create service from a wsdl
        SoapService service = new SoapService("http://localhost:9001/helloWorld?wsdl");

        // iterate through all operations and inputs
        for (SoapOperation op : service.getOperations()) {
            System.out.println("Operation:");
            System.out.println(op.getName());

            System.out.println("Operation Documentation:");
            System.out.println(op.getDocumentation());

            System.out.println("Default request message:");
            System.out.println(op.getRequest());

            System.out.println("Input fields:");
            for (SoapInput in : op.getInputs()) {
                System.out.println(in.getName());
                System.out.println(in.getDocumentation());
                System.out.println(in.getDefaultValue());
                System.out.println(in.isMultiValued());
            }
        }

        // set an input value for a specific operation
        SoapOperation sayHi = service.getOperation("sayHi");
        sayHi.getInput("text").setValue("Santa Clause");

        // get the results
        List<SoapOutput> outs = new ArrayList<>(sayHi.execute("admin", "admin").values());
        String outName = outs.get(0).getName();
        String outValue = outs.get(0).getValue();
        System.out.println("Output: ");
        System.out.println(outName + " : " + outValue);


        // get the soap request and response messages
        System.out.println(sayHi.getRequest());
        System.out.println(sayHi.getResponse());

    }

    @Disabled
    @Test
    public void testImpactWsdl() throws IOException {
        URL impactWsdl = new URL("http://localhost:9002/IMPACTAbbyyFre10OcrProxy.wsdl");
        SoapService service = new SoapService(impactWsdl);

        for (SoapOperation op : service.getOperations()) {
            System.out.println("Operation:");
            System.out.println(op.getName());

            System.out.println("Operation Documentation:");
            System.out.println(op.getDocumentation());

            System.out.println("Default request message:");
            System.out.println(op.getDefaultRequest());

            System.out.println("Input fields:");
            for (SoapInput in : op.getInputs()) {
                System.out.println(in.getName());
                System.out.println(in.getDocumentation());
                System.out.println(in.getDefaultValue());
                System.out.println(in.isMultiValued());
            }
        }

    }

    @Disabled
    @Test
    public void testWrapper03Wsdl() throws IOException {
        URL impactWsdl = new URL("http://localhost:9002/IMPACTFineReader_wrapper0.3.wsdl");
        SoapService service = new SoapService(impactWsdl);

        for (SoapOperation op : service.getOperations()) {
            System.out.println("Operation:");
            System.out.println(op.getName());

            System.out.println("Operation Documentation:");
            System.out.println(op.getDocumentation());

            System.out.println("Default request message:");
            System.out.println(op.getDefaultRequest());

            System.out.println("Input fields:");
            for (SoapInput in : op.getInputs()) {
                System.out.println(in.getName());
                System.out.println(in.getDocumentation());
                System.out.println(in.getDefaultValue());
                System.out.println(in.isMultiValued());
            }
        }

    }

    @Test
    public void testImageMagick() throws IOException {
        URL impactWsdl = new URL("http://localhost:9002/IMPACTImageMagickProxy.wsdl");
        SoapService service = new SoapService(impactWsdl);

        for (SoapOperation op : service.getOperations()) {
            System.out.println("Operation:");
            System.out.println(op.getName());

            System.out.println("Operation Documentation:");
            System.out.println(op.getDocumentation());

            System.out.println("Default request message:");
            System.out.println(op.getDefaultRequest());

            System.out.println("Input fields:");
            for (SoapInput in : op.getInputs()) {
                System.out.println(in.getName());
                System.out.println(in.getDocumentation());
                System.out.println(in.getDefaultValue());
                System.out.println(in.isMultiValued());
                System.out.println("--- possible values ---");
                for (String s : in.getPossibleValues())
                    System.out.println(s);
                System.out.println("--- --------------- ---");
            }
        }

    }


    @AfterEach
    public void tearDown() throws Exception {
        ServerStarter.stopAll();
    }

}
