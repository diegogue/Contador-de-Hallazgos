package ecci.GoF;

import ij.process.ByteProcessor;

import java.awt.*;
import java.util.*;

public class BlobDetector {
    private Deque<Point> pointsQueue;
    private Set<Point> pointsSet;
    private int tolerance;
    private int width;
    private int height;
    private byte[] image;
    public int[] xpoints;
    public int[] ypoints;
    public int npoints;

    public BlobDetector(int tolerance) {
        pointsQueue = new ArrayDeque<>();
        pointsSet = new HashSet<>(1000);
        this.tolerance = tolerance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public void setImage(ByteProcessor processor) {
        image = (byte[]) processor.getPixels();
        width = processor.getWidth();
        height = processor.getHeight();
    }

    public void computeBlob(Point startingPoint) {
        pointsSet.add(startingPoint);
        pointsQueue.add(startingPoint);

        while (!pointsQueue.isEmpty()) {
            Point base = pointsQueue.pop();
            // Assuming y increments from top to bottom
            Point west  = new Point(base.x - 1, base.y);
            Point north = new Point(base.x, base.y - 1);
            Point east  = new Point(base.x + 1, base.y);
            Point south = new Point(base.x, base.y + 1);
            addPoint(base, west);
            addPoint(base, north);
            addPoint(base, east);
            addPoint(base, south);
        }
        System.out.println(pointsSet);
        System.out.println(pointsQueue);
        setPoints();
    }

    private void addPoint(Point base, Point direction) {
        if (direction.x < 0 || direction.x >= width) return;
        if (direction.y < 0 || direction.y >= height) return;
        if (pointsSet.contains(direction)) return;
        byte pixelValueBase = image[base.x + base.y*width];
        byte pixelValueDire = image[direction.x + direction.y*width];
        if (Math.abs(pixelValueBase - pixelValueDire) > tolerance) return;

        pointsSet.add(direction);
        pointsQueue.add(direction);
    }

    private void setPoints() {
        /*
        ConvexHull hull = new ConvexHull(pointsSet.toArray(new Point[0]));
        hull.computeHull();
        xpoints = hull.xpoints;
        ypoints = hull.ypoints;
        npoints = hull.npoints;
        */
        Point points[] = pointsSet.toArray(new Point[0]);
        npoints = points.length;
        xpoints = new int[npoints];
        ypoints = new int[npoints];
        for (int i = 0; i < npoints; ++i) {
            xpoints[i] = points[i].x;
            ypoints[i] = points[i].y;
        }
    }
}
