package rules.constrains;

import rules.exceptions.ConstraintsErrors;

import java.util.*;

public class Constraints implements ConstraintsErrors {
    /** The list of constraints */
    private List<Constraint> constraints = new LinkedList<>();
    /** The names of the constraints in the list */
    private Set<String> constraintsNames = new HashSet<>();

    //TODO: add privileges
    /**
     * Creates a list of constraints with the given constraints.
     * @param constraints The constraints to add
     */
    public Constraints(Constraint... constraints) {
        for (Constraint constraint : constraints) {
            if (constraint != null) {
                addConstraint(constraint);
            }
        }
    }

    /**
     * Adds a constraint to the list of constraints.
     * @param constraint The constraint to add
     * @return True if the constraint was added, false otherwise
     */
    public boolean addConstraint(Constraint constraint) {
        if (constraintsNames.add(constraint.getName())) {
            this.constraints.add(constraint); return true;
        } else {
           return false;
        }
    }

    /**
     * Deletes a constraint from the list of constraints.
     * @param constraint The constraint to delete
     * @return True if the constraint was deleted, false otherwise
     */
    public boolean deleteConstraint(Constraint constraint) {
        if (constraintsNames.remove(constraint.getName())) {
            constraints.remove(constraint); return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes a constraint from the list of constraints.
     * @param name The name of the constraint to delete
     * @return True if the constraint was deleted, false otherwise
     */
    public boolean deleteConstraint(String name) {
        final Constraint constraint = getConstraint(name);
        if (constraintsNames.remove(name)) { //TODO: see if this checks for the name or the object
            constraints.remove(constraint); return true;
        } else {
           return false;
        }
    }

    /**
     * Get the list of constraints.
     * @return The constraints
     */
    public List<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Get the set of constraints names.
     * @return The constraints all names of the constraints contained in the list
     */
    public Set<String> getConstraintsNames() {
        return constraintsNames;
    }

    /**
     * Returns the constraint at the given index.
     * @param index The index of the constraint to return
     * @return The constraint at the given index
     */
    public Constraint getConstraint(int index) {
        return constraints.get(index);
    }

    /**
     * Returns the constraint with the given name (case-insensitive). If the constraint is not found, it returns null.
     * @param name The name of the constraint to return
     * @return The constraint with the given name
     */
    public Constraint getConstraint(String name) {
        for (Constraint constraint : constraints) {
            if (constraint.getName().equalsIgnoreCase(name)) {
                return constraint;
            }
        }
        return null;
    }

}
