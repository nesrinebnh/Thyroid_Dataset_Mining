package DataStructure;

public class Point {
    private float x;
    private float y;

    public Point(float x, int y){
        this.x =x;
        this.y = (int)y;
    }
    public Point(float x, float y){
        this.x =x;
        this.y = y;
    }
public  float gety1(){
        return  this.y;
 }
    public float getX(){
    	return this.x;
    }
    public int getY(){
    	return (int) this.y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
