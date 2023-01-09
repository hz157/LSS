import server from "../../../config/server"

// pages/activity/detailactivity/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      id: '',
      record: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
      wx.request({
        url: server.host + '/activity/getById?id=' + options.id,
        method: "POST",
        header: {"token" : server.token},
        success: (result) => {
          var data = result.data
          console.log(data)
          this.setData({
            id : options.id,
            record : data['data']
          })
        }
      })
  },
  join(){
    wx.showModal({
      title: '提示',
      content: '是否确认参加该活动？',
      complete: (res) => {
        if (res.cancel) {
          
        }
    
        if (res.confirm) {
          wx.request({
            url: server.host + '/activity/register?id='+this.data.id,
            method:"POST",
            header:{"token":server.token},
            success: (res)=>{
              var data = res.data
              if(data['code']=="200" && data['msg'].search("成功")!=-1){
                wx.request({
                  url: server.host + '/userInfo/addIntegral?type=1',
                  method: 'POST',
                  header:{"token": server.token},
                  success: (res) => {
                    console.log(res.data)
                  }
                })
                wx.showModal({
                  title: '提示',
                  content: '参加成功',
                  complete: (res) => {
                    if (res.confirm) {
                      wx.navigateTo({
                        url: '/pages/activity/index',
                      })
                    }
                  }
                })
              }else{
                wx.showToast({
                  title: '参加失败',
                  icon:"error",
                  duration:2000
                })
              }
            }
          })
        }
      }
    })
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

  }
})