package org.example.question.multiplechoice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MultipleChoiceQuestionDeserializer extends JsonDeserializer<MultipleChoiceQuestion> {
    @Override
    public MultipleChoiceQuestion deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String scoreMode = node.get("scoreMode").asText();

        if ("fix".equals(scoreMode)) {
            return p.getCodec().treeToValue(node, MultipleChoiceQuestionFix.class);
        } else if ("partial".equals(scoreMode)) {
            return p.getCodec().treeToValue(node, MultipleChoiceQuestionPartial.class);
        } else if ("nothing".equals(scoreMode)) {
            return p.getCodec().treeToValue(node, MultipleChoiceQuestionNothing.class);
        } else {
            throw new IllegalArgumentException("Invalid scoreMode: " + scoreMode);
        }
    }
}
