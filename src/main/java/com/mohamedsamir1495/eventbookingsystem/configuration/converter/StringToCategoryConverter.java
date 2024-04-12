package com.mohamedsamir1495.eventbookingsystem.configuration.converter;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    @Override
    public Category convert(String source) {
        return Category.valueOf(source.toUpperCase());
    }
}
