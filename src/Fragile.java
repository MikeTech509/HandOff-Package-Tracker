public interface Fragile {
    default void showFragileWarning() {
        System.out.println("⚠️  FRAGILE — HANDLE WITH CARE");
    }
}
