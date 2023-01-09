// pages/book/borrowbook/index.js
import server from '../../../config/server.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
      book:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log(options)
    console.log(server.host + '/book/getInfo?isbn=' + options.isbn)
    wx.request({
      url: server.host + '/book/getInfo?isbn=' + options.isbn,
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
        var data = result.data
        console.log(data)
        this.setData({
          book: data['data']
      });
      wx.request({
        url: server.host + "/stock/addHistory?isbn=" + data['data'].isbn,
        method: 'POST',
        header:{
          "token": server.token
        },
        success: (res) => {
            console.log(1 + res.data['data'])
        }
      })
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
  onShow(options) {
    
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
  borrow(){
      let isbn = this.data.book.isbn
      wx.request({
        url: server.host + '/stock/borrow?isbn=' + isbn,
        method: 'POST',
        header:{"token": server.token},
          success: (result) => {
            var data = result.data
            if(data['code'] == "200" && data['msg'].search("成功") != -1){
              wx.request({
                url: server.host + '/userInfo/addIntegral?type=0',
                method: 'POST',
                header:{"token": server.token},
                success: (res) => {
                  console.log(res.data)
                }
              })
              wx.showModal({
                title: '提示',
                content: data['msg'],
                complete: (res) => {
                  wx.navigateTo({
                    url: '/pages/index/index',
                  })
                }
              })
              wx.navigateTo({
                url: '/pages/index/index',
              })
            }else{
              wx.showToast({
                title: "借阅失败",
                icon: "error",
                duration: 2000
              })
            }
          }
      })
  }
})