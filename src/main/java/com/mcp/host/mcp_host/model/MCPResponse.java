package com.mcp.host.mcp_host.model;

import java.util.Map;

public class MCPResponse {

    //data members
    public String status;
    public String message;


    // parameterised constructor
    public MCPResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
