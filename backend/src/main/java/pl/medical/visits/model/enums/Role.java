package pl.medical.visits.model.enums;

public enum Role {
    PATIENT(Values.PATIENT),
    ADMIN(Values.DOCTOR),
    DOCTOR(Values.ADMIN);

    Role(String value) {}

    public static class Values {
        public static final String PATIENT = "PATIENT";
        public static final String DOCTOR = "DOCTOR";
        public static final String ADMIN = "ADMIN";
    }
}


