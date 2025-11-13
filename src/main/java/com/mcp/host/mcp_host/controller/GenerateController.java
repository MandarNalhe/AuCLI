package com.mcp.host.mcp_host.controller;

import com.google.gson.Gson;
import com.mcp.host.mcp_host.Services.GenerateTextFromTextInput;
import com.mcp.host.mcp_host.core.MCPHost;
import com.mcp.host.mcp_host.model.MCPRequest;
import com.mcp.host.mcp_host.model.MCPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GenerateController {

    @Autowired
    private GenerateTextFromTextInput generateService;

    @PostMapping("/generate")
    public MCPResponse generate(@RequestBody InputRequest request) {
        try {
            // Step 1: Convert natural language to JSON
            String jsonInput = generateService.inputToJSON(request.getPrompt());
            System.out.println(" Received prompt: " + request.getPrompt());
            System.out.println(" Generated JSON: " + jsonInput);

            // Step 2: Parse JSON into MCPRequest
            Gson gson = new Gson();
            MCPRequest mcpRequest = gson.fromJson(jsonInput, MCPRequest.class);

            // Step 3: Execute via MCPHost
            MCPHost host = new MCPHost();
            MCPResponse response = host.handle(mcpRequest);

            // Step 4: Return structured response to frontend
            System.out.println(" Execution Complete → " + response.status + ": " + response.message);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            MCPResponse error = new MCPResponse();
            error.status = "error";
            error.message = "Error while executing: " + e.getMessage();
            return error;
        }
    }

    // DTO for request body
    public static class InputRequest {
        private String prompt;

        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }
}
