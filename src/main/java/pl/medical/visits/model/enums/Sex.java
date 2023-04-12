package pl.medical.visits.model.enums;

public enum Sex {
    FEMALE(Sexes.FEMALE),
    MALE(Sexes.MALE);

    Sex(String sex) {}

    public static class Sexes {
        public static final String FEMALE = "Female";
        public static final String MALE = "Male";
    }
}
