package warehouse.erpclient.warehouse.model;

import warehouse.erpclient.utils.exception.EnumConverterException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class QuantityUnitConverter implements AttributeConverter<QuantityUnit, String> {

    @Override
    public String convertToDatabaseColumn(QuantityUnit quantityUnit) {
        if (quantityUnit == null) return null;
        return quantityUnit.getUnitSymbol();
    }

    @Override
    public QuantityUnit convertToEntityAttribute(String unitSymbol) {
        if (unitSymbol == null) return null;
        return Arrays.stream(QuantityUnit.values())
                .filter(quantityUnit -> quantityUnit.getUnitSymbol().equals(unitSymbol))
                .findFirst()
                .orElseThrow(() -> new EnumConverterException("Incorrect quantity unit value!"));
    }

}
