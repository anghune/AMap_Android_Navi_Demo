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
 * @version 1.7
 */
package org.xclcharts.renderer.info;

import java.util.ArrayList;

import org.xclcharts.common.DrawHelper;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.line.PlotDot;
import org.xclcharts.renderer.line.PlotDotRender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;


/**
 * @ClassName DyInfo
 * @Description DyInfo基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class DyInfo {
	
	private static final String TAG="DyInfo";
	
	private Paint mPaintBorder = null;
	private Paint mPaintBackground = null;
	
	private RectF mRect = new RectF();
	private float mRowSpan = 5.0f;
	private float mColSpan = 10.0f;
	private float mMargin = 5.f;
	
	private XEnum.DyInfoStyle mStyle = XEnum.DyInfoStyle.ROUNDRECT; //0 rect, 1 roundRect
	private float mRoundRectX = 5.f;
	private float mRoundRectY = 5.f;	
	
	private ArrayList<PlotDot> mClickedDotStyle = null;
	private ArrayList<String> mClickedText = null;
	private ArrayList<Paint> mClickedPaint = null;
	protected PointF mCenterXY = null;
	
	protected Align mPositionAlign = Align.RIGHT;
	private float mRectWidth = 0.0f;
	private float mRectHeight = 0.0f;
	
	public DyInfo()
	{
		
	}
	
	public Paint getBorderPaint()
	{
		if(null == mPaintBorder)
		{
			mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaintBorder.setStyle(Style.STROKE);
		}
		
		return mPaintBorder;
	}

	/**
	 * 开放背景画笔
	 * 
	 * @return 画笔
	 */
	public Paint getBackgroundPaint() 
	{
		if(null == mPaintBackground)
		{
			mPaintBackground  = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaintBackground.setAlpha(100);
			mPaintBackground.setColor(Color.YELLOW);
		}
		return mPaintBackground;			
	}
		
	private boolean validateParams()
	{		
		if(null == mCenterXY)
		{
			Log.e(TAG,"没有传入点击坐标.");
			return false;
		}
		if(null == mClickedPaint)
		{
			Log.e(TAG,"没有传入画笔.");
			return false;
		}		
		return true;		
	}
	
	private void getContentRect()
	{
		int countDots = (null != mClickedDotStyle)? mClickedDotStyle.size():0 ;	
		int countPaint = (null != mClickedPaint)? mClickedPaint.size():0 ;
		int countText = (null != mClickedPaint)? mClickedPaint.size():0 ;	
		//if(0 == countText && 0 == countDots ) return;
		
		float textWidth = 0.0f,textHeight = 0.0f;
				
		float maxWidth = 0.0f;
		float maxHeight = 0.0f;
		float rowWidth = 0.0f;
		
		Paint paint = null;	
		String text = "";
		for(int i=0;i<countText;i++)
		{
			if(countPaint > i) paint = mClickedPaint.get(i);
			if(null == paint) break;			
			
			text = mClickedText.get(i);			
			textHeight = DrawHelper.getInstance().getPaintFontHeight(paint);
			textWidth = DrawHelper.getInstance().getTextWidth(paint, text);
						
			rowWidth = textWidth;	
			if(countDots > i)
			{
				PlotDot plot = mClickedDotStyle.get(i);			
				if( plot.getDotStyle() !=  XEnum.DotStyle.HIDE )
				{
					rowWidth += textHeight + mColSpan;
				}		
			}
			
			if(Float.compare(rowWidth, maxWidth) == 1)
			{
				maxWidth = rowWidth;
			}
			
			maxHeight += textHeight;		
		}			
		maxHeight += 2 * mMargin + countText  * mRowSpan;
		maxWidth += 2 * mMargin;		
		
		mRectWidth = maxWidth;
		mRectHeight = maxHeight;
			
		getInfoRect();		
	}
	
	/**
	 * 设置行间距
	 * @param span 间距
	 */
	public void setRowSpan(float span)
	{
		mRowSpan = span;		
	}
	
	public void setColSpan(float span)
	{
		mColSpan = span;
	}
	
	public void setMargin(float margin)
	{
		mMargin = margin;
	}
	
	public void setRoundRectX(float x)
	{
		mRoundRectX = x;
	}
	
	public void setRoundRectY(float y)
	{
		mRoundRectY = y;
	}
	
	protected void setCenterXY(float x,float y)
	{
		if(null == mCenterXY) mCenterXY = new PointF();
		mCenterXY.x = x;
		mCenterXY.y = y;
	}
	
	public void setStyle(XEnum.DyInfoStyle style)
	{
		mStyle = style;
	}
	
	//public void setAlign(Align align)
	//{
	//	mPositionAlign = align;
	//}

	
	protected void addInfo(String text,Paint paint)
	{
		PlotDot dot = new PlotDot();
		dot.setDotStyle(XEnum.DotStyle.HIDE);
		addInfo(dot,text,paint);
	}
	
	protected void addInfo(PlotDot dotStyle,String text,Paint paint)
	{
		if(null == mClickedDotStyle) mClickedDotStyle = new ArrayList<PlotDot>();
		if(null == mClickedText) mClickedText = new ArrayList<String>();
		if(null == mClickedPaint) mClickedPaint = new ArrayList<Paint>();
		
		mClickedDotStyle.add(dotStyle);
		mClickedText.add(text);
		mClickedPaint.add(paint);		
	}
	

	protected void drawInfo(Canvas canvas) //hint  ToolTips
	{
		if(validateParams() == false)return ;	
		
		int countDots = (null != mClickedDotStyle)? mClickedDotStyle.size():0 ;	
		int countPaint = (null != mClickedPaint)? mClickedPaint.size():0 ;
		int countText = (null != mClickedPaint)? mClickedPaint.size():0 ;	
		if(0 == countText && 0 == countDots ) return;
		
		getContentRect();		
		if(null == mRect) return;
		
		if(XEnum.DyInfoStyle.RECT == mStyle)
		{
			canvas.drawRect(mRect, this.getBackgroundPaint());				
			canvas.drawRect(mRect, this.getBorderPaint());
		}else{
			canvas.drawRoundRect(mRect, mRoundRectX, mRoundRectY, this.getBackgroundPaint());	
			canvas.drawRoundRect(mRect, mRoundRectX, mRoundRectY,this.getBorderPaint());
		}
		
		float currDotsX = mRect.left + mMargin;
		float currRowY = mRect.top + mMargin;
		float textHeight = 0.0f;	
		float currTextX = currDotsX ;

		Paint paint = null;		
		for(int i=0;i<countText;i++)
		{
			if(countPaint > i) paint = mClickedPaint.get(i);
			if(null == paint) break;
			
			textHeight = DrawHelper.getInstance().getPaintFontHeight(paint);	
			
			if(countDots > i)
			{
				PlotDot plot = mClickedDotStyle.get(i);
				
				if(plot.getDotStyle() !=  XEnum.DotStyle.HIDE )//画dot
				{			
					PlotDotRender.getInstance().renderDot(canvas,plot, 						 
							currDotsX + (textHeight / 2), 
							currRowY + (textHeight / 2), 
							paint);		
					
					currTextX = currDotsX + textHeight + mColSpan;
				}
			}
			if(countText > i)canvas.drawText(mClickedText.get(i),currTextX , currRowY + textHeight,paint);
							
			currRowY += textHeight + mRowSpan;		
			currTextX = currDotsX ;
		}			
		
		
	}
	
	protected void clear()
	{
		if(null != mClickedDotStyle)mClickedDotStyle.clear();
		if(null != mClickedText)mClickedText.clear();
		if(null != mClickedPaint)mClickedPaint.clear();
	}

	private void getInfoRect()
	{	
		switch(mPositionAlign)
		{
			case LEFT:				
				mRect.left = mCenterXY.x - mRectWidth;
				mRect.right = mCenterXY.x ;
				mRect.top = mCenterXY.y - mRectHeight;
				mRect.bottom = mCenterXY.y;	
				break;
			case CENTER:
				float halfWidth = mRectWidth/2;
				mRect.left = mCenterXY.x - halfWidth;
				mRect.right = mCenterXY.x + halfWidth;
				mRect.top = mCenterXY.y - mRectHeight;
				mRect.bottom = mCenterXY.y;	
				break;
			case RIGHT:
				mRect.left = mCenterXY.x ;
				mRect.right = mCenterXY.x + mRectWidth;
				mRect.top = mCenterXY.y - mRectHeight;
				mRect.bottom = mCenterXY.y;	
				break;
			default:
				break;
				
		}
		
	}
	
	
}
