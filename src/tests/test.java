package tests;

import java.util.HashMap;

import db.objects.Tuple;
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

            final String TABLE_NAME = "table1";
            final String COLUMN_NAME = "weight";
            Table tb = tables.get(TABLE_NAME);

//            tb.addColumn(
//                    new Constraint("weight", Float.class),
//                    new Constraint("address", String.class),
//                    new Constraint("phone", String.class)
//            );
            //tb.addColumn(new Constraint("sport", String.class));
            String[] values = {"Salvini", "48", "matteo.lega.it", "Via Roma 1", "basketball", "0987654321"};

            //FIXME: adding a smaller tuple than the columns constraints, it should add the missing ones
            // and if it is bigger, it should crop the extra ones
            //tb.overrideColumns(columns);
            tb.addTuple(new Tuple(tb.getColumnsConstraints(), values));
            //tb.addColumn(new Constraint("height", String.class));
            tb.addTuple(new Tuple(tb.getColumnsConstraints(), "Pepe", "25", "pepe.calabrese.it", "1.75", "Via Napoli 2", "0987654321"));
            //tb.addColumn(new Constraint("sport", String.class));
            //tb.deleteColumn("age");

            //tb.deleteTuple(7);

//            Tuple tuple = tb.getTuple(1);
//            System.out.println(tuple.toString());
            //FIXME: fix the refactor in TableTools, so if the columns are less than the new ones,
            // it adds the missing ones, and if they are more, it crops the extra ones

//            tb.overrideColumns(new Constraints(
//                    new Constraint("weight", Float.class),
//                    new Constraint("sport", String.class),
//                    new Constraint("phone", String.class)
//            ));
            //tb.addTuple(new Tuple(tb.getColumnsConstraints(), values));

            //tables.get("table1").useOriginalColumnsRef();
//            tables.get("table3").overrideColumns(new Constraints(
//                    new Constraint("weight", Float.class),
//                    new Constraint("sport", String.class),
//                    new Constraint("phone", String.class)
//            ));



//            for (Constraint column : tables.get(TABLE_NAME).getColumnsConstraintsList()) {
//                System.out.println(column.getName());
//            }
            //Tuple tuple1 = new Tuple(tb.getColumnsConstraints(), "Salvini", "48", "matteo@lega.it");
            //Tuple tuple = tb.getTuple(1);
            //tb.addTuple(tuple1);
            //tb.deleteTuple(1);

            //System.out.println(Arrays.toString(tuple.values()));

//            if (tb.getColumnType(COLUMN_NAME).equals(Float.class)) {
//                System.out.println("The first value is a float");
//                //Float num = Float.valueOf(tuple.getValue(COLUMN_NAME));
//                //System.out.println(num);
//            }

        } catch (NotUniqueException e ){
            throw new RuntimeException(e);

        } catch (FailedToCreateException e) {
            throw new RuntimeException(e);
        } catch (DoNotExistsException e) {
            throw new RuntimeException(e);
        }
    }
}
