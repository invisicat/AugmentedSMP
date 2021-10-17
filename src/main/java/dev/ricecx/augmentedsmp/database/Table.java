package dev.ricecx.augmentedsmp.database;

public interface Table<T> {

    default SQLUtils getSQL() {
        return DatabaseManager.getSQLUtils();
    }
}
