// pages/account/forgot/index.js
import server from '../../../config/server.js'
const idcard = require('../../../utils/idcard.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tel: '',
    pwd: '',
    vpwd: '',
    idcard: '',
    pwdverift: true,
    idcardverift: true,
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
  verifypwd(){
    if(this.data.pwd != this.data.vpwd){
      this.setData({
        pwdverift: false
      })
      wx.showToast({
        title: '两次密码不一致',
        icon: 'none'
      })
    }else{
      this.setData({
        pwdverift: true
      })
    }
  },
  // 验证身份证
  verifyidcard(){
    if(!idcard.checkIdCard(this.data.idcard)){
      this.setData({
        idcardverift: false
      })
      wx.showToast({
        title: '身份证格式不正确',
        icon: 'none'
      })
    }else{
      this.setData({
        idcardverift: true
      })
    }
  },
  toForgot(){
    if(this.data.idcardverift && this.data.pwdverift){
      let{
        pwd,
        tel,
        idcard
      } = this.data;
      // pwd = hexMD5(pwd);
      wx.request({
        url: server.host + "/user/forgot",
        method: 'POST',
        data: {
          tel,
          pwd,
          idcard
        },
        success: (result) => {
          var data = result.data;
          if(data['msg'].search("修改成功") != -1){
            wx.showModal({
              title: '提示',
              content: '修改成功'
            })
            wx.reLaunch({
              url: '/pages/account/login/index',
            })
          }else{
            wx.showModal({
              title: '提示',
              content: '修改失败'
            })
          }
        }
      })
    }else if(this.data.idcardverift){
      wx.showToast({
        title: '身份证格式不正确',
        icon: 'none'
      })
    }else if(this.data.pwdverift){
      wx.showToast({
        title: '两次密码不一致',
        icon: 'none'
      })
    }
  }
})