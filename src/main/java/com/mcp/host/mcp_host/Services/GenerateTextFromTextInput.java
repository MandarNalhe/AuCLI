
package com.mcp.host.mcp_host.Services;


import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class GenerateTextFromTextInput {
    @Value("${apiKey}")
    private String apiKey;

    public String inputToJSON(String input) {


        String toolList = "MediaTool, ApplicationLauncher, FileManager, CommandPrompt, NetworkingCommands";

        String actionList =
                "MediaTool: openImage, playMedia; " +
                        "ApplicationLauncher: openApplication; " +
                        "FileManager: copyFile, createFile, createFolder, deleteFile, listFiles, moveFile, openFile; " +
                        "CommandPrompt: Independent, Dependent; " +
                        "NetworkingCommands: network;";

        String promptInstruction =
                "You are a CLI command planner. Your task is to convert the user's natural language request " +
                        "into a valid JSON command.\n" +
                        "The 'tool' field MUST be one of the following: [" + toolList + "].\n" +
                        "The 'action' field MUST be one of the following valid actions per tool:\n" +
                        actionList + "\n" +
                        "For action CommandPrompt limit parameters to path and command in dependent and only to command in independent"+
                        "IMPORTANT PATH RULE: All file paths, regardless of the target operating system, MUST use the forward slash (/) as the directory separator. DO NOT use the backslash (\\) character.\n" +
                        "IMPORTANT ACTION RULE: All actions should be camelCase with no spaces, hyphens, or underscores.\n" +
                        "For file operations, assume the user's Desktop path is 'Desktop'.\n" +
                        "The output JSON MUST strictly follow the example schema below.\n" +
                        "always make sure to have the default drive as C drive with users as hp fo FileManager operations \n"+
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
