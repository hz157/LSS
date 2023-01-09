// pages/mine/editpwd/index.js
import server from '../../../config/server.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    oldPwd: '',
    newPwd: ''
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
  editPwd(){
    let{
      oldPwd,
      newPwd
    } = this.data;
    wx.request({
      url: server.host + '/userInfo/editPwd',
      method: 'POST',
      header:{
        "token": server.token
      },
      data:{
        oldPwd,
        newPwd
      },
      success: (result) => {
        var data = result.data
        console.log(data)
        if(data['code']=="200" && data["msg"].search("修改成功") != -1){
          wx.showToast({
            title: data["msg"],
            icon: 'success',
            duration: 2000
          })
          wx.navigateTo({
            url: '/pages/mine/index',
          })
        }else{
          wx.showToast({
            title: data["msg"],
            icon: 'error',
            duration: 2000
          })
        }
      }
    })
  }
})