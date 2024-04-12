package com.mohamedsamir1495.eventbookingsystem.configuration.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;

import java.io.IOException;

public class CategoryToStringConverter extends JsonSerializer<Category> {

	@Override
	public void serialize(Category category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(category.toString());
	}

}
