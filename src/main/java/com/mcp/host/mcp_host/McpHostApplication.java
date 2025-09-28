package com.mcp.host.mcp_host;

import com.google.gson.Gson;
import com.mcp.host.mcp_host.Services.GenerateTextFromTextInput;
import com.mcp.host.mcp_host.core.MCPHost;
import com.mcp.host.mcp_host.model.MCPRequest;
import com.mcp.host.mcp_host.model.MCPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpHostApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(McpHostApplication.class, args);
	}
	@Autowired
	GenerateTextFromTextInput generateTextFromTextInput;
	@Override
	public void run(String... args) throws Exception{

		String jsonInput = generateTextFromTextInput.inputToJSON("delete a file named Hello.txt in Downloads which in manda in Users which is in C drive");

		Gson gson=new Gson();
		MCPRequest request=gson.fromJson(jsonInput, MCPRequest.class);
		MCPHost host=new MCPHost();
		MCPResponse response=host.handle(request);
		System.out.println("Status:"+response.status);
		System.out.println("Message:"+response.message);
	}
}
