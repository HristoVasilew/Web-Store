package io.pliant.webstore.convertor;

import io.pliant.webstore.model.Barcode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BarcodeJpaConverter implements AttributeConverter<Barcode, String> {

    @Override
    public String convertToDatabaseColumn(Barcode barcode) {
        if (barcode == null) {
            return null;
        }
        return barcode.getValue();
    }

    @Override
    public Barcode convertToEntityAttribute(String barcode) {
        if (barcode == null) {
            return null;
        }

        return new Barcode(barcode);
    }
}
