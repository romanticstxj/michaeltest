package jsi;

import com.infomatiq.jsi.Point;
import com.infomatiq.jsi.Rectangle;

public class Polygon {

	private int polyCorners;
	private float polyX[];
	private float polyY[];
	
	public Rectangle convertToRec(){
		float minPolyX = Utils.min(polyX);
		float minPolyY = Utils.min(polyY);
		float maxPolyX = Utils.max(polyX);
		float maxPolyY = Utils.max(polyY);
		Rectangle rec = new Rectangle(minPolyX, minPolyY, maxPolyX, maxPolyY);
		return rec;
	}

	public int getPolyCorners() {
		return polyCorners;
	}

	public void setPolyCorners(int polyCorners) {
		this.polyCorners = polyCorners;
	}

	public float[] getPolyX() {
		return polyX;
	}

	public void setPolyX(float[] polyX) {
		this.polyX = polyX;
	}

	public float[] getPolyY() {
		return polyY;
	}

	public void setPolyY(float[] polyY) {
		this.polyY = polyY;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Polygon polygon = new Polygon();
		polygon.setPolyCorners(6);
//		polygon.setPolyX(new float[] { 1, 2, 3, 2, 1, 0 });
//		polygon.setPolyY(new float[] { 0, 0, 1, 2, 2, 1 });
		polygon.setPolyX(new float[] { 0, 1, 2, 2, 1, 0 });
		polygon.setPolyY(new float[] { 0, 0.5f, 0, 2, 1, 2 });
		
//		float i = 1.0f/3*3;
//		System.out.println(i);

		System.out.println(polygon.pointInPolygon(new Point(1.5f,1f)));
	}

	// Globals which should be set before calling this function:
	//
	// int polyCorners = how many corners the polygon has (no repeats)
	// float polyX[] = horizontal coordinates of corners
	// float polyY[] = vertical coordinates of corners
	// float x, y = point to be tested
	//
	// (Globals are used in this example for purposes of speed. Change as
	// desired.)
	//
	// The function will return YES if the point x,y is inside the polygon, or
	// NO if it is not. If the point is exactly on the edge of the polygon,
	// then the function may return YES or NO.
	//
	// Note that division by zero is avoided because the division is protected
	// by the "if" clause which surrounds it.

	public boolean pointInPolygon(float x, float y) {

		int i, j = polyCorners - 1;
		boolean oddNodes = false;
		
		float sx, sy, tx,ty, xinter;

		for (i = 0; i < polyCorners; j = i,i++) {
			sx = polyX[i];
			sy = polyY[i];
			tx = polyX[j];
			ty = polyY[j];
			
			// �������ζ����غ�
		      if((sx == x && sy == y) || (tx == x && ty == y)) {
		    	  System.out.println("�������ζ����غ�");
		        return true;
		      }
		      
//		    //���ڶ���ε�ˮƽ����
//	    	  if(sy == ty && y == sy){
//	    		  System.out.println("���ڶ���ε�ˮƽ����");
//	    		  return true; 
//	    	  }
			
		   // �ж��߶����˵��Ƿ�����������
		      if((sy < y && ty >= y) || (sy >= y && ty < y)) {
		    	// �߶��������� Y ������ͬ�ĵ�� X ����
		    	  xinter = sx + (y - sy) * (tx - sx) / (ty - sy);
		    	  
		    	// ���ڶ���εķ�ˮƽ����
		    	  if(xinter == x){
		    		  System.out.println("���ڶ���εı���");
		    		  return true;
		    	  }
		    	  
		    	  if(xinter < x){
		    		  oddNodes = !oddNodes;
		    	  }
		    	  
//		    	  oddNodes ^= (xinter < x);
		    	  
		      }
//			//��ʾ���Ե��������������������֮�䣬����ֻҪ��һ���˵�ĺ������ڲ��Ե����������
//			if ((polyY[i] < y && polyY[j] >= y || polyY[j] < y && polyY[i] >= y)
//					&&  (polyX[i]<=x || polyX[j]<=x)) {
//				//�жϲ��Ե�����������������γɵĽ����Ƿ��ڲ��Ե����ߣ�Xinter= Xn + Xdelta = Xn + slope(x/y) * deltaY
//				// judge whether Xinter < Xtest
////				if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i]) * (polyX[j] - polyX[i]) < x) {
////					oddNodes = !oddNodes;
////					
////				}
//				
//				oddNodes^=(polyX[i]+(y-polyY[i])/(polyY[j]-polyY[i])*(polyX[j]-polyX[i])<x);
//			}
		}

		return oddNodes;
	}
	
	public boolean pointInPolygon(Point p){
		return pointInPolygon(p.x, p.y);
	}

//	public boolean pointInPolygon1(float x, float y) {
//
//		boolean oddNodes = false, current = polyY[polyCorners - 1] > y, previous;
//		for (int i = 0; i < polyCorners; i++) {
//			previous = current;
//			current = polyY[i] > y;
//			if (current != previous)
//				oddNodes ^= y * multiple[i] + constant[i] < x;
//		}
//		return oddNodes;
//	}
	
	public void test(){
		
	}
}