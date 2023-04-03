package io.pliant.webstore.convertor;

import io.pliant.webstore.model.enums.MemberTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MemberTypeJpaConverter implements AttributeConverter<MemberTypeEnum, Character> {

    private static final Map<Character, MemberTypeEnum> memberTypeEnumMap;

    static {
        memberTypeEnumMap = new HashMap<>();
        memberTypeEnumMap.put('R', MemberTypeEnum.REGULAR);
        memberTypeEnumMap.put('V', MemberTypeEnum.VALUED);
        memberTypeEnumMap.put('☺', MemberTypeEnum.PREMIUM);
    }


    @Override
    public Character convertToDatabaseColumn(MemberTypeEnum memberTypeEnum) {
        if (memberTypeEnum == null) {
            return null;
        }

        for (Map.Entry<Character, MemberTypeEnum> entry : memberTypeEnumMap.entrySet()) {
            if (entry.getValue().toString().equalsIgnoreCase(memberTypeEnum.toString())){
                return entry.getKey();
            }
        }

        return null;
    }

    @Override
    public MemberTypeEnum convertToEntityAttribute(Character character) {
        if (character == null) {
            return null;
        }

        return memberTypeEnumMap.get(character);
    }

}
