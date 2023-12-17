package context;

public class Enum {
    public enum CustomerStaus {
        All(0, "Tất cả"),
        Active(1,"Hoạt động"),
        Lock(2, "Bị khóa"),
        Deleted(3, "Đã xóa"),
        ;
        private final int value;
        private final String label;
        CustomerStaus(int value, String label) {
            this.value = value;
            this.label = label;
        }
        public int getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }
}
