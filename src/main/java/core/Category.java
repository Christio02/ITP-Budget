package core;

public class Category {

    private final String categoryName;
    private int amount;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.amount = 0;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return getCategoryName();
    }
}
