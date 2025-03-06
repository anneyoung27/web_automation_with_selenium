package com.automation.utils;

import net.datafaker.Faker;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class RandomDataGeneratorUtils {

    private static final Faker faker = new Faker();

    private static final List<String> TITLES = Arrays.asList("Mr", "Mrs");
    private static final List<String> COUNTRIES = Arrays.asList("India","United States","Canada","Australia","Israel","New Zealand","Singapore");
    private static final List<String> SUBJECTS = Arrays.asList("Product Inquiry", "Technical Support",
                                                               "Billing Issue", "Product Problem",
                                                               "Feedback & Suggestions", "Partnership Request");

    public static String getRandomData(RandomDataGeneratorEnum genData) {
        return switch (genData) {
            case TITLE -> TITLES.get(getRandomNumber(0, TITLES.size()));
            case NAME -> faker.name().fullName();
            case EMAIL -> faker.internet().emailAddress();
            case PASSWORD -> faker.internet().password();
            case DATE -> String.valueOf(faker.number().numberBetween(1, 32));
            case MONTH -> getRandomMonth();
            case YEAR -> String.valueOf(faker.number().numberBetween(1900, 2021));
            case FIRST_NAME -> faker.name().firstName();
            case LAST_NAME -> faker.name().lastName();
            case COMPANY -> faker.company().name();
            case ADDRESS -> faker.address().streetAddress();
            case ADDRESS2 -> faker.address().buildingNumber();
            case COUNTRY -> COUNTRIES.get(getRandomNumber(0, 7));
            case STATE -> faker.address().state();
            case CITY -> faker.address().cityName();
            case ZIPCODE -> faker.address().zipCode();
            case MOBILE_NUMBER -> faker.phoneNumber().cellPhone();
            case SUBJECT -> SUBJECTS.get(getRandomNumber(0, SUBJECTS.size()));
            case MESSAGE -> faker.lorem().sentence();
        };
    }

    public static String getRandomMonth(){
        int monthNumber = faker.number().numberBetween(1, 13);
        String month = Month.of(monthNumber).name();
        return month.charAt(0) + month.substring(1).toLowerCase();
    }

    public static int getRandomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }
}
