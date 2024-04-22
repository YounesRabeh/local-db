package tests;

import db.objects.DbEntry;
import db.objects.Tuple;
import db.structure.DataBase;
import db.structure.Table;
import rules.constrains.Constraint;
import rules.constrains.Constraints;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class test {
    public static void main(String[] args) {
        Constraints columns = new Constraints(
                new Constraint("name", String.class),
                new Constraint("age", Integer.class),
                new Constraint("email", String.class)
        );

        DataBase db = new DataBase("db",
                new Table("table", columns)
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
                new Constraint("address", String.class),
                new Constraint("phone", String.class)
        ));
        for (Constraint column : tables.getFirst().getColumnsConstraints()) {
            System.out.println(column.getName());
        }
        //System.out.println(tables.getFirst().getColumnsConstraints());

    }
    

}
