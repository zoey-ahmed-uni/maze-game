package io.maze.objects;

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
