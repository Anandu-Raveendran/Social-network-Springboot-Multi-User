package artshop.utils;

public class Constants {
    public static final Long MONTH_IN_MILLIS = 31 * 24 * 3600 * 1000L;
    public static final int VERIFICATION_CODE_VALIDITY_PERIOD_IN_MONTHS = 1;

    public enum PostStatus {
        ACTIVE(1), INACTIVE(0), DELETED(2);
        private int value;

        private PostStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum VerificationCodeMode {
        EMAIL(0), PHONE(1);
        private int value;

        private VerificationCodeMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum UserStatus {
        INACTIVE(0), REGISTERED(1),ACTIVE(2), PAID(3), EXPIRED(4), BLOCKED(5);
        private int value;

        private UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
