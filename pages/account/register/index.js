// pages/account/register/index.js
import server from '../../../config/server.js'
const idcard = require('../../../utils/idcard.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
      pwd: '',
      vpwd: '',
      username: '',
      actual: '',
      tel: '',
      idcard: '',
      idcardverift: true,
      pwdverift: true,
      accountverify: true
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
  // 验证密码是否一致
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
  // 验证账号是否存在
  accountExistence(){
    var data = '';
    // 调用后端登录接口
    wx.request({
      url: server.host + "/user/verify?tel=" + this.data.tel,
      method: 'POST',
      success: (result) => {
        var data = result.data;
        wx.showToast({
          title: data['msg'],
          icon: 'none'
        })
        if(data['msg'].search("已存在") != -1){
          this.setData({
            accountverify: false
          })
        }else{
          this.setData({
            accountverify: true
          })
        }
      }
    })
  },
  // 发起注册请求
  toRegister(){
    if(this.data.accountverify && this.data.idcardverift && this.data.pwdverift){
      let{
        pwd,
        username,
        actual,
        tel,
        idcard
      } = this.data;
      let sex = idcard.charAt(16) % 2;
      wx.request({
        url: server.host + "/user/register",
        method: 'POST',
        data: {
          pwd,
          username,
          actual,
          sex,
          tel,
          idcard
        },
        success: (result) => {
          var data = result.data;
          if(data['msg'].search("注册成功") != -1){
            wx.showModal({
              title: '提示',
              content: '注册成功'
            })
            wx.reLaunch({
              url: '/pages/index/index',
            })
          }
        }
      })
    }
    if(!this.data.accountverify){
      wx.showToast({
        title: "手机号已存在",
        icon: 'none'
      })
    }
    if(!this.data.pwdverift){
      wx.showToast({
        title: "两次密码不一致",
        icon: 'none'
      })
    }
    if(!this.data.idcardverift){
      wx.showToast({
        title: "身份证格式不正确",
        icon: 'none'
      })
    }
  }
})