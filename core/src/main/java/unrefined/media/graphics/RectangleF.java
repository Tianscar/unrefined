package unrefined.media.graphics;

import unrefined.io.BinaryInput;
import unrefined.io.BinaryOutput;
import unrefined.io.BundleInput;
import unrefined.io.BundleOutput;
import unrefined.io.Savable;
import unrefined.math.FastMath;

import java.io.IOException;

public class RectangleF implements Savable {

    private float x;
    private float y;
    private float width;
    private float height;

    public RectangleF() {
    }

    public RectangleF(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectangleF(RectangleF rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void getPosition(PointF position) {
        position.setX(x);
        position.setY(y);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void getSize(DimensionF size) {
        size.setDimension(width, height);
    }

    public void setRectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setRectangle(RectangleF rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    public void setRectangle(Rectangle rectangle) {
        this.x = rectangle.getX();
        this.y = rectangle.getY();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBounds(float x, float y, float width, float height) {
        setRectangle(x, y, width, height);
    }

    public void getBounds(RectangleF bounds) {
        bounds.setRectangle(this);
    }

    public void getBounds(Rectangle bounds) {
        float left = FastMath.floor(x);
        float top = FastMath.floor(y);
        float right = FastMath.ceil(x + width);
        float bottom = FastMath.ceil(y + height);
        bounds.setRectangle((int) left, (int) top, (int) (right - left), (int) (bottom - top));
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    public boolean contains(float x, float y, float width, float height) {
        return x >= this.x && x + width < this.x + this.width && y >= this.y && y + height < this.y + this.height;
    }

    public boolean contains(int x, int y, int width, int height) {
        return x >= this.x && x + width < this.x + this.width && y >= this.y && y + height < this.y + this.height;
    }

    public boolean intersects(float x, float y, float width, float height) {
        return x >= this.x && x + width < this.x + this.width && y >= this.y && y + height < this.y + this.height;
    }

    public boolean intersects(int x, int y, int width, int height) {
        return x >= this.x && x + width < this.x + this.width && y >= this.y && y + height < this.y + this.height;
    }

    @Override
    public void writePortable(BinaryOutput out) throws IOException {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(width);
        out.writeFloat(height);
    }

    @Override
    public void readPortable(BinaryInput in) throws IOException {
        x = in.readFloat();
        y = in.readFloat();
        width = in.readFloat();
        height = in.readFloat();
    }

    @Override
    public void to(Object dst) {
        ((RectangleF) dst).setRectangle(this);
    }

    @Override
    public void from(Object src) {
        setRectangle((RectangleF) src);
    }

    @Override
    public void swap(Object o) {
        RectangleF that = (RectangleF) o;
        float x = that.x;
        float y = that.y;
        float width = that.width;
        float height = that.height;
        that.setRectangle(this);
        setRectangle(x, y, width, height);
    }

    @Override
    public RectangleF clone() {
        try {
            return (RectangleF) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return copy();
        }
    }

    @Override
    public RectangleF copy() {
        return new RectangleF(this);
    }

    @Override
    public void reset() {
        x = y = width = height = 0;
    }

    @Override
    public boolean isIdentity() {
        return x == 0 && y == 0 && width == 0 && height == 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        RectangleF that = (RectangleF) object;

        if (Float.compare(x, that.x) != 0) return false;
        if (Float.compare(y, that.y) != 0) return false;
        if (Float.compare(width, that.width) != 0) return false;
        return Float.compare(height, that.height) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != 0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != 0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (width != 0.0f ? Float.floatToIntBits(width) : 0);
        result = 31 * result + (height != 0.0f ? Float.floatToIntBits(height) : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName()
                + '{' +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public void writeToBundle(BundleOutput out) throws IOException {
        out.putFloat("x", x);
        out.putFloat("y", y);
        out.putFloat("width", width);
        out.putFloat("height", height);
    }

    @Override
    public void readFromBundle(BundleInput in) throws IOException {
        x = in.getFloat("x", 0);
        y = in.getFloat("y", 0);
        width = in.getFloat("width", 0);
        height = in.getFloat("height", 0);
    }

}
