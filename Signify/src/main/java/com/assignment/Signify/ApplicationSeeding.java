package com.assignment.Signify;

import com.assignment.Signify.model.ReviewEntity;
import com.assignment.Signify.model.ReviewRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ApplicationSeeding implements CommandLineRunner {

    private final ReviewRepository reviewRepository;
    private final String filePath = "classpath:/Data/alexa.json";
    private ResourceLoader resourceLoader;
    private List<ReviewEntity> extractedReview;

    @Override
    public void run(String... args) throws Exception {


        List<JsonNode> JsonList = JsontoList();

        for(JsonNode node :JsonList){
            ReviewEntity reviewEntity = getReviewEntity(node) ;
            extractedReview.add(reviewEntity);
        }
        //System.out.println(extractedReview.size());
        reviewRepository.saveAll(extractedReview);

    }

    private ReviewEntity getReviewEntity(JsonNode jsonNode) {
        ReviewEntity reviewEntity =  new ReviewEntity();
        reviewEntity.setReview(jsonNode.get("review").asText());
        reviewEntity.setAuthor(jsonNode.get("author").asText());
        reviewEntity.setReviewSource(jsonNode.get("review_source").asText());
        reviewEntity.setRating(jsonNode.get("rating").asInt());
        reviewEntity.setTitle(jsonNode.get("title").asText());
        reviewEntity.setProductName(jsonNode.get("product_name").asText());
        reviewEntity.setReviewedDate(jsonNode.get("reviewed_date").asText());


        return reviewEntity;
    }


    private List<JsonNode> JsontoList() {
        List<JsonNode> JsonList = new ArrayList<>();
        try {

            Resource resource = resourceLoader.getResource(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            ObjectMapper objectMapper = new ObjectMapper();

            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                JsonNode jsonNode = objectMapper.readTree(line);

                //System.out.println("Parsed JSON object: " + jsonNode.toString());
                JsonList.add(jsonNode);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonList;
    }



}
