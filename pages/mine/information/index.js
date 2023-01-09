// pages/mine/information/index.js
import server from '../../../config/server.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
      uid: '',
      username: '',
      actualname: '',
      sex: '',
      address: '',
      mail: '',
      idcard: '',
      tel: ''
      
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
    wx.request({
      url: server.host + '/userInfo/getInfo',
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
        var data = result.data['data']
        let chsex = "女"
        if (data['sex']=="Male"){
          chsex = "男"
        }
        let sidcard = data['idcard'].substring(0,13) + "****"
        console.log(data)
        this.setData({
          username: data['username'],
          uid: data['id'],
          actualname: data['actualname'],
          sex: chsex,
          address: data['address'],
          mail: data['mail'],
          idcard: sidcard,
          tel: data['tel']
      });
      }
    })
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
  editProfile(){
    let{
      username,
      mail,
      address
    } = this.data
    wx.request({
      url: server.host + '/userInfo/editProfile',
      method: 'POST',
      header:{
        "token": server.token
      },
      data: {
        username,
        mail,
        address
      },
      success: (result) => {
          var data = result.data
          if(data['code']=="200" && data['msg'].search("成功")!=-1){
              wx.showToast({
                title: '修改成功',
                icon: 'success',
                duration: 2000
              })
              wx.navigateTo({
                url: '/pages/mine/index',
              })
          }else{
            wx.showToast({
              title: data['msg'],
              icon: 'error',
              duration: 2000
            })
          }
      }
    })
  },
  inputMsg(event){
    console.log(event);
    let type = event.currentTarget.id; 
    this.setData({
      [type]: event.detail.detail.value
    })
  }
  
})