// pages/account/login/index.js
import server from '../../../config/server.js'
Page({
  /**
   * 页面的初始数据
   */
  data: {
    id: '',
    pwd: ''
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
    let type = event.currentTarget.id;
    this.setData({
      [type]: event.detail.value
    })
  },
  /**
 * 登录
 */
toLogin() {
  let {
    id,
    pwd
  } = this.data;
  var data = '';
  // 调用后端登录接口
  wx.request({
    url: server.host + "/user/login",
    method: 'POST',
    data: {
      id,
      pwd
    },
    success: (result) => {
      var data = result.data
      console.log(data)
      if (data['code'] == "200" && data['data']!="null"){
        server.token = data['data'];
        wx.showToast({
          title: '登录成功',
        }),
        // 跳转首页
        wx.reLaunch({
          url: '/pages/index/index',
        })
      }else if(data['code'] == "200"){
        wx.showToast({
          title: data['msg'],
          icon: 'none'
        })
      }else{
        wx.showToast({
          title: data['msg'],
          icon: 'none'
        })
      }
    }
  })
},

// 忘记密码
forgot(){
  wx.navigateTo({
    url: '/pages/account/forgot/index',
  })
},


// 注册账号
register(){
  wx.navigateTo({
    url: '/pages/account/register/index',
  })
}
})