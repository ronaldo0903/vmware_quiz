package titi.quiz.oodesign;

public abstract class AbstractShape implements Drawable {
    @Override
    public final void draw() {
        System.out.println("Pre-processing before drawing...");
        drawShape();
        System.out.println("Post-processing after drawing...");
    }
    public abstract void drawShape();
}
