package dev.ricecx.augmentedsmp.utils;

public enum TimeUnits {
    MILLIS {
        public long toMillis(long value) {
            return value;
        }

        public long toSeconds(long value) {
            return value / 1000;
        }

        public long toMinutes(long value) {
            return value / 60000;
        }

        public long toHours(long value) {
            return value / 3600000;
        }

        public long toDays(long value) {
            return value / 86400000;
        }
    },

    SECONDS {
        public long toMillis(long value) {
            return value * 1000;
        }

        public long toSeconds(long value) {
            return value;
        }

        public long toMinutes(long value) {
            return value / 60;
        }

        public long toHours(long value) {
            return value / 3600;
        }

        public long toDays(long value) {
            return value / 86400;
        }
    },

    MINUTES {
        public long toMillis(long value) {
            return value * 60000;
        }

        public long toSeconds(long value) {
            return value * 60;
        }

        public long toMinutes(long value) {
            return value;
        }

        public long toHours(long value) {
            return value / 60;
        }

        public long toDays(long value) {
            return value / 1440;
        }
    },

    HOURS {
        public long toMillis(long value) {
            return value * 3600000;
        }

        public long toSeconds(long value) {
            return value * 3600;
        }

        public long toMinutes(long value) {
            return value * 60;
        }

        public long toHours(long value) {
            return value;
        }

        public long toDays(long value) {
            return value / 24;
        }

    },

    DAY {
        public long toMillis(long value) {
            return value * 86400000;
        }

        public long toSeconds(long value) {
            return value * 86400;
        }

        public long toMinutes(long value) {
            return value * 1440;
        }

        public long toHours(long value) {
            return value * 24;
        }

        public long toDays(long value) {
            return value;
        }
    },

    TICKS {
        public long toMillis(long value) {
            return value / 20;
        }

        public long toSeconds(long value) {
            return value * 20;
        }

        public long toMinutes(long value) {
            return value * 1200;
        }

        public long toHours(long value) {
            return value * 72000;
        }

        public long toDays(long value) {
            return value * 1728000;
        }
    };

    public long toMillis(long duration) {
        throw new AbstractMethodError();
    }


    public long toSeconds(long duration) {
        throw new AbstractMethodError();
    }

    public long toMinutes(long duration) {
        throw new AbstractMethodError();
    }


    public long toHours(long duration) {
        throw new AbstractMethodError();
    }


    public long toDays(long duration) {
        throw new AbstractMethodError();
    }

    public static long currentMillis() {
        return System.currentTimeMillis();
    }
}
