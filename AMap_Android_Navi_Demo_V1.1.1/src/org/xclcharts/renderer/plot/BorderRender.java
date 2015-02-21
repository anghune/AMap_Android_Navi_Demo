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
 * @version 1.0
 */
package org.xclcharts.renderer.plot;

import org.xclcharts.common.DrawHelper;

import android.graphics.Canvas;
import android.graphics.RectF;
/**
 * @ClassName BorderRender
 * @Description  图边框绘制类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */
public class BorderRender extends Border {
	
	private RectF mRect = new RectF();	
	
	

	public BorderRender()
	{
	}
	
	/**
	 * 边框默认内边距
	 * @return 内边距
	 */
	public int getBorderSpadding()
	{
		return mBorderSpadding;
	}
	
	
	 
	 private void setPaintLineStyle()
	 {
		 switch(getBorderLineStyle())
			{
			case SOLID:					
				break;
			case DOT:			
				getLinePaint().setPathEffect(DrawHelper.getInstance().getDotLineStyle());			
				break;
			case DASH:		
				//虚实线 	
				getLinePaint().setPathEffect(DrawHelper.getInstance().getDashLineStyle());
				break;
			}
	 }
	 
	 
	public void renderBox(Canvas canvas,RectF rect,
							boolean showBoxBorder,boolean showBackground)
	{			
		setPaintLineStyle();	
											
		switch(getBorderRectType())
		{
			case RECT:		
				if(showBackground)
					canvas.drawRect(rect, getBackgroundPaint());	
				
				if(showBoxBorder)
					canvas.drawRect(rect, getLinePaint());
				break;
			case ROUNDRECT:		
				if(showBackground)
						canvas.drawRoundRect(rect, getRoundRadius(), 
											 getRoundRadius(), getBackgroundPaint());	
				if(showBoxBorder)
					canvas.drawRoundRect(rect, getRoundRadius(), 
											getRoundRadius(), getLinePaint());					
			break;
		}			
	}

	/**
	 * 绘制边
	 * @param canvas	画布
	 * @param left	左边距
	 * @param top	上边距
	 * @param right	右边距
	 * @param bottom	底边距
	 */
	public void renderBorder(String type ,Canvas canvas,
							 float left,float top,float right,float bottom)
	{
		
		mRect.left = left + mBorderSpadding;
		mRect.top = top + mBorderSpadding;
		mRect.right = right - mBorderSpadding;
		mRect.bottom = bottom - mBorderSpadding;		
			
		setPaintLineStyle();	
		
		switch(getBorderRectType())
		{
			case RECT:				
				if(type.equals("CHART"))
				{
					if(null != mPaintBackground) 
						canvas.drawRect(mRect, mPaintBackground);		
				}else{ //BORDER
					canvas.drawRect(mRect, getLinePaint());
				}
				break;
			case ROUNDRECT:		
				if(type.equals("CHART"))
				{
					if(null != mPaintBackground)
						canvas.drawRoundRect(mRect, getRoundRadius(), 
								getRoundRadius(), mPaintBackground);	
				}else{ //BORDER
					canvas.drawRoundRect(mRect, getRoundRadius(), 
								getRoundRadius(), getLinePaint());		
				}
			break;
		}
	}
	
	
}
