package titi.quiz.oodesign;

public class DrawingDemo {
    public static void main(String[] args) {
        Drawable circle = new Circle(5,5,3);
        Drawable rect = new Rectangle();
        circle.draw();
        rect.draw();
    }
}
