package org.example.fileUtil.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.fileUtil.Reader;
import org.example.question.multiplechoice.MultipleChoiceQuestion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class JsonReader<T> implements Reader<T> {
    private final Class<T> valueType;
    private final ObjectMapper mapper;

    public JsonReader(Class<T> valueType) {
        this.valueType = valueType;
        this.mapper = new ObjectMapper();
    }

    // 添加特定类型的 Deserializer
    public <D> void addDeserializer(Class<D> targetType, JsonDeserializer<? extends D> customDeserializer) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(targetType, customDeserializer);
        mapper.registerModule(module);
    }

    @Override
    public T read(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), valueType);
    }

    @Override
    public T read(File file) throws IOException {
        return mapper.readValue(file, valueType);
    }
}
