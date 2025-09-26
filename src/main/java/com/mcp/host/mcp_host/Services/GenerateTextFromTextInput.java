
package com.mcp.host.mcp_host.Services;


import com.google.genai.Client;
import com.google.genai.types.ClientOptions;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class GenerateTextFromTextInput {

    public static String inputToJSON(String input) {
        String apiKey = "";

        String toolList = "MediaTool, ApplicationLauncher, FileManager, CommandPrompt, NetworkingCommand";

        String promptInstruction =  "You are a CLI command planner. Your task is to convert the user's natural language request " +
                "into a valid JSON command. The 'tool' field MUST be one of the following: [" + toolList + "]. " +
                "IMPORTANT PATH RULE: All file paths, regardless of the target operating system, MUST use the forward slash (/) as the directory separator. DO NOT use the backslash (\\) character. " +
                "IMPORTANT ACTION RULE : All action should be in camel case with no space, hyphens or underscores"+
                "For file operations, assume the user's Desktop path is 'Desktop'. " +
                "The output JSON MUST strictly follow the example schema below. " +
                "User Request: " + input;

        String jsonSchemaExample =
                "Example JSON output:\n" +
                        "{\n" +
                        "\"tool\":\"string\",\n" +
                        "\"action\":\"string\",\n" +
                        "\"parameters\":{\n" +
                        "    \"path\":\"string\",\n" +
                        "    \"fileName\":\"string\"\n" +
                        "}\n" +
                        "}\n" +
                        "Respond ONLY with the final JSON object, no extra text, no markdown block (e.g., ```json).";

        String prompt = promptInstruction +"\n\n"+jsonSchemaExample;
        Client client = Client.builder()
                                .apiKey(apiKey)
                                .build();

        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        config);
        System.out.println(response.text());
        return response.text();
    }
}
