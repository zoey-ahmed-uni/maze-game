package io.maze.objects;

/** Represents an exam the player must complete to progress. */
public class Exam extends Object {
    private boolean completed;

    public Exam(String name) {
        super(name, "objects/exam.png");

        completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }
}
