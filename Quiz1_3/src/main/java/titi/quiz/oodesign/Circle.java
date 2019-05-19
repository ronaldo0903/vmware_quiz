package titi.quiz.oodesign;

public class Circle extends AbstractShape {
    private Point center;
    private int radius;
    public Circle(int x, int y, int r) {
        this.center = new Point(x, y);
        this.radius = r;
    }
    @Override
    public void drawShape() {
        System.out.println("Good job! You've drawn a circle at " + center.toString() + " with radius: " + this.getRadius() + " !");
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
