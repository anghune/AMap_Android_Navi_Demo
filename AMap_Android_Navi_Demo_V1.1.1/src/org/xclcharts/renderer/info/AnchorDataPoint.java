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

import org.xclcharts.renderer.XEnum;

import android.graphics.Color;


/**
 * @ClassName AnchorDataPoint
 * @Description 数据批注基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */
public class AnchorDataPoint {
	
	private int mDataSeriesID = -1; //
	private int mDataChildID = -1; //Points
	
	private XEnum.AnchorStyle mAnchorStyle = XEnum.AnchorStyle.RECT;
	private String mAnchor = "";
	private int mAnchorTextSize = 22;
	private int mAnchorTextColor = Color.BLUE;
	
	private int mBgColor = Color.BLACK;
	private int mAlpha = 100;
	private XEnum.DataAreaStyle mDataAreaStyle = XEnum.DataAreaStyle.STROKE;
	
	private float mRadius = 30.f;
	
	
	public AnchorDataPoint()
	{
		
	}

	public AnchorDataPoint(int dataSeriesID,int dataChildID,XEnum.AnchorStyle anchorStyle)
	{
		mDataSeriesID = dataSeriesID;
		mDataChildID = dataChildID;
		mAnchorStyle = anchorStyle;
	}
	
	public AnchorDataPoint(int dataSeriesID,XEnum.AnchorStyle anchorStyle)
	{
		mDataSeriesID = dataSeriesID;
		mAnchorStyle = anchorStyle;
	}
	
	
	public XEnum.AnchorStyle getAnchorStyle() {
		return mAnchorStyle;
	}

	public void setAnchorStyle(XEnum.AnchorStyle style) {
		this.mAnchorStyle = style;
	}


	public int getDataSeriesID() {
		return mDataSeriesID;
	}



	public void setDataSeriesID(int mDataSeriesID) {
		this.mDataSeriesID = mDataSeriesID;
	}



	public int getDataChildID() {
		return mDataChildID;
	}



	public void setDataChildID(int mDataChildID) {
		this.mDataChildID = mDataChildID;
	}
	
	public void setAnchor(String anchor)
	{
		mAnchor = anchor;
	}
	
	public String getAnchor()
	{
		return mAnchor;
	}
	
	
	public void setRadius(float radius)
	{
		mRadius = radius;
	}
	
	public float getRadius()
	{
		return mRadius;
	}
	
	public void setTextSize(int size)
	{
		mAnchorTextSize = size;
	}
	
	public float getTextSize()
	{
		return mAnchorTextSize;
	}
	
	public void setTextColor(int color)
	{
		mAnchorTextColor = color;
	}
	
	public int getTextColor()
	{
		return mAnchorTextColor;
	}
	
	
	
	public void setAlpha(int alpha)
	{
		mAlpha = alpha;
	}
	
	public int getAlpha()
	{
		return mAlpha;
	}
	
	public void setBgColor(int color)
	{
		mBgColor = color;
	}
	
	public int getBgColor()
	{
		return mBgColor;
	}
	
	public void setAreaStyle(XEnum.DataAreaStyle style)
	{
		mDataAreaStyle = style;
	}
	
	public XEnum.DataAreaStyle getAreaStyle()
	{
		return mDataAreaStyle;
	}
	
	

}
