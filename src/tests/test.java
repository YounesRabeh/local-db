package tests;

import java.util.HashMap;
import db.structure.DataBase;
import db.structure.Table;
import environment.WorkspaceInit;
import rules.notes.Constraint;
import rules.notes.Constraints;

import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;


public class test {
    public static void main(String[] args) {
        try {
            WorkspaceInit.setWorkspace("project1");

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

            //columns.addConstraint(new Constraint("height", Float.class));

            HashMap<String, Table> tables = db.getDbTables();

            //FIXME: correct the refactor
            final String TABLE_NAME = "table1";
            final String COLUMN_NAME = "weight";
            Table tb = tables.get(TABLE_NAME);

            tb.addColumn(
                    new Constraint("weight", Float.class),
                    new Constraint("address", String.class),
                    new Constraint("phone", String.class)
            );
            tb.addColumn(new Constraint("sport", String.class));

            //tables.get("table1").useOriginalColumnsRef();
//            tables.get("table3").overrideColumns(new Constraints(
//                    new Constraint("weight", Float.class),
//                    new Constraint("sport", String.class),
//                    new Constraint("phone", String.class)
//            ));



            for (Constraint column : tables.get(TABLE_NAME).getColumnsConstraintsList()) {
                System.out.println(column.getName());
            }
            //Tuple tuple1 = new Tuple(tb.getColumnsConstraints(), "Salvini", "48", "matteo@lega.it");
            //Tuple tuple = tb.getTuple(1);
            //tb.addTuple(tuple1);
            //tb.deleteTuple(1);

            //System.out.println(Arrays.toString(tuple.values()));

            if (tb.getColumnType(COLUMN_NAME).equals(Float.class)) {
                System.out.println("The first value is a float");
                //Float num = Float.valueOf(tuple.getValue(COLUMN_NAME));
                //System.out.println(num);
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
