package pl.medical.visits.model.enums;

public enum Role {
    PATIENT(Values.PATIENT),
    ADMIN(Values.DOCTOR),
    DOCTOR(Values.ADMIN);

//    private Role (String val) {
//        // force equality between name of enum instance, and value of constant
//        if (!this.name().equals(val))
//            throw new IllegalArgumentException("Incorrect use of ELanguage");
//    }

    Role(String patient) {
    }

    public static class Values {
        public static final String PATIENT = "PATIENT";
        public static final String DOCTOR = "DOCTOR";
        public static final String ADMIN = "ADMIN";
    }
}


