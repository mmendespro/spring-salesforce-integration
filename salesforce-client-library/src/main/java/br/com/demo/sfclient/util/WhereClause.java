package br.com.demo.sfclient.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representation of a constructed SQL query where clause
 */
public final class WhereClause {

    public final String selection;
    public final String[] selectionArgs;

    private WhereClause(String selection, String[] selectionArgs) {
        this.selection = selection;
        this.selectionArgs = selectionArgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhereClause that = (WhereClause) o;

        return !(selection != null ? !selection.equals(that.selection) : that.selection != null)
                && Arrays.equals(selectionArgs, that.selectionArgs);

    }

    @Override
    public int hashCode() {
        int result = selection != null ? selection.hashCode() : 0;
        result = 31 * result + (selectionArgs != null ? Arrays.hashCode(selectionArgs) : 0);
        return result;
    }

    @Override
    public String toString() {
        return WhereClause.class.getSimpleName() + "{" +
                "selection='" + selection + '\'' +
                ", selectionArgs=" + Arrays.toString(selectionArgs) +
                '}';
    }

    /**
     * Common SQL operators. For convenience, consider implementing this interface if creating a
     * lot of queries.
     */
    public interface Operator {
        public String EQUALS = "=";
        public String NOT_EQUALS = "!=";
        public String GREATER_THAN = ">";
        public String LESS_THAN = "<";
        public String GREATER_THAN_EQUALS = ">=";
        public String LESS_THAN_EQUALS = "<=";
        public String LIKE = " LIKE ";
        public String IS = " IS ";
        public String IS_NOT = " IS NOT ";
    }

    public static class Builder {

        private static final String AND = " AND ";
        private static final String OR = " OR ";

        private final StringBuilder stringBuilder = new StringBuilder();
        private final List<String> args = new ArrayList<>();
        private String nextOperator;

        public Builder where(String column, String operand, String arg) {
            setNextOperatorIfNeeded();
            stringBuilder.append(column).append(operand).append('?');
            args.add(arg);
            nextOperator = null;

            return this;
        }

        public Builder where(String column, String operand, boolean arg) {
            return where(column, operand, arg ? "1" : "0");
        }

        public Builder where(String column, String operand, int arg) {
            return where(column, operand, Integer.toString(arg));
        }

        public Builder where(String column, String operand, long arg) {
            return where(column, operand, Long.toString(arg));
        }

        public Builder where(String column, String operand, float arg) {
            return where(column, operand, Float.toString(arg));
        }

        public Builder where(String column, String operand, double arg) {
            return where(column, operand, Double.toString(arg));
        }

        public Builder where(WhereClause whereClause) {

            if (whereClause.selectionArgs.length > 0) {
                setNextOperatorIfNeeded();
                stringBuilder.append('(').append(whereClause.selection).append(')');
                args.addAll(Arrays.asList(whereClause.selectionArgs));
            }

            nextOperator = null;
            return this;
        }

        /**
         * Joins two statements with an {@code AND} operator. This is also the implicit behavior.
         */
        public Builder and() {
            nextOperator = AND;
            return this;
        }

        public Builder or() {
            nextOperator = OR;
            return this;
        }

        /**
         * Ensures that multiple {@code where} statements can be joined safely. Defaults to using
         * {@code AND}.
         */
        private void setNextOperatorIfNeeded() {
            if (stringBuilder.length() == 0) {
                return;
            }

            if (nextOperator == null) {
                stringBuilder.append(AND);
            } else {
                stringBuilder.append(nextOperator);
                nextOperator = null;
            }
        }

        public WhereClause build() {
            String[] arguments = args.toArray(new String[args.size()]);
            return new WhereClause(stringBuilder.toString(), arguments);
        }
    }
}