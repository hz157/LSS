// pages/book/historybook/index.js
import server from '../../../config/server.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    record: [],
    books: []
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
      url: server.host + "/stock/getHistory",
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
          var data = result.data['data']
          this.setData({
            records: data
          })
        console.log(data)
        for(var i=0;i<data.length;i++){
          let tmp =  data[i]
            wx.request({
              url: server.host + '/book/getInfo?isbn=' + tmp['isbn'],
              method: "POST",
              header:{"token":server.token},
              success: (res) => {
                this.setData({
                  books: this.data.books.concat(res.data['data'])
                })
              }
            })
      }
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

  }
})