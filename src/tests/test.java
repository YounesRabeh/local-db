package tests;

import java.util.Arrays;
import java.util.HashMap;

import db.objects.Tuple;
import db.structure.DataBase;
import db.structure.Table;
import environment.WorkspaceInit;
import rules.constraints.Constraint;
import rules.constraints.Constraints;

import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;


public class test {
    public static void main(String[] args) {
        try {
            WorkspaceInit.getInstance();
            Constraints columns = new Constraints(
                    new Constraint("name", String.class),
                    new Constraint("age", Integer.class),
                    new Constraint("email", String.class)
            );

            DataBase db = new DataBase("db",
                    new Table("table1", columns),
                    new Table("table2", columns),
                    new Table("table3", columns)
            );

            columns.addConstraint(new Constraint("height", Float.class));

            HashMap<String, Table> tables = db.getDbTables();
            tables.get("table1").addColumn(
                    new Constraint("weight", Float.class),
                    new Constraint("address", String.class),
                    new Constraint("phone", String.class)
            );
            tables.get("table1").useOriginalColumnsRef();
            tables.get("table3").overrideColumns(new Constraints(
                    new Constraint("weight", Float.class),
                    new Constraint("sport", String.class),
                    new Constraint("phone", String.class)
            ));

            final String TABLE_NAME = "table3";
            final String COLUMN_NAME = "weigt";

            Table tb = tables.get(TABLE_NAME);
            for (Constraint column : tables.get(TABLE_NAME).getColumnsConstraints()) {
                System.out.println(column.getName());
            }
            Tuple tuple = tb.getTuple(1);

            System.out.println(Arrays.toString(tuple.values()));

            if (tb.getColumnType(COLUMN_NAME).equals(Float.class)) {
                System.out.println("The first value is a float");
                Float num = Float.valueOf(tuple.getValue(COLUMN_NAME));
                System.out.println(num);
            }

        } catch (NotUniqueException e ){
            System.out.println(e.getMessage());
        } catch (FailedToCreateException e) {
            System.out.println(e.getMessage());
        } catch (DoNotExistsException e) {
            System.out.println(e.getMessage());
        }
    }
}
