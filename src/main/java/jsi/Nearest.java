package jsi;

import java.util.ArrayList;
import java.util.List;

import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.SpatialIndex;
import com.infomatiq.jsi.rtree.RTree;

import gnu.trove.TIntProcedure;

public class Nearest {
	
//	private static int rowCount = 1000;
//	private static int columnCount = 1000;
//	private static int count = rowCount * columnCount;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Polygon> polygons = new ArrayList<>();
		long start, end;
		
		//1)generate polygons
		Polygon polygon;
		float x1=1, x2=2, x3=3, x4=2, x5=1, x6=0;
		float y1=0, y2=0, y3=1, y4=2, y5=2, y6=1;
		for(int dx=0;dx<1000;dx+=3){
			for(int dy=0;dy<1000;dy+=2){
				x1 +=dx;
			   x2 +=dx;
			   x3 +=dx;
			   x4 +=dx;
			   x5 +=dx;
			   x6 +=dx;
			   float[] polyX = new float[]{x1,x2,x3,x4,x5,x6};
			   y1 +=dy;
			   y2 +=dy;
			   y3 +=dy;
			   y4 +=dy;
			   y5 +=dy;
			   y6 +=dy;
			   float[] polyY = new float[]{y1,y2,y3,y4,y5,y6};
			   polygon = new Polygon();
			   polygon.setPolyX(polyX);
			   polygon.setPolyY(polyY);
			   polygon.setPolyCorners(6);
			   polygons.add(polygon);
			}
		}
		
		SpatialIndex si = new RTree();
		si.init(null);
		List<Rectangle> recs = new ArrayList<>();
		//2)convert pologons to rectangles, set them to rtree index
		for(int i=0;i<polygons.size();i++){
			Rectangle rec =polygons.get(i).convertToRec();
			recs.add(rec);
			si.add(rec, i);
		}
		//above procedure can be setup when engine starts up
		
		//find the nearsestN whose distance is 0
		final Point p = new Point(1f, 1f);
	    System.out.println("Querying for the nearest 3 rectangles to " + p);
	    final int[] ret = new int[]{-1};
	    start = System.currentTimeMillis(); 
	    si.nearestN(p, new TIntProcedure() {
	      public boolean execute(int i) {
	        System.out.println("Rectangle " + i + " " + recs.get(i) + ", distance=" + recs.get(i).distance(p));
	        ret[0] = i;
	        return true;
	      }
	    }, 1, 0.0f);
	    end = System.currentTimeMillis();
	    System.out.println("total time = " + (end - start) + "ms");
		
		if(ret[0] != -1){ //找到了这个矩形，接着找点是否在相应的多边形内
			polygon = polygons.get(ret[0]);
			boolean b = polygon.pointInPolygon(p);
			System.out.println(b);
		}
		
	}

}
