package tests;

import java.util.List;

import db.structure.DataBase;
import db.structure.Table;
import environment.WorkspaceInit;
import rules.constrains.Constraint;
import rules.constrains.Constraints;

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

            List<Table> tables = db.getDbTables();
            tables.getFirst().addColumn(
                    new Constraint("weight", Float.class),
                    new Constraint("address", String.class),
                    new Constraint("phone", String.class)
            );
            tables.getFirst().useOriginalColumnsRef();
            tables.getFirst().overrideColumns(new Constraints(
                    new Constraint("weight", Float.class),
                    new Constraint("sport", String.class),
                    new Constraint("phone", String.class)
            ));
            for (Constraint column : tables.getFirst().getColumnsConstraints()) {
                System.out.println(column.getName());
            }

        } catch (NotUniqueException e ){
            System.out.println(e.getMessage());
        } catch (FailedToCreateException e) {
            throw new RuntimeException(e);
        } catch (DoNotExistsException e) {
            throw new RuntimeException(e);
        }
    }
}
