// pages/activity/signup/signCode/index.js
import server from '../../../../config/server.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
      code: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  inputMsg(event){
    console.log(event);
    let type = event.target.id;
    this.setData({
      [type]: event.detail.detail.value
    })
  },
  signup(){
    wx.request({
      url: server.host + '/activity/signup?code=' + this.data.code,
      method:"POST",
      header:{"token":server.token},
      success: (res) => {
        var data = res.data
          if(data['code']=="200" && data['msg'].search("成功")!=-1){
            wx.showModal({
              title: '提示',
              content: '签到成功',
              complete: (res) => {
                wx.navigateTo({
                  url: '/pages/activity/index',
                })
              }
            })
          }else{
            wx.showToast({
              title: '签到失败',
              icon:"error",
              duration:2000
            })
          }
      }
    })
  }
})