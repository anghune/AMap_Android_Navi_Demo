/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 2.1
 */
package org.xclcharts.renderer.info;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

/**
 * @ClassName AnchorRender
 * @Description  绘制批注的形状
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class AnchorRender {
	
	private static AnchorRender instance = null;
	
	private RectF mRect= null;
	
	private Paint mPaintText = null;
	private Paint mPaintBg = null;
	
	public AnchorRender()
	{

	}
	
	public static synchronized AnchorRender getInstance()
	{
		if(instance == null)
		{
			instance = new AnchorRender();
		}
		return instance;
	}
	
	
	public void renderAnchor(Canvas canvas,
					AnchorDataPoint pAnchor, float cx, float cy) {
		
		if(null == pAnchor) return;
		
		float radius = pAnchor.getRadius();
		
		switch(pAnchor.getAreaStyle())
		{
		case FILL:
			getBgPaint().setStyle(Style.FILL);
			break;
		case STROKE:
			getBgPaint().setStyle(Style.STROKE);
			break;		
		}		
		getBgPaint().setColor(pAnchor.getBgColor());
		
		switch (pAnchor.getAnchorStyle()) {
		//case DOT:										
		//	canvas.drawCircle(cx, cy, radius, getBgPaint());	
		//	break;
		case RECT:	
			renderRect(canvas,getBgPaint(),radius,cx, cy);			
			break;
		case CIRCLE:			
			canvas.drawCircle(cx, cy, radius, getBgPaint());
			break;
		default:
		}						
		
		if(pAnchor.getAnchor() != "")
		{
			getTextPaint().setColor(pAnchor.getTextColor());
			getTextPaint().setTextSize(pAnchor.getTextSize());
			canvas.drawText(pAnchor.getAnchor(), cx, cy, getTextPaint());
		}
	}
	
	private void renderRect(Canvas canvas,Paint paint,
							float radius,float cirX,float cirY )
	{
		
		if(null == mRect)mRect = new RectF();
		
		mRect.left =  (cirX - radius);
		mRect.top =   (cirY - radius); 
		mRect.right =  (cirX + radius);
		mRect.bottom = (cirY + radius);
		canvas.drawRect(mRect,getBgPaint());
	}
	
	private Paint getTextPaint()
	{
		if(null == mPaintText) mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);		
		return mPaintText;
	}
	
	private Paint getBgPaint()
	{
		if(null == mPaintBg) mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);	
		mPaintBg.setStrokeWidth(2);
		return mPaintBg;
	}
	
	
	
}
