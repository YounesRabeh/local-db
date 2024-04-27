package rules.constraints;

import rules.exceptions.ConstraintsErrors;
import rules.exceptions.NotUniqueException;
import systemx.exceptions.DoNotExistsException;

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
     * @throws NullPointerException If any of the constraints is null
     */
    public Constraints(Constraint... constraints) throws NotUniqueException {
        for (Constraint constraint : constraints) {
            if (constraint != null) {
                addConstraint(constraint);
            } else throw new NullPointerException();
        }
    }

    /**
     * Creates a list of constraints with the given a constraints.
     * @param constraints The List of constraints to add
     * @throws NotUniqueException If any of the constraints is already in the list
     */
    public Constraints(List<Constraint> constraints) throws NotUniqueException {
        for (Constraint constraint : constraints) {
            if (constraint != null) {
                addConstraint(constraint);
            } else throw new NullPointerException();
        }
    }

    /**
     * Adds a constraint to the list of constraints.
     * @param constraint The constraint to add
     * @throws NotUniqueException If the constraint is already in the list
     */
    public void addConstraint(Constraint constraint) throws NotUniqueException {
        if (constraintsNames.add(constraint.getName())) {
            this.constraints.add(constraint);
        } else throw new NotUniqueException(constraint.getName());
    }

    /**
     * Deletes a constraint from the list of constraints.
     * @param constraint The constraint to delete
     * @throws NotUniqueException If the constraint is not in the list
     */
    public void deleteConstraint(Constraint constraint) throws NotUniqueException {
        if (constraintsNames.remove(constraint.getName())) {
            constraints.remove(constraint);
        } else throw new NotUniqueException(constraint.getName());
    }

    /**
     * Deletes a constraint from the list of constraints.
     * @param name The name of the constraint to delete
     * @throws DoNotExistsException If the constraint is not in the list
     */
    public void deleteConstraint(String name) throws DoNotExistsException {
        final Constraint constraint = getConstraint(name);
        if (constraintsNames.remove(name)) { //TODO: see if this checks for the name or the object
            constraints.remove(constraint);
        } else throw new DoNotExistsException(name);
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
