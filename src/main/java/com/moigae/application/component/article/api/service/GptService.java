package com.moigae.application.component.article.api.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GptService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String DALLE_API_URL = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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";

    public String getGpt(String subject) throws Exception {
        // Create message content
        JSONObject messageContent = new JSONObject();
        messageContent.put("role", "system");
        messageContent.put("content", "You are a helpful assistant.");

        JSONObject messageUser = new JSONObject();
        messageUser.put("role", "user");

        messageUser.put("content", "<"+subject+">라는 질문에 효과적으로 답변하기 위해 거쳐야 할 단계를 알려줘. 제시할 수 있는 단계는 3단계로 고정이야." +

                "단계만 간략하게 알려주고 세부 설명은 안해도 돼");

        JSONArray messages = new JSONArray();
        messages.add(messageContent);
        messages.add(messageUser);

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", messages);

        // Create connection
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Send request
        OutputStream os = conn.getOutputStream();
        os.write(body.toJSONString().getBytes());
        os.flush();
        os.close();

        // Get response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        //System.out.println("Response: " + response.toString());

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response.toString());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray choices = (JSONArray) jsonObject.get("choices");
        JSONObject choice = (JSONObject) choices.get(0);
        JSONObject message = (JSONObject) choice.get("message");
        String text = (String) message.get("content");
        //System.out.println(text);
        return text;
    }

    public List<String> listing(String input) {

        Pattern pattern = Pattern.compile("\\d+\\.\\s*[^\\d]*");
        Matcher matcher = pattern.matcher(input);
        List<String> listItems = new ArrayList<>();

        while (matcher.find()) {
            String fullMatch = matcher.group();
            int indexOfDot = fullMatch.indexOf('.');

            String textPart = fullMatch.substring(indexOfDot + 2).trim();
            listItems.add(textPart);
        }

        return listItems;
    }

    public String generateImage(String prompt) throws Exception {
        // Create request body
        JSONObject body = new JSONObject();
        body.put("prompt", prompt
                +"");
        body.put("n", 1);
        body.put("size", "512x512");

        // Create connection
        URL url = new URL(DALLE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Send request
        OutputStream os = conn.getOutputStream();
        os.write(body.toJSONString().getBytes());
        os.flush();
        os.close();

        // Get response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //System.out.println("Response: " + response.toString());

        // Parse the response
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
        JSONArray dataArray = (JSONArray) jsonResponse.get("data");
        if (dataArray.size() > 0) {
            JSONObject firstDataItem = (JSONObject) dataArray.get(0);
            String imageUrl = (String) firstDataItem.get("url");
            return imageUrl;
        }

        // In case the response does not contain the expected data
        return null;
    }

    public String getGptEnglish(String subject) throws Exception {
        // Create message content
        JSONObject messageContent = new JSONObject();
        messageContent.put("role", "system");
        messageContent.put("content", "You are a helpful assistant.");

        JSONObject messageUser = new JSONObject();
        messageUser.put("role", "user");
        messageUser.put("content", "<"+subject+">,<>안에 있는 주제에 어울리는 " +
                "photograph 생성을 위한 프롬프트 명령어를 영어로 반환해줘.");

        JSONArray messages = new JSONArray();
        messages.add(messageContent);
        messages.add(messageUser);

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", messages);

        // Create connection
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Send request
        OutputStream os = conn.getOutputStream();
        os.write(body.toJSONString().getBytes());
        os.flush();
        os.close();

        // Get response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        //System.out.println("Response: " + response.toString());

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response.toString());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray choices = (JSONArray) jsonObject.get("choices");
        JSONObject choice = (JSONObject) choices.get(0);
        JSONObject message = (JSONObject) choice.get("message");
        String text = (String) message.get("content");
        return text;
    }

    public MultipartFile downloadAsMultipartFile(String imageUrl, String fileName) throws IOException {
        // Download the image from URL as byte array
        byte[] imageBytes = StreamUtils.copyToByteArray(new URL(imageUrl).openStream());

        // Create MultipartFile object from byte array
        MultipartFile multipartFile = new InMemoryMultipartFile(imageBytes, fileName);

        return multipartFile;
    }

    private static class InMemoryMultipartFile implements MultipartFile {

        private final byte[] content;
        private final String name;
        private final String originalFilename;
        private final String contentType;

        public InMemoryMultipartFile(byte[] content, String name) {
            this.content = content;
            this.name = name;
            this.originalFilename = name;
            this.contentType = "image/png"; // Set the appropriate content type for your image
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            throw new IllegalStateException("transferTo() method is not supported for in-memory MultipartFile");
        }
    }

    public String getGptDetail(String subject) throws Exception {
        // Create message content
        JSONObject messageContent = new JSONObject();
        messageContent.put("role", "system");
        messageContent.put("content", "You are a helpful assistant.");

        JSONObject messageUser = new JSONObject();
        messageUser.put("role", "user");
        messageUser.put("content", "<"+subject+">에 대해 7문장 내외로 알려줘. " +
                "숫자같은거 붙이지말고 산문으로 써줘");

        JSONArray messages = new JSONArray();
        messages.add(messageContent);
        messages.add(messageUser);

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", messages);

        // Create connection
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Send request
        OutputStream os = conn.getOutputStream();
        os.write(body.toJSONString().getBytes());
        os.flush();
        os.close();

        // Get response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        //System.out.println("Response: " + response.toString());

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response.toString());
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray choices = (JSONArray) jsonObject.get("choices");
        JSONObject choice = (JSONObject) choices.get(0);
        JSONObject message = (JSONObject) choice.get("message");
        String text = (String) message.get("content");
        return text;
    }
}
