package fr.gwombat.cmstest.converters;

import fr.gwombat.cmstest.domain.PhoneNumber;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 16/04/2018
 */
public class PhoneNumberConverter implements Converter<PhoneNumber> {

    @Override
    public PhoneNumber convert(Map<String, String> cmsResults) {
        final PhoneNumber phoneNumber = new PhoneNumber();

        final String number = cmsResults.get("number");
        phoneNumber.setNumber(formatPhoneNumber(number));
        phoneNumber.setName(cmsResults.get("name"));
        return phoneNumber;
    }

    private static String formatPhoneNumber(String number) {
        final String phonePart1 = number.substring(0, 2);
        final String phonePart2 = number.substring(2, 4);
        final String phonePart3 = number.substring(4, 6);
        final String phonePart4 = number.substring(6, 8);
        final String phonePart5 = number.substring(8);

        return MessageFormat.format("{0} {1} {2} {3} {4}", phonePart1, phonePart2, phonePart3, phonePart4, phonePart5);
    }
}
