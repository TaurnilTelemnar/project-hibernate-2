package converter;

import entity.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        if (isNull(dbData) || dbData.isEmpty()) return null;

        return Arrays.stream(Rating.values())
                .filter(r -> r.getValue().equals(dbData))
                .findFirst()
                .orElse(null);
    }
}
